package game;

import board.Board;
import pieces.Piece;
import gui.ChessGUI;
import utils.Position;
import javax.swing.JOptionPane;
import java.util.Deque;
import java.util.ArrayDeque;

/**
 * PHASE 3: Chess Game with FULL rules implementation
 * Enforces turn-based play, move validation, and check/checkmate detection
 */

public class ChessGame {
    private Board board;
    private String currentTurn;
    private boolean gameActive;
    private ChessGUI gui;

    /**
     * Stores the information needed to undo a single move.
     */
    private static class MoveRecord {
        private final Position from;
        private final Position to;
        private final Piece movedPiece;
        private final Piece capturedPiece;
        private final String turnBeforeMove;

        public MoveRecord(Position from, Position to,
                Piece movedPiece, Piece capturedPiece,
                String turnBeforeMove) {
            this.from = from;
            this.to = to;
            this.movedPiece = movedPiece;
            this.capturedPiece = capturedPiece;
            this.turnBeforeMove = turnBeforeMove;
        }
    }

    /**
     * Stack of moves to support undo functionality.
     */
    private final Deque<MoveRecord> moveHistory = new ArrayDeque<>();

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

        // Capture any piece currently on the destination square (before moving)
        Piece capturedPiece = board.getPiece(to);

        // Attempt the move with full validation
        boolean moveSuccessful = board.movePiece(from, to);

        if (moveSuccessful) {
            // Record this move so it can be undone later
            moveHistory.push(new MoveRecord(from, to, piece, capturedPiece, currentTurn));

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
        this.moveHistory.clear();
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

    /**
     * PHASE 3: Undo the last move, if possible.
     *
     * @return true if a move was undone, false if there was no move to undo
     */
    public boolean undoMove() {
        if (moveHistory.isEmpty()) {
            return false;
        }

        MoveRecord last = moveHistory.pop();

        // Restore the moved piece back to its original position
        board.setPiece(last.from, last.movedPiece);

        // Restore the captured piece (if any) to the destination square,
        // otherwise clear that square.
        board.setPiece(last.to, last.capturedPiece);

        // Restore whose turn it was before the move
        this.currentTurn = last.turnBeforeMove;

        // Make sure the game is active again
        this.gameActive = true;

        // Update the GUI if it is attached
        if (gui != null) {
            gui.updateBoardDisplay();
            gui.updateTurnDisplay();
        }

        return true;
    }
}
