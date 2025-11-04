package pieces;

import utils.Position;

/**
 * Queen piece - visual representation only, no movement rules
 */
public class Queen extends Piece {
    public Queen(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "Q";
    }
}