package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "Q";
    }

    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();

        // Rook-like rays
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

        // Bishop-like rays
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

    private boolean addRay(Piece[][] board, List<Position> moves, int row, int col) {
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
