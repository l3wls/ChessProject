package utils;

/**
 * Represents a position on the chessboard with row and column coordinates.
 * Converts between array indices and chess notation.
 */
public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position(String chessNotation) {
        if (chessNotation.length() != 2) {
            throw new IllegalArgumentException("Invalid chess notation: " + chessNotation);
        }

        char colChar = Character.toUpperCase(chessNotation.charAt(0));
        char rowChar = chessNotation.charAt(1);

        if (colChar < 'A' || colChar > 'H' || rowChar < '1' || rowChar > '8') {
            throw new IllegalArgumentException("Invalid chess notation: " + chessNotation);
        }

        this.col = colChar - 'A';
        this.row = '8' - rowChar;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String toChessNotation() {
        return String.valueOf((char) ('A' + col)) + (8 - row);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Position position = (Position) obj;
        return row == position.row && col == position.col;
    }

    @Override
    public String toString() {
        return toChessNotation();
    }
}