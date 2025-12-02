package gui;

import game.ChessGame;
import javax.swing.*;
import java.awt.*;

public class ChessGUI {
    private ChessGame chessGame;
    private BoardPanel boardPanel;
    private GameHistoryPanel historyPanel;
    private JMenuBar menuBar;
    private JLabel turnLabel;

    // Color themes
    private Color lightSquareColor = new Color(240, 217, 181);
    private Color darkSquareColor = new Color(181, 136, 99);

    public ChessGUI() {
        initializeGame();
        initializeGUI();
        initializeMenu();
    }

    private void initializeGame() {
        chessGame = new ChessGame();
        chessGame.setGui(this);
    }

    private void initializeGUI() {
        setTitle("Java Chess Game - Phase 3: Full Rules Implementation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        boardPanel = new BoardPanel(chessGame, this);
        historyPanel = new GameHistoryPanel(chessGame, this);

        // Create turn display panel
        JPanel topPanel = new JPanel();
        turnLabel = new JLabel("Current Turn: White");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
        turnLabel.setForeground(Color.BLACK);
        topPanel.add(turnLabel);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(historyPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void initializeMenu() {
        menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem settingsItem = new JMenuItem("Settings");
        JMenuItem exitItem = new JMenuItem("Exit");

        newGameItem.addActionListener(e -> newGame());
        settingsItem.addActionListener(e -> showSettings());
        exitItem.addActionListener(e -> System.exit(0));

        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
        gameMenu.add(settingsItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void newGame() {
        int result = JOptionPane.showConfirmDialog(
                this, "Start a new game?", "New Game", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            chessGame.newGame();
            boardPanel.updateBoard();
            historyPanel.clearHistory();
            updateTurnDisplay();
        }
    }

    private void showSettings() {
        SettingsDialog settingsDialog = new SettingsDialog(this, this);
        settingsDialog.setVisible(true);
    }

    public void showGameOver(String message) {
        JOptionPane.showMessageDialog(this, message, "Game Over!", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateBoardDisplay() {
        boardPanel.updateBoard();
    }

    public void updateTurnDisplay() {
        String currentTurn = chessGame.getCurrentTurn();
        turnLabel.setText("Current Turn: " +
                (currentTurn.equals("white") ? "White" : "Black"));
        turnLabel.setForeground(currentTurn.equals("white") ? Color.BLACK : Color.BLUE);
    }

    public void addMoveToHistory(String moveDescription) {
        historyPanel.addMove(moveDescription);
    }

    public void addCapturedPiece(String pieceDescription) {
        historyPanel.addCapturedPiece(pieceDescription);
    }

    public Color getLightSquareColor() {
        return lightSquareColor;
    }

    public Color getDarkSquareColor() {
        return darkSquareColor;
    }

    public void setBoardColors(Color lightColor, Color darkColor) {
        this.lightSquareColor = lightColor;
        this.darkSquareColor = darkColor;
        boardPanel.updateBoardColors();
    }

    public ChessGame getChessGame() {
        return chessGame;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            ChessGUI chessGUI = new ChessGUI();
            chessGUI.setVisible(true);
        });
    }
}
