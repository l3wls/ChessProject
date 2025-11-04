package board;

import pieces.*;
import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * SIMPLIFIED Board for Phase 2 - handles visual piece placement and basic moves
 * No move validation - any piece can move to any square
 */
public class Board {
    private Piece[][] squares;
    private List<Piece> capturedPieces;

    /**
     * Constructs a new chessboard with pieces in starting positions.
     */
    public Board() {
        squares = new Piece[8][8];
        capturedPieces = new ArrayList<>();
        initializeBoard();
    }

    /**
     * Places all pieces in their standard starting positions.
     */
    private void initializeBoard() {
        // Initialize pawns
        for (int col = 0; col < 8; col++) {
            squares[1][col] = new Pawn("black", new Position(1, col));
            squares[6][col] = new Pawn("white", new Position(6, col));
        }

        // Initialize black pieces (back rank)
        squares[0][0] = new Rook("black", new Position(0, 0));
        squares[0][1] = new Knight("black", new Position(0, 1));
        squares[0][2] = new Bishop("black", new Position(0, 2));
        squares[0][3] = new Queen("black", new Position(0, 3));
        squares[0][4] = new King("black", new Position(0, 4));
        squares[0][5] = new Bishop("black", new Position(0, 5));
        squares[0][6] = new Knight("black", new Position(0, 6));
        squares[0][7] = new Rook("black", new Position(0, 7));

        // Initialize white pieces (back rank)
        squares[7][0] = new Rook("white", new Position(7, 0));
        squares[7][1] = new Knight("white", new Position(7, 1));
        squares[7][2] = new Bishop("white", new Position(7, 2));
        squares[7][3] = new Queen("white", new Position(7, 3));
        squares[7][4] = new King("white", new Position(7, 4));
        squares[7][5] = new Bishop("white", new Position(7, 5));
        squares[7][6] = new Knight("white", new Position(7, 6));
        squares[7][7] = new Rook("white", new Position(7, 7));
    }

    /**
     * SIMPLIFIED: Moves any piece to any square without validation.
     * Handles captures and detects king capture for game end.
     *
     * @param from the starting position
     * @param to   the target position
     * @return true if move was executed, false if no piece at start position
     */
    public boolean movePiece(Position from, Position to) {
        Piece piece = getPiece(from);

        if (piece == null) {
            return false;
        }

        // Handle capture if target square is occupied
        Piece targetPiece = getPiece(to);
        if (targetPiece != null) {
            capturedPieces.add(targetPiece);
        }

        // Move the piece
        squares[to.getRow()][to.getCol()] = piece;
        squares[from.getRow()][from.getCol()] = null;
        piece.setPosition(to);

        return true;
    }

    /**
     * Gets the piece at the specified position.
     *
     * @param position the position to check
     * @return the piece at the position, or null if empty
     */
    public Piece getPiece(Position position) {
        if (!isValidPosition(position)) {
            return null;
        }
        return squares[position.getRow()][position.getCol()];
    }

    /**
     * Checks if a position is within the chessboard boundaries.
     *
     * @param position the position to validate
     * @return true if the position is valid, false otherwise
     */
    private boolean isValidPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    /**
     * Checks if a king has been captured.
     *
     * @return true if a king is in the captured pieces list, false otherwise
     */
    public boolean isKingCaptured() {
        for (Piece piece : capturedPieces) {
            if (piece instanceof King) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines the winner based on which king was captured.
     *
     * @return the color of the winning player, or null if no king was captured
     */
    public String getWinner() {
        for (Piece piece : capturedPieces) {
            if (piece instanceof King) {
                // The opponent of the captured king's color wins
                return piece.getColor().equals("white") ? "black" : "white";
            }
        }
        return null;
    }

    /**
     * Gets the 2D array representing the board squares.
     *
     * @return the 8x8 array of pieces
     */
    public Piece[][] getSquares() {
        return squares;
    }

    /**
     * Gets the list of captured pieces.
     *
     * @return the list of captured pieces
     */
    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }
}