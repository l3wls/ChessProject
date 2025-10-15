package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "N";
    }

    @Override
    public List<Position> possibleMoves(Piece[][] squares) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();
        int[][] d = { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 } };
        for (int[] v : d) {
            int nr = r + v[0], nc = c + v[1];
            if (nr < 0 || nr >= 8 || nc < 0 || nc >= 8)
                continue;
            Piece target = squares[nr][nc];
            if (target == null || !target.getColor().equals(this.color)) {
                moves.add(new Position(nr, nc));
            }
        }
        return moves;
    }
}
