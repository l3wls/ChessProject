package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Pawn chess piece with unique movement rules.
 * Pawns move forward but capture diagonally, and can move two squares on first
 * move.
 * Promotes to Queen when reaching the opposite end of the board.
 */
public class Pawn extends Piece {

    /**
     * Constructs a Pawn with specified color and position.
     *
     * @param color    the color of the pawn ("white" or "black")
     * @param position the initial position of the pawn
     */
    public Pawn(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "P";
    }

    /**
     * Calculates all possible moves for the pawn.
     * Includes forward movement and diagonal captures.
     *
     * @param board the current chess board state
     * @return list of possible move positions
     */
    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();

        // Determine movement direction based on color
        // White moves up (decreasing row), Black moves down (increasing row)
        int direction = color.equals("white") ? -1 : 1;
        int startRow = color.equals("white") ? 6 : 1;

        // Forward movement (one square)
        int oneR = r + direction;
        if (inBoard(oneR, c) && board[oneR][c] == null) {
            moves.add(new Position(oneR, c));

            // Double move from starting position
            int twoR = r + 2 * direction;
            if (r == startRow && inBoard(twoR, c) && board[twoR][c] == null) {
                moves.add(new Position(twoR, c));
            }
        }

        // Diagonal captures
        int[] captureColumns = { -1, 1 }; // Left and right diagonals
        for (int dc : captureColumns) {
            int nr = r + direction, nc = c + dc;
            if (inBoard(nr, nc)) {
                Piece target = board[nr][nc];
                // Can capture if there's an opponent piece diagonally
                if (target != null && !target.color.equals(this.color)) {
                    moves.add(new Position(nr, nc));
                }
            }
        }

        return moves;
    }

    /**
     * Checks if the pawn has reached the promotion rank.
     *
     * @param to the target position
     * @return true if the pawn should be promoted
     */
    public boolean isPromotionSquare(Position to) {
        int lastRank = color.equals("white") ? 0 : 7; // White promotes on rank 1, Black on rank 8
        return to.getRow() == lastRank;
    }

    /**
     * Checks if a position is within the board boundaries.
     *
     * @param row the row to check
     * @param col the column to check
     * @return true if the position is valid
     */
    private boolean inBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}