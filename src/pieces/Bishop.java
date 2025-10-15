package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
* Represents a Bishop chess piece that moves diagonally.
* The Bishop can move any number of squares along diagonals.
*/
public class Bishop extends Piece {

    /**
    * Constructs a Bishop with specified color and position.
    *
    * @param color the color of the bishop ("white" or "black")
    * @param position the initial position of the bishop
    */
    public Bishop(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "B";
    }
    
    /**
    * Calculates all possible diagonal moves for the bishop.
    * The bishop moves in four diagonal directions until blocked.
    *
    * @param board the current chess board state
    * @return list of possible move positions
    */
    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();

        // // Up-left diagonal: row decreases, column decreases
        for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--) {
            if (!addRaySquare(board, moves, i, j))
                break;
        }

        // Up-right diagonal: row decreases, column increases
        for (int i = r - 1, j = c + 1; i >= 0 && j < 8; i--, j++) {
            if (!addRaySquare(board, moves, i, j))
                break;
        }

        // Down-left diagonal: row increases, column decreases
        for (int i = r + 1, j = c - 1; i < 8 && j >= 0; i++, j--) {
            if (!addRaySquare(board, moves, i, j))
                break;
        }

        // Down-right diagonal: row increases, column increases
        for (int i = r + 1, j = c + 1; i < 8 && j < 8; i++, j++) {
            if (!addRaySquare(board, moves, i, j))
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
    * @param row the target row
    * @param col the target column
    * @return true if ray should continue, false if ray should stop
    */
    private boolean addRaySquare(Piece[][] board, List<Position> moves, int row, int col) {
        Piece at = board[row][col];
        if (at == null) {
            //Empty square - add and continue ray
            moves.add(new Position(row, col));
            return true;
        } else {
            // Occupied square - add only if capturing opponent, then stop ray
            if (!at.color.equals(this.color)) {
                moves.add(new Position(row, col));
            }
            return false; // Stop the ray
        }
    }
}
