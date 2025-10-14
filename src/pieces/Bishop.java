package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "B";
    }

    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();

        // Up-left
        for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--) {
            if (!addRaySquare(board, moves, i, j))
                break;
        }

        // Up-right
        for (int i = r - 1, j = c + 1; i >= 0 && j < 8; i--, j++) {
            if (!addRaySquare(board, moves, i, j))
                break;
        }

        // Down-left
        for (int i = r + 1, j = c - 1; i < 8 && j >= 0; i++, j--) {
            if (!addRaySquare(board, moves, i, j))
                break;
        }

        // Down-right
        for (int i = r + 1, j = c + 1; i < 8 && j < 8; i++, j++) {
            if (!addRaySquare(board, moves, i, j))
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
            if (!at.color.equals(this.color)) {
                moves.add(new Position(row, col));
            }
            return false;
        }
    }
}
