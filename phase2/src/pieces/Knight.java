package pieces;

import utils.Position;

/**
 * Knight piece - visual representation only, no movement rules
 */
public class Knight extends Piece {
    public Knight(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "N";
    }
}