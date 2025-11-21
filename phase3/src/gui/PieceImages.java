package gui;

import pieces.Piece;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages Unicode chess piece characters for the GUI.
 * Provides Unicode symbols for each chess piece type and color.
 */
public class PieceImages {
    // Unicode characters for chess pieces (White then Black)
    private static final String[] UNICODE_PIECES = {
            "\u2654", // White King
            "\u2655", // White Queen
            "\u2656", // White Rook
            "\u2657", // White Bishop
            "\u2658", // White Knight
            "\u2659", // White Pawn
            "\u265A", // Black King
            "\u265B", // Black Queen
            "\u265C", // Black Rook
            "\u265D", // Black Bishop
            "\u265E", // Black Knight
            "\u265F" // Black Pawn
    };

    private static final Map<String, String> pieceUnicodeMap = new HashMap<>();

    static {
        initializeUnicodeMap();
    }

    /**
     * Initializes the mapping between piece types and Unicode characters.
     */
    private static void initializeUnicodeMap() {
        // White pieces
        pieceUnicodeMap.put("wK", UNICODE_PIECES[0]); // White King
        pieceUnicodeMap.put("wQ", UNICODE_PIECES[1]); // White Queen
        pieceUnicodeMap.put("wR", UNICODE_PIECES[2]); // White Rook
        pieceUnicodeMap.put("wB", UNICODE_PIECES[3]); // White Bishop
        pieceUnicodeMap.put("wN", UNICODE_PIECES[4]); // White Knight
        pieceUnicodeMap.put("wP", UNICODE_PIECES[5]); // White Pawn

        // Black pieces
        pieceUnicodeMap.put("bK", UNICODE_PIECES[6]); // Black King
        pieceUnicodeMap.put("bQ", UNICODE_PIECES[7]); // Black Queen
        pieceUnicodeMap.put("bR", UNICODE_PIECES[8]); // Black Rook
        pieceUnicodeMap.put("bB", UNICODE_PIECES[9]); // Black Bishop
        pieceUnicodeMap.put("bN", UNICODE_PIECES[10]); // Black Knight
        pieceUnicodeMap.put("bP", UNICODE_PIECES[11]); // Black Pawn
    }

    /**
     * Gets the Unicode character for a chess piece.
     *
     * @param piece the chess piece
     * @return Unicode character representing the piece
     */
    public static String getPieceUnicode(Piece piece) {
        String key = piece.getSymbol();
        return pieceUnicodeMap.getOrDefault(key, "");
    }

    /**
     * Gets the Unicode character for a specific piece type and color.
     *
     * @param color     the piece color ("white" or "black")
     * @param pieceType the type of piece ("king", "queen", etc.)
     * @return Unicode character for the specified piece
     */
    public static String getPieceUnicode(String color, String pieceType) {
        String key = color.substring(0, 1) + pieceType.substring(0, 1).toUpperCase();
        return pieceUnicodeMap.getOrDefault(key, "");
    }
}