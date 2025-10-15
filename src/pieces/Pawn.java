package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "P";
    }

    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();

        int dir = color.equals("white") ? -1 : 1;
        int startRow = color.equals("white") ? 6 : 1;

        int oneR = r + dir;
        if (inBoard(oneR, c) && board[oneR][c] == null) {
            moves.add(new Position(oneR, c));

            int twoR = r + 2 * dir;
            if (r == startRow && inBoard(twoR, c) && board[twoR][c] == null) {
                moves.add(new Position(twoR, c));
            }
        }

        int[] dcs = { -1, 1 };
        for (int dc : dcs) {
            int nr = r + dir, nc = c + dc;
            if (inBoard(nr, nc)) {
                Piece target = board[nr][nc];
                if (target != null && !target.color.equals(this.color)) {
                    moves.add(new Position(nr, nc));
                }
            }
        }

        return moves;
    }

    public boolean isPromotionSquare(Position to) {
        int lastRank = color.equals("white") ? 0 : 7;
        return to.getRow() == lastRank;
    }

    private boolean inBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
