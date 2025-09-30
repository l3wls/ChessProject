package board;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private Piece[][] squares;
    private List<Piece> capturedPiece;

    public Board(){
        squares = new Piece[8][8];
        capturedPiece = new ArrayList<>();
        initializeBoard();
    }

    public void initialBoard(){
        for(int col = 0; col < 8; col++){
            squares[1][col] = new Pawn("black", new Position(1, col));
            squares[6][col] = new Pawn("white", new Position(6, col));
        }

        squares[0][0] = new Rook("black", new Position(0,0));
        squares[0][1] = new Knight("black", new Position(0,1));
        squares[0][2] = new Bishop("black", new Position(0,2));
        squares[0][3] = new Queen("black", new Position(0,3));
        squares[0][4] = new King("black", new Position(0,4));
        squares[0][5] = new Bishop("black", new Position(0,5));
        squares[0][6] = new Knight("black", new Position(0,6));
        squares[0][7] = new Rook("black", new Position(0,7));

        squares[0][0] = new Rook("white", new Position(7, 0));
        squares[0][1] = new Knight("white", new Position(7, 1));
        squares[0][2] = new Bishop("white", new Position(7, 2));
        squares[0][3] = new Queen("white", new Position(7, 3));
        squares[0][4] = new King("white", new Position(7, 4));
        squares[0][5] = new Bishop("white", new Position(7, 5));
        squares[0][6] = new Knight("white", new Position(7, 6));
        squares[0][7] = new Rook("white", new Position(7, 7));
    }
}