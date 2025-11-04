package pieces;

import utils.Position;

/**
 * Bishop piece - visual representation only, no movement rules
 */
public class Bishop extends Piece {
    public Bishop(String color, Position position) {
        super(color, position);
        this.symbol = (color.equals("white") ? "w" : "b") + "B";
    }
}