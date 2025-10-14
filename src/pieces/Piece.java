package pieces;

import utils.Position;
import java.util.List;

public abstract class Piece {
    protected String color;
    protected Position position;
    protected String symbol;

    public Piece(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract List<Position> possibleMoves(Piece[][] board);

    public boolean isValidMove(Piece[][] board, Position target) {
        return possibleMoves(board).contains(target);
    }

    public void move(Position newPosition) {
        this.position = newPosition;
    }

    @Override
    public String toString() {
        return symbol;
    }
}