package pieces;

import utils.Position;

/**
 * Rook piece - visual representation only, no movement rules
 */
public class Rook extends Piece {
    public Rook(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "R";
    }
}