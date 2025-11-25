package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Rook chess piece that moves horizontally and vertically.
 * The Rook can move any number of squares along rows or columns.
 */
public class Rook extends Piece {

    /**
     * Constructs a Rook with specified color and position.
     *
     * @param color    the color of the rook ("white" or "black")
     * @param position the initial position of the rook
     */
    public Rook(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "R";
    }

    /**
     * Calculates all possible horizontal and vertical moves for the rook.
     * The rook moves in four straight directions until blocked.
     *
     * @param board the current chess board state
     * @return list of possible move positions
     */
    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();

        // Up: decrease row, same column
        for (int i = r - 1; i >= 0; i--) {
            if (!addRaySquare(board, moves, i, c))
                break;
        }
        // Down: increase row, same column
        for (int i = r + 1; i < 8; i++) {
            if (!addRaySquare(board, moves, i, c))
                break;
        }
        // Left: same row, decrease column
        for (int j = c - 1; j >= 0; j--) {
            if (!addRaySquare(board, moves, r, j))
                break;
        }
        // Right: same row, increase column
        for (int j = c + 1; j < 8; j++) {
            if (!addRaySquare(board, moves, r, j))
                break;
        }

        return moves;
    }

    /**
     * Helper method to add a square to possible moves along a ray.
     * Stops the ray if a piece is encountered.
     *
     * @param board the chess board
     * @param moves list to add valid moves to
     * @param row   the target row
     * @param col   the target column
     * @return true if ray should continue, false if ray should stop
     */

    private boolean addRaySquare(Piece[][] board, List<Position> moves, int row, int col) {
        Piece at = board[row][col];
        if (at == null) {
            // Empty square - add and continue ray
            moves.add(new Position(row, col));
            return true;
        } else {
            // Occupied square - add only if capturing opponent, then stop ray
            if (!at.color.equals(this.color))
                moves.add(new Position(row, col));
            return false; // Stop the ray
        }
    }
}
