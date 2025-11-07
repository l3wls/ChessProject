package gui;

import game.ChessGame;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel that displays game history, captured pieces, and provides undo
 * functionality.
 * Tracks all moves and captures during the game.
 */
public class GameHistoryPanel extends JPanel {
    private ChessGame chessGame;
    private JTextArea moveHistoryArea;
    private JList<String> capturedPiecesList;
    private JButton undoButton;
    private DefaultListModel<String> capturedPiecesModel;
    private List<String> moveHistory;

    /**
     * Constructs the game history panel.
     *
     * @param chessGame the chess game engine
     */
    public GameHistoryPanel(ChessGame chessGame) {
        this.chessGame = chessGame;
        this.moveHistory = new ArrayList<>();
        this.capturedPiecesModel = new DefaultListModel<>();

        initializePanel();
    }

    /**
     * Initializes the history panel components and layout.
     */
    private void initializePanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Move History Section
        JPanel historyPanel = createMoveHistoryPanel();
        // Captured Pieces Section
        JPanel capturedPanel = createCapturedPiecesPanel();
        // Control Buttons
        JPanel controlPanel = createControlPanel();

        // Add components to main panel
        add(historyPanel, BorderLayout.CENTER);
        add(capturedPanel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.NORTH);
    }

    /**
     * Creates the move history display area.
     */
    private JPanel createMoveHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Move History",
                TitledBorder.LEFT,
                TitledBorder.TOP));

        moveHistoryArea = new JTextArea(15, 20);
        moveHistoryArea.setEditable(false);
        moveHistoryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        moveHistoryArea.setText("Game Started\n===========\n");

        JScrollPane scrollPane = new JScrollPane(moveHistoryArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the captured pieces display.
     */
    private JPanel createCapturedPiecesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Captured Pieces",
                TitledBorder.LEFT,
                TitledBorder.TOP));

        capturedPiecesList = new JList<>(capturedPiecesModel);
        capturedPiecesList.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(capturedPiecesList);
        scrollPane.setPreferredSize(new Dimension(200, 100));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the control buttons panel.
     */
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

    /**
     * Adds a move to the history display.
     *
     * @param moveDescription description of the move
     */
    public void addMove(String moveDescription) {
        moveHistory.add(moveDescription);
        moveHistoryArea.append(moveDescription + "\n");

        // Auto-scroll to bottom
        moveHistoryArea.setCaretPosition(moveHistoryArea.getDocument().getLength());
    }

    /**
     * Adds a captured piece to the captured pieces list.
     *
     * @param pieceDescription description of the captured piece
     */
    public void addCapturedPiece(String pieceDescription) {
        capturedPiecesModel.addElement(pieceDescription);
    }

    /**
     * Undoes the last move in the game.
     */
    private void undoLastMove() {
        if (moveHistory.size() > 0) {
            // Remove last move from history
            String lastMove = moveHistory.remove(moveHistory.size() - 1);

            // Update display
            String historyText = moveHistoryArea.getText();
            int lastNewline = historyText.lastIndexOf("\n");
            if (lastNewline > 0) {
                moveHistoryArea.setText(historyText.substring(0, lastNewline));
            }

            JOptionPane.showMessageDialog(this, "Undid: " + lastMove + "\n\nNote: Board state not reverted in Phase 2");
        } else {
            JOptionPane.showMessageDialog(this, "No moves to undo.");
        }
    }

    /**
     * Clears the entire game history.
     */
    public void clearHistory() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Clear all game history?",
                "Clear History",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            moveHistory.clear();
            capturedPiecesModel.clear();
            moveHistoryArea.setText("Game Started\n===========\n");
        }
    }

    /**
     * Gets the number of moves in the history.
     *
     * @return the number of moves recorded
     */
    public int getMoveCount() {
        return moveHistory.size();
    }
}