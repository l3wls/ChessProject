package utils;
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
}