package board;

import pieces.*;
import java.util.ArrayList;
import java.util.List;
import utils.Position;

public class Board {
    private Piece[][] squares;
    private List<Piece> capturedPieces;

    public Board() {
        squares = new Piece[8][8];
        capturedPieces = new ArrayList<>();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int col = 0; col < 8; col++) {
            squares[1][col] = new Pawn("black", new Position(1, col));
            squares[6][col] = new Pawn("white", new Position(6, col));
        }

        squares[0][0] = new Rook("black", new Position(0, 0));
        squares[0][1] = new Knight("black", new Position(0, 1));
        squares[0][2] = new Bishop("black", new Position(0, 2));
        squares[0][3] = new Queen("black", new Position(0, 3));
        squares[0][4] = new King("black", new Position(0, 4));
        squares[0][5] = new Bishop("black", new Position(0, 5));
        squares[0][6] = new Knight("black", new Position(0, 6));
        squares[0][7] = new Rook("black", new Position(0, 7));

        squares[7][0] = new Rook("white", new Position(7, 0));
        squares[7][1] = new Knight("white", new Position(7, 1));
        squares[7][2] = new Bishop("white", new Position(7, 2));
        squares[7][3] = new Queen("white", new Position(7, 3));
        squares[7][4] = new King("white", new Position(7, 4));
        squares[7][5] = new Bishop("white", new Position(7, 5));
        squares[7][6] = new Knight("white", new Position(7, 6));
        squares[7][7] = new Rook("white", new Position(7, 7));
    }

    public Piece getPiece(Position position) {
        if (!isValidPosition(position)) {
            return null;
        }
        return squares[position.getRow()][position.getCol()];
    }

    public boolean movePiece(Position from, Position to) {
        Piece piece = getPiece(from);
        if (piece == null) {
            return false;
        }
        if (!piece.isValidMove(squares, to)) {
            return false;
        }
        Piece targetPiece = getPiece(to);
        if (targetPiece != null) {
            capturedPieces.add(targetPiece);
            System.out.println(piece.getColor() + " " + getPieceType(piece) + " captures " + targetPiece.getColor()
                    + " " + getPieceType(targetPiece) + "!");
        }
        squares[to.getRow()][to.getCol()] = piece;
        squares[from.getRow()][from.getCol()] = null;
        piece.move(to);
        if (piece instanceof Pawn) {
            String color = piece.getColor();
            if ((color.equals("white") && to.getRow() == 0) || (color.equals("black") && to.getRow() == 7)) {
                squares[to.getRow()][to.getCol()] = new Queen(color, to);
            }
        }
        return true;
    }

    private String getPieceType(Piece piece) {
        return piece.getClass().getSimpleName();
    }

    public boolean isCheck(String color) {
        Position kingPosition = findKing(color);
        if (kingPosition == null) {
            return false;
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null && !piece.getColor().equals(color)) {
                    if (piece.isValidMove(squares, kingPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Position findKing(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return piece.getPosition();
                }
            }
        }
        return null;
    }

    public boolean isCheckmate(String color) {
        if (!isCheck(color)) {
            return false;
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    for (Position move : piece.possibleMoves(squares)) {
                        Piece originalPiece = squares[move.getRow()][move.getCol()];
                        Position originalPosition = piece.getPosition();
                        squares[move.getRow()][move.getCol()] = piece;
                        squares[originalPosition.getRow()][originalPosition.getCol()] = null;
                        piece.setPosition(move);
                        boolean stillInCheck = isCheck(color);
                        squares[move.getRow()][move.getCol()] = originalPiece;
                        squares[originalPosition.getRow()][originalPosition.getCol()] = piece;
                        piece.setPosition(originalPosition);
                        if (!stillInCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isStalemate(String color) {
        if (isCheck(color)) {
            return false;
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    if (!piece.possibleMoves(squares).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isValidPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public void display() {
        System.out.println("  A  B  C  D  E  F  G  H");
        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece == null) {
                    System.out.print("## ");
                } else {
                    System.out.print(piece.getSymbol() + " ");
                }
            }
            System.out.println((8 - row));
        }
        System.out.println("  A  B  C  D  E  F  G  H");
    }

    public Piece[][] getSquares() {
        return squares;
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }
}
