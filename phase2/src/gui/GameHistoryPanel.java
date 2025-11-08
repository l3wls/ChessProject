package gui;

import game.ChessGame;
import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    private List<Boolean> hadCapture; // Track which moves had captures

    public GameHistoryPanel(ChessGame chessGame, ChessGUI chessGUI) {
        this.chessGame = chessGame;
        this.chessGUI = chessGUI;
        this.moveHistory = new ArrayList<>();
        this.hadCapture = new ArrayList<>();
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
        hadCapture.add(false); // Default to no capture
        moveHistoryArea.append(moveDescription + "\n");
        moveHistoryArea.setCaretPosition(moveHistoryArea.getDocument().getLength());
    }

    public void addCapturedPiece(String pieceDescription) {
        capturedPiecesModel.addElement(pieceDescription);
        // Mark the last move as having a capture
        if (!hadCapture.isEmpty()) {
            hadCapture.set(hadCapture.size() - 1, true);
        }
    }

    private void undoLastMove() {
        if (moveHistory.size() > 0) {
            String lastMove = moveHistory.remove(moveHistory.size() - 1);
            boolean lastMoveHadCapture = hadCapture.remove(hadCapture.size() - 1);

            // FIRST: Undo the move in the game engine
            boolean undoSuccess = chessGame.undoMove();

            if (undoSuccess) {
                // SECOND: Update the board display to show the reverted state
                chessGUI.updateBoardDisplay();

                // THIRD: Update the text area
                String historyText = moveHistoryArea.getText();
                int lastNewline = historyText.lastIndexOf("\n");
                if (lastNewline > 0) {
                    moveHistoryArea.setText(historyText.substring(0, lastNewline));
                }

                // FOURTH: Remove captured piece if this move had one
                if (lastMoveHadCapture && capturedPiecesModel.size() > 0) {
                    capturedPiecesModel.removeElementAt(capturedPiecesModel.size() - 1);
                }

                JOptionPane.showMessageDialog(this, "Undid: " + lastMove);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to undo move in game engine");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No moves to undo.");
        }
    }

    public void clearHistory() {
        int result = JOptionPane.showConfirmDialog(this, "Clear all game history?",
                "Clear History", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            moveHistory.clear();
            hadCapture.clear();
            capturedPiecesModel.clear();
            moveHistoryArea.setText("Game Started\n===========\n");
        }
    }
}