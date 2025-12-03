package game;

import board.Board;
import pieces.Piece;
import gui.ChessGUI;
import utils.Position;
import javax.swing.JOptionPane;

/**
 * PHASE 3: Chess Game with FULL rules implementation
 * Enforces turn-based play, move validation, and check/checkmate detection
 */

public class ChessGame {
    private Board board;
    private String currentTurn;
    private boolean gameActive;
    private ChessGUI gui;

    public ChessGame() {
        this.board = new Board();
        this.currentTurn = "white";
        this.gameActive = true;
    }

    public void setGui(ChessGUI gui) {
        this.gui = gui;
    }

    /**
     * PHASE 3: Makes a move with FULL validation
     * - Enforces turn-based play
     * - Validates piece ownership
     * - Checks for check/checkmate after move
     */
    public boolean makeMove(Position from, Position to) {
        if (!gameActive) {
            return false;
        }

        Piece piece = board.getPiece(from);

        // Check if there's a piece at the starting position
        if (piece == null) {
            return false;
        }

        // PHASE 3: Enforce turn-based play
        if (!piece.getColor().equals(currentTurn)) {
            JOptionPane.showMessageDialog(null,
                    "It's " + currentTurn + "'s turn!",
                    "Wrong Turn",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Attempt the move with full validation
        boolean moveSuccessful = board.movePiece(from, to);

        if (moveSuccessful) {
            // Switch turns
            currentTurn = currentTurn.equals("white") ? "black" : "white";

            // Check for check on the opponent
            if (board.isCheck(currentTurn)) {
                JOptionPane.showMessageDialog(null,
                        currentTurn + " is in check!",
                        "Check!",
                        JOptionPane.WARNING_MESSAGE);
            }

            return true;
        } else {
            // Move was invalid
            JOptionPane.showMessageDialog(null,
                    "Invalid move! Please try again.",
                    "Invalid Move",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Starts a new game, resetting the board and game state.
     */
    public void newGame() {
        this.board = new Board();
        this.currentTurn = "white";
        this.gameActive = true;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * PHASE 3: Checks if the game has ended in checkmate
     */
    public boolean isCheckmate() {
        return board.isCheckmate(currentTurn);
    }

    /**
     * PHASE 3: Checks if the game has ended in stalemate
     */
    public boolean isStalemate() {
        return board.isStalemate(currentTurn);
    }

    /**
     * PHASE 3: Checks if the current player is in check
     */
    public boolean isInCheck() {
        return board.isCheck(currentTurn);
    }

    public boolean isKingCaptured() {
        return board.isKingCaptured();
    }

    public String getWinner() {
        return board.getWinner();
    }

    public boolean isGameActive() {
        return gameActive;
    }

    public void endGame() {
        this.gameActive = false;
    }

    public void undoMove() {
        // TODO: implement actual undo logic later
    }
}
