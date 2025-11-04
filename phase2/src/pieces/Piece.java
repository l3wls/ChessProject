package pieces;

import utils.Position;

/**
 * SIMPLIFIED abstract Piece class for Phase 2.
 * Contains only visual properties - no movement logic.
 */
public abstract class Piece {
    protected String color;
    protected Position position;
    protected String symbol;

    /**
     * Constructs a Piece with specified color and position.
     *
     * @param color    the color of the piece ("white" or "black")
     * @param position the position of the piece
     */
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

    @Override
    public String toString() {
        return symbol;
    }
}