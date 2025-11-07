package gui;

import board.Board;
import game.ChessGame;
import pieces.Piece;
import utils.Position;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Panel that displays the chess board and handles piece interactions.
 * Uses Unicode characters for chess pieces and supports click-to-move.
 */
public class BoardPanel extends JPanel {
    private ChessGame chessGame;
    private ChessGUI chessGUI;
    private JLabel[][] squares;
    private Position selectedSquare;

    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 80;

    /**
     * Constructs the board panel with the specified game and GUI references.
     *
     * @param chessGame the chess game engine
     * @param chessGUI  the main GUI window
     */
    public BoardPanel(ChessGame chessGame, ChessGUI chessGUI) {
        this.chessGame = chessGame;
        this.chessGUI = chessGUI;
        this.squares = new JLabel[BOARD_SIZE][BOARD_SIZE];
        this.selectedSquare = null;

        initializeBoard();
        setupInteraction();
    }

    /**
     * Initializes the chess board with squares and pieces.
     */
    private void initializeBoard() {
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        setPreferredSize(new Dimension(SQUARE_SIZE * BOARD_SIZE, SQUARE_SIZE * BOARD_SIZE));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Create squares
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JLabel square = new JLabel("", JLabel.CENTER);
                square.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                square.setOpaque(true);
                square.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                square.setFont(new Font("Segoe UI Symbol", Font.BOLD, 36));

                // Set square color based on position
                if ((row + col) % 2 == 0) {
                    square.setBackground(chessGUI.getLightSquareColor());
                } else {
                    square.setBackground(chessGUI.getDarkSquareColor());
                }

                squares[row][col] = square;
                add(square);
            }
        }

        updateBoard();
    }

    /**
     * Sets up mouse interactions for the board squares.
     */
    private void setupInteraction() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                final int currentRow = row;
                final int currentCol = col;

                squares[row][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        handleSquareClick(currentRow, currentCol);
                    }
                });
            }
        }
    }

    /**
     * Handles clicks on chess board squares.
     *
     * @param row the row of the clicked square
     * @param col the column of the clicked square
     */
    private void handleSquareClick(int row, int col) {
        Position clickedPosition = new Position(row, col);

        if (selectedSquare == null) {
            // First click - select piece
            Piece piece = chessGame.getBoard().getPiece(clickedPosition);
            if (piece != null) {
                selectedSquare = clickedPosition;
                highlightSquare(row, col, true);
                chessGUI.playMoveSound();
            }
        } else {
            // Second click - move piece
            if (!selectedSquare.equals(clickedPosition)) {
                boolean moveSuccessful = chessGame.makeMove(selectedSquare, clickedPosition);

                if (moveSuccessful) {
                    String moveDescription = formatMove(selectedSquare, clickedPosition);
                    chessGUI.addMoveToHistory(moveDescription);
                    updateBoard();

                    // Check for game over
                    if (chessGame.isKingCaptured()) {
                        chessGUI.showGameOver(chessGame.getWinner());
                    }
                }

                // Clear selection regardless of move success
                highlightSquare(selectedSquare.getRow(), selectedSquare.getCol(), false);
                selectedSquare = null;
            } else {
                // Clicked same square - deselect
                highlightSquare(row, col, false);
                selectedSquare = null;
            }
        }
    }

    /**
     * Highlights or unhighlights a square on the board.
     *
     * @param row       the row of the square
     * @param col       the column of the square
     * @param highlight true to highlight, false to remove highlight
     */
    private void highlightSquare(int row, int col, boolean highlight) {
        Color originalColor = ((row + col) % 2 == 0) ? chessGUI.getLightSquareColor() : chessGUI.getDarkSquareColor();
        Color highlightColor = highlight ? Color.YELLOW : originalColor;

        squares[row][col].setBackground(highlightColor);
    }

    /**
     * Updates the entire board display with current piece positions.
     */
    public void updateBoard() {
        Board board = chessGame.getBoard();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Position position = new Position(row, col);
                Piece piece = board.getPiece(position);
                JLabel square = squares[row][col];

                // Reset square color
                if ((row + col) % 2 == 0) {
                    square.setBackground(chessGUI.getLightSquareColor());
                } else {
                    square.setBackground(chessGUI.getDarkSquareColor());
                }

                // Set piece Unicode character or clear
                if (piece != null) {
                    String unicode = PieceImages.getPieceUnicode(piece);
                    square.setText(unicode);
                } else {
                    square.setText("");
                }
            }
        }

        repaint();
    }

    /**
     * Updates board colors when theme changes.
     */
    public void updateBoardColors() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 == 0) {
                    squares[row][col].setBackground(chessGUI.getLightSquareColor());
                } else {
                    squares[row][col].setBackground(chessGUI.getDarkSquareColor());
                }
            }
        }
        repaint();
    }

    /**
     * Formats a move for display in the history panel.
     *
     * @param from starting position
     * @param to   target position
     * @return formatted move string
     */
    private String formatMove(Position from, Position to) {
        Piece piece = chessGame.getBoard().getPiece(to);
        String pieceName = piece != null ? piece.getClass().getSimpleName() : "Piece";
        return pieceName + " " + from.toString() + " → " + to.toString();
    }
}