package pieces;

import utils.Position;

/**
 * King piece - visual representation only, no movement rules
 */
public class King extends Piece {
    public King(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "K";
    }
}