package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
* Represents a Knight chess piece that moves in an L-shape.
* The Knight is the only piece that can jump over other pieces.
*/
public class Knight extends Piece {

    /**
    * Constructs a Knight with specified color and position.
    *
    * @param color the color of the knight ("white" or "black")
    * @param position the initial position of the knight
    */
    public Knight(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "N";
    }

    /**
    * Calculates all possible L-shaped moves for the knight.
    * The knight moves two squares in one direction and one square perpendicular.
    *
    * @param squares the current chess board state
    * @return list of possible move positions
    */
    @Override
    public List<Position> possibleMoves(Piece[][] squares) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();

        // All 8 possible L-shaped moves
        int[][] d = { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 } };                                                                                        // Down-left
        for (int[] v : d) {
            int nr = r + v[0], nc = c + v[1];

            // Check if new position is within board boundaries
            if (nr < 0 || nr >= 8 || nc < 0 || nc >= 8)
                continue;
            Piece target = squares[nr][nc];
            // Add move if square is empty or contains opponent piece
            if (target == null || !target.getColor().equals(this.color)) {
                moves.add(new Position(nr, nc));
            }
        }
        return moves;
    }
}
