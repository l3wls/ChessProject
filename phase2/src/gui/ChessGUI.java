package gui;

import game.ChessGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main chess game GUI window that contains the board, menu, and game controls.
 * Handles the overall user interface and coordinates between different
 * components.
 */
public class ChessGUI extends JFrame {
    private ChessGame chessGame;
    private BoardPanel boardPanel;
    private GameHistoryPanel historyPanel;
    private JMenuBar menuBar;
    private SoundManager soundManager;

    // Color themes
    private Color lightSquareColor = new Color(240, 217, 181); // Classic cream
    private Color darkSquareColor = new Color(181, 136, 99); // Classic brown

    /**
     * Constructs the main chess GUI window.
     */
    public ChessGUI() {
        initializeGame();
        initializeGUI();
        initializeMenu();
        initializeSounds();
    }

    /**
     * Initializes the chess game engine.
     */
    private void initializeGame() {
        chessGame = new ChessGame();
        chessGame.setGui(this);
    }

    /**
     * Sets up the main GUI components and layout.
     */
    private void initializeGUI() {
        setTitle("Java Chess Game - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create main components
        boardPanel = new BoardPanel(chessGame, this);
        historyPanel = new GameHistoryPanel(chessGame);

        // Add components to layout
        add(boardPanel, BorderLayout.CENTER);
        add(historyPanel, BorderLayout.EAST);

        // Set window properties
        pack();
        setLocationRelativeTo(null); // Center on screen
        setResizable(true);
    }

    /**
     * Creates the menu bar with game controls.
     */
    private void initializeMenu() {
        menuBar = new JMenuBar();

        // Game menu
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        JMenuItem settingsItem = new JMenuItem("Settings");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Add action listeners
        newGameItem.addActionListener(e -> newGame());
        saveGameItem.addActionListener(e -> saveGame());
        loadGameItem.addActionListener(e -> loadGame());
        settingsItem.addActionListener(e -> showSettings());
        exitItem.addActionListener(e -> System.exit(0));

        // Add items to menu
        gameMenu.add(newGameItem);
        gameMenu.add(saveGameItem);
        gameMenu.add(loadGameItem);
        gameMenu.addSeparator();
        gameMenu.add(settingsItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);

        // Add menus to menu bar
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Initializes sound effects manager.
     */
    private void initializeSounds() {
        soundManager = new SoundManager();
    }

    /**
     * Starts a new game, resetting the board and game state.
     */
    private void newGame() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Start a new game? Current progress will be lost.",
                "New Game",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            chessGame.newGame();
            boardPanel.updateBoard();
            historyPanel.clearHistory();
            soundManager.playSound("newgame");
        }
    }

    /**
     * Saves the current game state to a file.
     */
    private void saveGame() {
        JOptionPane.showMessageDialog(this,
                "Save feature would be implemented in Phase 3",
                "Save Game",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Loads a previously saved game state from a file.
     */
    private void loadGame() {
        JOptionPane.showMessageDialog(this,
                "Load feature would be implemented in Phase 3",
                "Load Game",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows the settings dialog for customizing the game appearance.
     */
    private void showSettings() {
        SettingsDialog settingsDialog = new SettingsDialog(this, this);
        settingsDialog.setVisible(true);
    }

    /**
     * Shows the about dialog with game information.
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(this,
                "Java Chess Game - Phase 2\n" +
                        "GUI Implementation - Visual Only\n" +
                        "Features:\n" +
                        "- Chess board with Unicode pieces\n" +
                        "- Click to move pieces (no rules)\n" +
                        "- Piece capture visualization\n" +
                        "- King capture detection\n" +
                        "- Game history with undo\n" +
                        "- Customizable board colors\n" +
                        "- Sound effects\n\n" +
                        "Note: No move validation in Phase 2",
                "About Chess Game",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays the game over dialog when a king is captured.
     *
     * @param winner the color of the winning player
     */
    public void showGameOver(String winner) {
        soundManager.playSound("checkmate");

        String message = winner.equals("white") ? "White wins by capturing the Black King!"
                : "Black wins by capturing the White King!";

        JOptionPane.showMessageDialog(this,
                message + "\n\nStart a new game from the Game menu.",
                "Game Over!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Updates the board display after a move.
     */
    public void updateBoardDisplay() {
        boardPanel.updateBoard();
    }

    /**
     * Adds a move to the game history.
     *
     * @param moveDescription description of the move
     */
    public void addMoveToHistory(String moveDescription) {
        historyPanel.addMove(moveDescription);
    }

    /**
     * Plays a move sound effect.
     */
    public void playMoveSound() {
        soundManager.playSound("move");
    }

    /**
     * Plays a capture sound effect.
     */
    public void playCaptureSound() {
        soundManager.playSound("capture");
    }

    /**
     * Plays a check sound effect.
     */
    public void playCheckSound() {
        soundManager.playSound("check");
    }

    // Getters for color themes
    public Color getLightSquareColor() {
        return lightSquareColor;
    }

    public Color getDarkSquareColor() {
        return darkSquareColor;
    }

    /**
     * Updates the board colors based on user preferences.
     *
     * @param lightColor the new light square color
     * @param darkColor  the new dark square color
     */
    public void setBoardColors(Color lightColor, Color darkColor) {
        this.lightSquareColor = lightColor;
        this.darkSquareColor = darkColor;
        boardPanel.updateBoardColors();
    }

    /**
     * Main method to launch the chess game.
     */
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show GUI
        SwingUtilities.invokeLater(() -> {
            ChessGUI chessGUI = new ChessGUI();
            chessGUI.setVisible(true);
        });
    }
}