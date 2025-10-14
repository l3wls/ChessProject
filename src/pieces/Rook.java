package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "R";
    }

    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();

        // Up
        for (int i = r - 1; i >= 0; i--) {
            if (!addRaySquare(board, moves, i, c))
                break;
        }
        // Down
        for (int i = r + 1; i < 8; i++) {
            if (!addRaySquare(board, moves, i, c))
                break;
        }
        // Left
        for (int j = c - 1; j >= 0; j--) {
            if (!addRaySquare(board, moves, r, j))
                break;
        }
        // Right
        for (int j = c + 1; j < 8; j++) {
            if (!addRaySquare(board, moves, r, j))
                break;
        }

        return moves;
    }

    private boolean addRaySquare(Piece[][] board, List<Position> moves, int row, int col) {
        Piece at = board[row][col];
        if (at == null) {
            moves.add(new Position(row, col));
            return true;
        } else {
            if (!at.color.equals(this.color))
                moves.add(new Position(row, col));
            return false;
        }
    }
}
