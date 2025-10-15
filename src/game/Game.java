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

        play();
    }

    public void end(String message) {
        System.out.println(message);
        System.out.println("Game over!");
        System.exit(0);
    }

    public void play() {
        while (true) {
            board.display();

            // Check for checkmate or stalemate
            if (board.isCheckmate(currentTurn)) {
                String winner = currentTurn.equals("white") ? "black" : "white";
                end("Checkmate! " + winner + " wins!");
            } else if (board.isStalemate(currentTurn)) {
                end("Stalemate! The game is a draw.");
            }

            // Check for check
            if (board.isCheck(currentTurn)) {
                System.out.println(currentTurn + " is in check!");
            }

            // Get move from current player
            boolean moveSuccessful = false;
            if (currentTurn.equals("white")) {
                moveSuccessful = whitePlayer.makeMove(board);
            } else {
                moveSuccessful = blackPlayer.makeMove(board);
            }

            // After a successful move, check if a king was captured
            if (moveSuccessful) {
                // Check if the move resulted in a king capture
                if (board.isKingCaptured()) {
                    String winner = board.getWinner();
                    end("King captured! " + winner + " wins the game!");
                }
                currentTurn = currentTurn.equals("white") ? "black" : "white";
            }

            System.out.println();
        }
    }
}