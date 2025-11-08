package gui;

import board.Board;
import game.ChessGame;
import pieces.Piece;
import utils.Position;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {
    private ChessGame chessGame;
    private ChessGUI chessGUI;
    private JLabel[][] squares;
    private Position selectedSquare;

    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 80;

    public BoardPanel(ChessGame chessGame, ChessGUI chessGUI) {
        this.chessGame = chessGame;
        this.chessGUI = chessGUI;
        this.squares = new JLabel[BOARD_SIZE][BOARD_SIZE];
        this.selectedSquare = null;

        initializeBoard();
        setupInteraction();
    }

    private void initializeBoard() {
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        setPreferredSize(new Dimension(SQUARE_SIZE * BOARD_SIZE, SQUARE_SIZE * BOARD_SIZE));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JLabel square = new JLabel("", JLabel.CENTER);
                square.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                square.setOpaque(true);
                square.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                square.setFont(new Font("Segoe UI Symbol", Font.BOLD, 36));

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

    private void handleSquareClick(int row, int col) {
        Position clickedPosition = new Position(row, col);

        if (selectedSquare == null) {
            // First click - select piece
            Piece piece = chessGame.getBoard().getPiece(clickedPosition);
            if (piece != null) {
                selectedSquare = clickedPosition;
                highlightSquare(row, col, true);
            }
        } else {
            // Second click - move piece
            if (!selectedSquare.equals(clickedPosition)) {
                // Check if there's a piece to capture BEFORE moving
                Piece targetPiece = chessGame.getBoard().getPiece(clickedPosition);
                boolean moveSuccessful = chessGame.makeMove(selectedSquare, clickedPosition);

                if (moveSuccessful) {
                    String moveDescription = formatMove(selectedSquare, clickedPosition);
                    chessGUI.addMoveToHistory(moveDescription);

                    // If there was a piece at the target, it was captured
                    if (targetPiece != null) {
                        String captureText = targetPiece.getColor() + " " +
                                targetPiece.getClass().getSimpleName();
                        chessGUI.addCapturedPiece(captureText);
                    }

                    updateBoard();

                    if (chessGame.isKingCaptured()) {
                        chessGUI.showGameOver(chessGame.getWinner());
                    }
                }

                highlightSquare(selectedSquare.getRow(), selectedSquare.getCol(), false);
                selectedSquare = null;
            } else {
                highlightSquare(row, col, false);
                selectedSquare = null;
            }
        }
    }

    private void highlightSquare(int row, int col, boolean highlight) {
        Color originalColor = ((row + col) % 2 == 0) ? chessGUI.getLightSquareColor() : chessGUI.getDarkSquareColor();
        Color highlightColor = highlight ? Color.YELLOW : originalColor;
        squares[row][col].setBackground(highlightColor);
    }

    public void updateBoard() {
        Board board = chessGame.getBoard();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Position position = new Position(row, col);
                Piece piece = board.getPiece(position);
                JLabel square = squares[row][col];

                if ((row + col) % 2 == 0) {
                    square.setBackground(chessGUI.getLightSquareColor());
                } else {
                    square.setBackground(chessGUI.getDarkSquareColor());
                }

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

    private String formatMove(Position from, Position to) {
        Piece piece = chessGame.getBoard().getPiece(to);
        String pieceName = piece != null ? piece.getClass().getSimpleName() : "Piece";
        return pieceName + " " + from.toString() + " → " + to.toString();
    }
}