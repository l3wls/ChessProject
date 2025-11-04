package pieces;

import utils.Position;

/**
 * Pawn piece - visual representation only, no movement rules
 */
public class Pawn extends Piece {
    public Pawn(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "P";
    }
}