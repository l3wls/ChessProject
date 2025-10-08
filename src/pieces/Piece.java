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
}