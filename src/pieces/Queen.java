package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
* Represents a Queen chess piece - the most powerful piece. 
* The Queen combines the movement capabilities of Rook and Bishop.
*/
public class Queen extends Piece {

    /**
    * Constructs a Queen with specified color and position.
    *
    * @param color the color of the queen ("white" or "black")
    * @param position the initial position of the queen
    */
    public Queen(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "Q";
    }
    
    /**
    * Calculates all possible moves for the queen (horizontal, vertical, and
    * diagonal).
    * The queen can move any number of squares in straight lines.
    *
    * @param board the current chess board state
    * @return list of possible move positions
    */

    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();

        // Rook-like movement (horizontal and vertical)
        for (int i = r - 1; i >= 0; i--)
            if (!addRay(board, moves, i, c))
                break; // up
        for (int i = r + 1; i < 8; i++)
            if (!addRay(board, moves, i, c))
                break; // down
        for (int j = c - 1; j >= 0; j--)
            if (!addRay(board, moves, r, j))
                break; // left
        for (int j = c + 1; j < 8; j++)
            if (!addRay(board, moves, r, j))
                break; // right

        // // Bishop-like movement (diagonal)
        for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--)
            if (!addRay(board, moves, i, j))
                break;
        for (int i = r - 1, j = c + 1; i >= 0 && j < 8; i--, j++)
            if (!addRay(board, moves, i, j))
                break;
        for (int i = r + 1, j = c - 1; i < 8 && j >= 0; i++, j--)
            if (!addRay(board, moves, i, j))
                break;
        for (int i = r + 1, j = c + 1; i < 8 && j < 8; i++, j++)
            if (!addRay(board, moves, i, j))
                break;

        return moves;
    }
    
    /**
    * Helper method to add a square to possible moves along a ray.
    * Stops the ray if a piece is encountered.
    *
    * @param board the chess board
    * @param moves list to add valid moves to
    * @param row the target row
    * @param col the target column
    * @return true if ray should continue, false if ray should stop
    */
    private boolean addRay(Piece[][] board, List<Position> moves, int row, int col) {
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
