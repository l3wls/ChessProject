package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a King chess piece that moves one square in any direction.
 * The King is the most important piece - if captured, the game ends.
 * Can perform castling under certain conditions.
 */
public class King extends Piece {

    /**
     * Constructs a King with specified color and position.
     *
     * @param color    the color of the king ("white" or "black")
     * @param position the initial position of the king
     */
    public King(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "K";
    }

    /**
     * Calculates all possible moves for the king (one square in any direction).
     * The king moves to adjacent squares that are empty or contain opponent pieces.
     * Includes castling moves if eligible.
     *
     * @param squares the current chess board state
     * @return list of possible move positions
     */
    @Override
    public List<Position> possibleMoves(Piece[][] squares) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();

        // Check all 8 surrounding squares
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0)
                    continue; // Skip current position

                int nr = r + dr, nc = c + dc;

                // Check if new position is within board boundaries
                if (nr < 0 || nr >= 8 || nc < 0 || nc >= 8)
                    continue;

                Piece target = squares[nr][nc];
                // Add move if square is empty or contains opponent piece
                if (target == null || !target.getColor().equals(this.color)) {
                    moves.add(new Position(nr, nc));
                }
            }
        }

        // Add castling moves (these will be validated in Board.movePiece)
        if (c == 4) { // King is in starting position
            // Kingside castling
            moves.add(new Position(r, 6));
            // Queenside castling
            moves.add(new Position(r, 2));
        }

        return moves;
    }
}