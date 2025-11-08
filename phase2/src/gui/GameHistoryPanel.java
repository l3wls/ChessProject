package gui;

import game.ChessGame;
import pieces.Piece;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameHistoryPanel extends JPanel {
    private ChessGame chessGame;
    private ChessGUI chessGUI;
    private JTextArea moveHistoryArea;
    private JList<String> capturedPiecesList;
    private JButton undoButton;
    private DefaultListModel<String> capturedPiecesModel;
    private List<String> moveHistory;

    public GameHistoryPanel(ChessGame chessGame, ChessGUI chessGUI) {
        this.chessGame = chessGame;
        this.chessGUI = chessGUI;
        this.moveHistory = new ArrayList<>();
        this.capturedPiecesModel = new DefaultListModel<>();

        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(createMoveHistoryPanel(), BorderLayout.CENTER);
        add(createCapturedPiecesPanel(), BorderLayout.SOUTH);
        add(createControlPanel(), BorderLayout.NORTH);
    }

    private JPanel createMoveHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Move History"));

        moveHistoryArea = new JTextArea(15, 20);
        moveHistoryArea.setEditable(false);
        moveHistoryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        moveHistoryArea.setText("Game Started\n===========\n");

        JScrollPane scrollPane = new JScrollPane(moveHistoryArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCapturedPiecesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Captured Pieces"));

        capturedPiecesList = new JList<>(capturedPiecesModel);
        capturedPiecesList.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(capturedPiecesList);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        undoButton = new JButton("Undo Move");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoLastMove();
            }
        });

        JButton clearButton = new JButton("Clear History");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearHistory();
            }
        });

        panel.add(undoButton);
        panel.add(clearButton);
        return panel;
    }

    public void addMove(String moveDescription) {
        moveHistory.add(moveDescription);
        moveHistoryArea.append(moveDescription + "\n");
        moveHistoryArea.setCaretPosition(moveHistoryArea.getDocument().getLength());
    }

    public void addCapturedPiece(String pieceDescription) {
        capturedPiecesModel.addElement(pieceDescription);
    }

    private void undoLastMove() {
        if (moveHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No moves to undo.");
            return;
        }

        // Store the captured pieces count BEFORE undo
        int capturedSizeBefore = chessGame.getBoard().getCapturedPieces().size();

        // Get the last move description
        String lastMove = moveHistory.remove(moveHistory.size() - 1);

        // Call the game engine to undo the move
        boolean undoSuccess = chessGame.undoMove();

        if (undoSuccess) {
            // Get captured pieces count AFTER undo
            int capturedSizeAfter = chessGame.getBoard().getCapturedPieces().size();

            // Update the board display FIRST - this is critical!
            chessGUI.updateBoardDisplay();

            // Remove the last move from text area
            String historyText = moveHistoryArea.getText();
            int lastNewline = historyText.lastIndexOf("\n", historyText.length() - 2);
            if (lastNewline > 0) {
                moveHistoryArea.setText(historyText.substring(0, lastNewline + 1));
            }

            // If a piece was restored (captured list got smaller), remove from display
            if (capturedSizeAfter < capturedSizeBefore && capturedPiecesModel.getSize() > 0) {
                capturedPiecesModel.removeElementAt(capturedPiecesModel.getSize() - 1);
            }

            // Force a complete repaint
            chessGUI.revalidate();
            chessGUI.repaint();

            JOptionPane.showMessageDialog(this,
                    "Move undone: " + lastMove,
                    "Undo Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Re-add the move back if undo failed
            moveHistory.add(lastMove);
            JOptionPane.showMessageDialog(this,
                    "Failed to undo move",
                    "Undo Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearHistory() {
        int result = JOptionPane.showConfirmDialog(this,
                "Clear all game history?",
                "Clear History",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            moveHistory.clear();
            capturedPiecesModel.clear();
            moveHistoryArea.setText("Game Started\n===========\n");
        }
    }

    // Add this method to refresh captured pieces from the actual game state
    public void refreshCapturedPieces() {
        capturedPiecesModel.clear();
        for (Piece piece : chessGame.getBoard().getCapturedPieces()) {
            String captureText = piece.getColor() + " " + piece.getClass().getSimpleName();
            capturedPiecesModel.addElement(captureText);
        }
    }
}