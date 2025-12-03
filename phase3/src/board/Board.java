package board;

import pieces.*;
import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the chessboard and manages piece placement, movement, and game
 * state.
 * Handles move validation, capture logic, check/checkmate detection, castling,
 * and pawn promotion.
 */
public class Board {
    private Piece[][] squares;
    private List<Piece> capturedPieces;
    private boolean whiteKingMoved = false;
    private boolean blackKingMoved = false;
    private boolean whiteRookKingSideMoved = false;
    private boolean whiteRookQueenSideMoved = false;
    private boolean blackRookKingSideMoved = false;
    private boolean blackRookQueenSideMoved = false;

    /**
     * Constructs a new chessboard with pieces in their standard starting positions.
     */
    public Board() {
        squares = new Piece[8][8];
        capturedPieces = new ArrayList<>();
        initializeBoard();
    }

    /**
     * Initializes the board with all chess pieces in their starting positions.
     * Sets up pawns on ranks 2 and 7, and other pieces on ranks 1 and 8.
     */
    private void initializeBoard() {
        // Initialize pawns for both colors
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
     * Gets the piece at the specified position on the board.
     *
     * @param position the position to check
     * @return the piece at the position, or null if the square is empty
     */
    public Piece getPiece(Position position) {
        if (!isValidPosition(position)) {
            return null;
        }
        return squares[position.getRow()][position.getCol()];
    }

    /**
     * Sets the piece at the specified position on the board.
     * Used by undo logic to restore pieces to their previous squares.
     *
     * @param position the position to set
     * @param piece    the piece to place there (or null to clear)
     */
    public void setPiece(Position position, Piece piece) {
        if (!isValidPosition(position)) {
            return;
        }
        int row = position.getRow();
        int col = position.getCol();

        squares[row][col] = piece;

        if (piece != null) {
            // Keep the piece's internal position in sync with the board
            piece.setPosition(position);

            // If this piece was previously captured, remove it from the captured list
            capturedPieces.remove(piece);
        }
    }

    /**
     * Attempts to move a piece from one position to another.
     * Validates the move and handles captures, castling, and pawn promotion.
     * Prevents moves that would leave the king in check.
     *
     * @param from the starting position of the piece to move
     * @param to   the target position to move to
     * @return true if the move was successful, false if invalid
     */
    public boolean movePiece(Position from, Position to) {
        Piece piece = getPiece(from);

        if (piece == null) {
            return false;
        }

        // Handle castling moves
        if (piece instanceof King && Math.abs(from.getCol() - to.getCol()) == 2) {
            return handleCastling((King) piece, from, to);
        }

        if (!piece.isValidMove(squares, to)) {
            return false;
        }

        // PREVENT MOVES THAT PUT OWN KING IN CHECK
        if (wouldLeaveKingInCheck(piece, to)) {
            System.out.println("Invalid move: Would put your king in check!");
            return false;
        }

        Piece targetPiece = getPiece(to);
        if (targetPiece != null) {
            capturedPieces.add(targetPiece);
            System.out.println(piece.getColor() + " " + getPieceType(piece) + " captures " + targetPiece.getColor()
                    + " " + getPieceType(targetPiece) + "!");

            // END GAME IF KING IS CAPTURED
            if (targetPiece instanceof King) {
                return true; // Move successful and king was captured
            }
        }

        // Track king and rook movements for castling
        trackPieceMovement(piece, from);

        // Move the piece
        squares[to.getRow()][to.getCol()] = piece;
        squares[from.getRow()][from.getCol()] = null;
        piece.move(to);

        // Handle pawn promotion
        if (piece instanceof Pawn && ((Pawn) piece).isPromotionSquare(to)) {
            promotePawn(to, piece.getColor());
        }

        return true;
    }

    /**
     * Handles castling moves for both kingside and queenside.
     *
     * @param king the king piece being moved
     * @param from the king's starting position
     * @param to   the king's target position
     * @return true if castling was successful, false otherwise
     */
    private boolean handleCastling(King king, Position from, Position to) {
        String color = king.getColor();
        int row = from.getRow();

        // Check if king has moved
        if ((color.equals("white") && whiteKingMoved) || (color.equals("black") && blackKingMoved)) {
            return false;
        }

        // Kingside castling (O-O)
        if (to.getCol() == 6) {
            // Check if rook has moved
            if ((color.equals("white") && whiteRookKingSideMoved) ||
                    (color.equals("black") && blackRookKingSideMoved)) {
                return false;
            }

            // Check if squares between king and rook are empty
            if (squares[row][5] != null || squares[row][6] != null) {
                return false;
            }

            // Check if king would move through or into check
            if (wouldLeaveKingInCheck(king, new Position(row, 5)) ||
                    wouldLeaveKingInCheck(king, new Position(row, 6))) {
                return false;
            }

            // Perform castling
            Piece rook = squares[row][7];
            squares[row][6] = king;
            squares[row][5] = rook;
            squares[row][4] = null;
            squares[row][7] = null;
            king.move(new Position(row, 6));
            rook.move(new Position(row, 5));

        }
        // Queenside castling (O-O-O)
        else if (to.getCol() == 2) {
            // Check if rook has moved
            if ((color.equals("white") && whiteRookQueenSideMoved) ||
                    (color.equals("black") && blackRookQueenSideMoved)) {
                return false;
            }

            // Check if squares between king and rook are empty
            if (squares[row][1] != null || squares[row][2] != null || squares[row][3] != null) {
                return false;
            }

            // Check if king would move through or into check
            if (wouldLeaveKingInCheck(king, new Position(row, 3)) ||
                    wouldLeaveKingInCheck(king, new Position(row, 2))) {
                return false;
            }

            // Perform castling
            Piece rook = squares[row][0];
            squares[row][2] = king;
            squares[row][3] = rook;
            squares[row][4] = null;
            squares[row][0] = null;
            king.move(new Position(row, 2));
            rook.move(new Position(row, 3));
        } else {
            return false;
        }

        // Mark king as moved
        if (color.equals("white")) {
            whiteKingMoved = true;
        } else {
            blackKingMoved = true;
        }

        return true;
    }

    /**
     * Tracks movement of kings and rooks for castling eligibility.
     *
     * @param piece the piece that moved
     * @param from  the original position
     */
    private void trackPieceMovement(Piece piece, Position from) {
        String color = piece.getColor();

        if (piece instanceof King) {
            if (color.equals("white")) {
                whiteKingMoved = true;
            } else {
                blackKingMoved = true;
            }
        } else if (piece instanceof Rook) {
            if (color.equals("white")) {
                if (from.getCol() == 0)
                    whiteRookQueenSideMoved = true;
                if (from.getCol() == 7)
                    whiteRookKingSideMoved = true;
            } else {
                if (from.getCol() == 0)
                    blackRookQueenSideMoved = true;
                if (from.getCol() == 7)
                    blackRookKingSideMoved = true;
            }
        }
    }

    /**
     * Promotes a pawn to a queen when it reaches the opposite end of the board.
     *
     * @param position the position where promotion occurs
     * @param color    the color of the pawn being promoted
     */
    private void promotePawn(Position position, String color) {
        squares[position.getRow()][position.getCol()] = new Queen(color, position);
        System.out.println(color + " pawn promoted to Queen!");
    }

    /**
     * Checks if moving a piece to a new position would leave the player's king in
     * check.
     * Uses temporary move simulation to test the game state.
     *
     * @param piece the piece being moved
     * @param to    the target position
     * @return true if the move would leave the king in check, false otherwise
     */
    private boolean wouldLeaveKingInCheck(Piece piece, Position to) {
        // Store original state
        Position originalPos = piece.getPosition();
        Piece targetPiece = squares[to.getRow()][to.getCol()];

        // Make temporary move
        squares[to.getRow()][to.getCol()] = piece;
        squares[originalPos.getRow()][originalPos.getCol()] = null;
        piece.setPosition(to);

        // Check if king is in check after this move
        boolean inCheck = isCheck(piece.getColor());

        // Undo move
        squares[to.getRow()][to.getCol()] = targetPiece;
        squares[originalPos.getRow()][originalPos.getCol()] = piece;
        piece.setPosition(originalPos);

        return inCheck;
    }

    /**
     * Helper method to get the simple class name of a piece for display purposes.
     *
     * @param piece the piece to get the type of
     * @return the simple class name of the piece
     */
    private String getPieceType(Piece piece) {
        return piece.getClass().getSimpleName();
    }

    /**
     * Checks if the specified color's king is currently in check.
     *
     * @param color the color to check ("white" or "black")
     * @return true if the king is in check, false otherwise
     */
    public boolean isCheck(String color) {
        // Find the king
        Position kingPosition = findKing(color);
        if (kingPosition == null) {
            return false;
        }

        // Check if any opponent piece can attack the king
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null && !piece.getColor().equals(color)) {
                    if (piece.isValidMove(squares, kingPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Finds the position of the king of the specified color.
     *
     * @param color the color of the king to find ("white" or "black")
     * @return the position of the king, or null if not found
     */
    private Position findKing(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return piece.getPosition();
                }
            }
        }
        return null;
    }

    /**
     * Checks if the specified color is in checkmate (no legal moves to escape
     * check).
     *
     * @param color the color to check ("white" or "black")
     * @return true if the color is in checkmate, false otherwise
     */
    /**
     * Checks if the specified color is in checkmate (no legal moves to escape
     * check).
     *
     * @param color the color to check ("white" or "black")
     * @return true if the color is in checkmate, false otherwise
     */
    /**
     * Checks if the specified color is in checkmate (no legal moves to escape
     * check).
     *
     * @param color the color to check ("white" or "black")
     * @return true if the color is in checkmate, false otherwise
     */
    public boolean isCheckmate(String color) {
        // First, must be in check to be in checkmate
        if (!isCheck(color)) {
            return false;
        }

        // Check if any LEGAL move can get out of check
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    List<Position> possibleMoves = piece.possibleMoves(squares);

                    for (Position move : possibleMoves) {
                        // IMPORTANT: For kings attempting castling, skip if in check
                        // You cannot castle out of check
                        if (piece instanceof King && Math.abs(piece.getPosition().getCol() - move.getCol()) == 2) {
                            continue;
                        }

                        // Check if this move is actually legal (not blocked by friendly piece)
                        Piece targetPiece = squares[move.getRow()][move.getCol()];
                        if (targetPiece != null && targetPiece.getColor().equals(color)) {
                            // Can't move to square occupied by own piece
                            continue;
                        }

                        // Try the move
                        Position originalPosition = piece.getPosition();

                        // Make temporary move
                        squares[move.getRow()][move.getCol()] = piece;
                        squares[originalPosition.getRow()][originalPosition.getCol()] = null;
                        piece.setPosition(move);

                        boolean stillInCheck = isCheck(color);

                        // Undo the move
                        squares[move.getRow()][move.getCol()] = targetPiece;
                        squares[originalPosition.getRow()][originalPosition.getCol()] = piece;
                        piece.setPosition(originalPosition);

                        if (!stillInCheck) {
                            return false; // Found an escape move
                        }
                    }
                }
            }
        }

        // No escape moves found - it's checkmate
        return true;
    }

    /**
     * Checks if the specified color is in stalemate (no legal moves but not in
     * check).
     *
     * @param color the color to check ("white" or "black")
     * @return true if the color is in stalemate, false otherwise
     */
    public boolean isStalemate(String color) {
        if (isCheck(color)) {
            return false;
        }
        // Check if any legal move is available
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    if (!piece.possibleMoves(squares).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if a king was captured in any previous move.
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
     * Validates if a position is within the chessboard boundaries.
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
     * Displays the current state of the chessboard in the console.
     * Shows piece positions using algebraic notation with file (A-H) and rank (1-8)
     * labels.
     */
    private boolean isDarkSquare(int row, int col) {
        return ((row + col) % 2) == 1;
    }

    public void display() {
        System.out.println("  A  B  C  D  E  F  G  H");
        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece == null) {
                    System.out.print(isDarkSquare(row, col) ? "## " : "   ");
                } else {
                    System.out.print(piece.getSymbol() + " ");
                }
            }
            System.out.println(8 - row);
        }
        System.out.println("  A  B  C  D  E  F  G  H");
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
     * Gets the list of pieces that have been captured during the game.
     *
     * @return the list of captured pieces
     */
    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }
}
