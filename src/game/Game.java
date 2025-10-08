package game;

import board.Board;
import player.Player;
public class Game {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private String currentTurn;

    public Game() {
        board = new Board();
        whitePlayer = new Player("white");
        blackPlayer = new Player("black");
        currentTurn = "white";
    }

    public void start() {
        System.out.println("Welcome to Chess!");
        System.out.println("Enter moves in the format: [FROM] [TO] (e.g., E2 E4)");
        System.out.println();

    }
}