package game;

import board.Board;
import gui.ChessGUI;
import utils.Position;

/**
 * SIMPLIFIED Chess Game for Phase 2 - visual functionality only
 * No move validation - any piece can move anywhere
 */
public class ChessGame {
    private Board board;
    private String currentTurn;
    private boolean gameActive;
    private ChessGUI gui;

    /**
     * Constructs a new chess game with initial state.
     */
    public ChessGame() {
        this.board = new Board();
        this.currentTurn = "white";
        this.gameActive = true;
    }

    /**
     * Sets the GUI reference for this game.
     *
     * @param gui the chess GUI
     */
    public void setGui(ChessGUI gui) {
        this.gui = gui;
    }

    /**
     * SIMPLIFIED: Moves any piece to any square without validation.
     * Only checks if there's a piece at the start position.
     *
     * @param from the starting position
     * @param to   the target position
     * @return true if move was executed, false if no piece at start position
     */
    public boolean makeMove(Position from, Position to) {
        if (!gameActive) {
            return false;
        }

        // Check if there's a piece to move
        if (board.getPiece(from) == null) {
            return false;
        }

        // Execute the move (no validation in Phase 2)
        boolean moveSuccessful = board.movePiece(from, to);

        if (moveSuccessful) {
            // Switch turn for visual indication (not enforced)
            currentTurn = currentTurn.equals("white") ? "black" : "white";
        }

        return moveSuccessful;
    }

    /**
     * Starts a new game, resetting the board and game state.
     */
    public void newGame() {
        this.board = new Board();
        this.currentTurn = "white";
        this.gameActive = true;
    }

    /**
     * Gets the current player's turn (visual indication only).
     *
     * @return "white" or "black"
     */
    public String getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Gets the game board.
     *
     * @return the chess board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Checks if a king has been captured.
     *
     * @return true if a king was captured, false otherwise
     */
    public boolean isKingCaptured() {
        return board.isKingCaptured();
    }

    /**
     * Gets the winner of the game.
     *
     * @return the winning color, or null if no winner
     */
    public String getWinner() {
        return board.getWinner();
    }

    /**
     * Checks if the game is active.
     *
     * @return true if the game is still active, false if finished
     */
    public boolean isGameActive() {
        return gameActive;
    }

    /**
     * Ends the current game.
     */
    public void endGame() {
        this.gameActive = false;
    }
}