package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "K";
    }

    @Override
    public List<Position> possibleMoves(Piece[][] squares) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0)
                    continue;
                int nr = r + dr, nc = c + dc;
                if (nr < 0 || nr >= 8 || nc < 0 || nc >= 8)
                    continue;
                Piece target = squares[nr][nc];
                if (target == null || !target.getColor().equals(this.color)) {
                    moves.add(new Position(nr, nc));
                }
            }
        }
        return moves;
    }
}
