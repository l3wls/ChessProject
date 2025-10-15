package player;

import board.Board;
import utils.Position;
import java.util.Scanner;

/**
* Represents a chess player with a specific color.
*/
public class Player {
    private String color;

    /**
    * Constructs a Player object with the specified color.
    *
    * @param color the color of the player ("white" or "black")
    */
    public Player(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
    
    /**
    * Allows the player to input a move and attempts to execute it on the board.
    *
    * @param board the game board
    * @return true if the move was successful, false otherwise
    */
    public boolean makeMove(Board board) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(color + " player's turn. Enter your move (e.g., E2 E4): ");
        String input = scanner.nextLine().trim();

        // Handle castling notation (optional)
        if (input.equalsIgnoreCase("O-O")) {
            System.out.println("Kingside castling not implemented yet.");
            return false;
        } else if (input.equalsIgnoreCase("O-O-O")) {
            System.out.println("Queenside castling not implemented yet.");
            return false;
        }

        // Handle pawn promotion notation (optional)
        if (input.contains("=")) {
            System.out.println("Pawn promotion not implemented yet.");
            return false;
        }

        // Parse standard move notation
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            System.out.println("Invalid move format. Please use format like 'E2 E4'.");
            return false;
        }

        try {
            Position from = new Position(parts[0]);
            Position to = new Position(parts[1]);

            // Validate that the piece belongs to the player
            if (board.getPiece(from) == null || !board.getPiece(from).getColor().equals(color)) {
                System.out.println("You can only move your own pieces.");
                return false;
            }

            // Attempt to move the piece
            if (board.movePiece(from, to)) {
                return true;
            } else {
                System.out.println("Invalid move. Please try again.");
                return false;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid position: " + e.getMessage());
            return false;
        }
    }
}