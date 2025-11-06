package pieces;

import utils.Position;
import java.util.List;

/**
* Abstract class representing a chess piece.
*/
public abstract class Piece {
    protected String color;
    protected Position position;
    protected String symbol;

    /**
    * Constructs a Piece object with specified color and position.
    *
    * @param color the color of the piece ("white" or "black")
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

    /**
    * Calculates all possible moves for this piece on the given board.
    *
    * @param board the current board state
    * @return a list of possible moves
    */
    public abstract List<Position> possibleMoves(Piece[][] board);
    
    /**
    * Checks if the move to the target position is valid.
    *
    * @param board the current board state
    * @param target the target position
    * @return true if the move is valid, false otherwise
    */
    public boolean isValidMove(Piece[][] board, Position target) {
        return possibleMoves(board).contains(target);
    }

    /**
    * Moves the piece to the new position.
    *
    * @param newPosition the new position to move to
    */
    public void move(Position newPosition) {
        this.position = newPosition;
    }

    @Override
    public String toString() {
        return symbol;
    }
}