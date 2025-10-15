package player;
import pieces.King;
import board.Board;
import utils.Position;
import java.util.Scanner;

/**
 * Represents a chess player with a specific color.
 * Handles player input for moves, including castling and basic move notation.
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
     * Supports standard move notation, castling (O-O and O-O-O), and basic pawn
     * promotion.
     *
     * @param board the game board
     * @return true if the move was successful, false otherwise
     */
    public boolean makeMove(Board board) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(color + " player's turn. Enter your move (e.g., E2 E4, O-O, O-O-O): ");
        String input = scanner.nextLine().trim();

        // Handle kingside castling notation (O-O)
        if (input.equalsIgnoreCase("O-O")) {
            return handleCastling(board, true);
        }
        // Handle queenside castling notation (O-O-O)
        else if (input.equalsIgnoreCase("O-O-O")) {
            return handleCastling(board, false);
        }

        // Handle pawn promotion notation (e.g., E7 E8=Q)
        if (input.contains("=")) {
            // For now, we auto-promote to Queen, so we'll just remove the promotion part
            input = input.split("=")[0].trim();
        }

        // Parse standard move notation
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            System.out.println("Invalid move format. Please use format like 'E2 E4', 'O-O', or 'O-O-O'.");
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

    /**
     * Handles castling moves by determining the appropriate king and rook
     * positions.
     *
     * @param board      the game board
     * @param isKingside true for kingside castling (O-O), false for queenside
     *                   (O-O-O)
     * @return true if castling was successful, false otherwise
     */
    private boolean handleCastling(Board board, boolean isKingside) {
        int row = color.equals("white") ? 7 : 0;
        Position kingFrom = new Position(row, 4);
        Position kingTo = new Position(row, isKingside ? 6 : 2);

        // Validate that the king is at the correct position
        if (board.getPiece(kingFrom) == null || !(board.getPiece(kingFrom) instanceof King)) {
            System.out.println("Castling not possible: King not in starting position.");
            return false;
        }

        // Attempt the castling move
        if (board.movePiece(kingFrom, kingTo)) {
            System.out.println(color + " castles " + (isKingside ? "kingside" : "queenside") + "!");
            return true;
        } else {
            System.out.println("Castling not possible.");
            return false;
        }
    }
}