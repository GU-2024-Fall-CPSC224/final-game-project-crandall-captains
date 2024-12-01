package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Board extends JPanel {

    private LogBoard logicalBoard; // Logical board representation
    private JLabel statusLabel;   // Status display
    private JButton[][] buttons;  // 8x8 grid of UI buttons
    private Piece selectedPiece = null;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean isWhiteTurn = true; // True means White's turn, false means Black's turn

    public Board() {
        logicalBoard = new LogBoard();
        logicalBoard.setupBoard(); // Initialize logical board with pieces

        setLayout(new BorderLayout());
        statusLabel = new JLabel("White's turn", SwingConstants.CENTER);
        buttons = new JButton[8][8];

        // Add components to the board
        add(statusLabel, BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.CENTER);
    }

    // Create the chessboard UI
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(8, 8));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();

                // Set alternating colors for chessboard pattern
                if ((row + col) % 2 == 0) {
                    button.setBackground(new Color(139, 69, 19)); // Dark brown
                } else {
                    button.setBackground(new Color(222, 184, 135)); // Light beige
                }

                // Get the piece from LogBoard for this square
                Square square = logicalBoard.getSquare(row, col);
                Piece piece = square.getPiece();
                if (piece != null) {
                    button.setText(piece.getSymbol()); // Unicode symbol
                    button.setFont(new Font("Serif", Font.BOLD, 36)); // Large and bold font
                    button.setForeground(piece.getColor().equalsIgnoreCase("White") ? Color.WHITE : Color.BLACK);
                }

                button.addActionListener(new ButtonClickListener(row, col));
                buttons[row][col] = button;
                buttonPanel.add(button);
            }
        }

        return buttonPanel;
    }

    // Refresh the board UI after a move
    private void updateBoardUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = buttons[row][col];
                Square square = logicalBoard.getSquare(row, col);
                Piece piece = square.getPiece();

                if (piece != null) {
                    button.setText(piece.getSymbol());
                    button.setFont(new Font("Serif", Font.BOLD, 36)); // Reapply font
                    button.setForeground(piece.getColor().equalsIgnoreCase("White") ? Color.WHITE : Color.BLACK);
                } else {
                    button.setText("");
                }
            }
        }
    }

    // Check if a king of the given color is in check
    private boolean isKingInCheck(String color) {
        int kingRow = -1, kingCol = -1;

        // Find the king
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = logicalBoard.getSquare(row, col);
                Piece piece = square.getPiece();
                if (piece instanceof King && piece.getColor().equalsIgnoreCase(color)) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        // Check if any opponent piece can attack the king
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = logicalBoard.getSquare(row, col);
                Piece piece = square.getPiece();
                if (piece != null && !piece.getColor().equalsIgnoreCase(color)) {
                    if (piece.isValidMove(kingRow, kingCol, logicalBoard)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // Simulate a move and check if it places the king in check
    private boolean simulateMoveAndCheck(int fromRow, int fromCol, int toRow, int toCol) {
        // Backup current state
        Piece originalFromPiece = logicalBoard.getSquare(fromRow, fromCol).getPiece();
        Piece originalToPiece = logicalBoard.getSquare(toRow, toCol).getPiece();

        // Simulate move
        logicalBoard.getSquare(toRow, toCol).setPiece(originalFromPiece);
        logicalBoard.getSquare(fromRow, fromCol).setPiece(null);
        originalFromPiece.setPosition(toRow, toCol);

        boolean isCheck = isKingInCheck(originalFromPiece.getColor());

        // Revert move
        logicalBoard.getSquare(fromRow, fromCol).setPiece(originalFromPiece);
        logicalBoard.getSquare(toRow, toCol).setPiece(originalToPiece);
        originalFromPiece.setPosition(fromRow, fromCol);

        return isCheck;
    }

    private class ButtonClickListener implements ActionListener {
        private final int row;
        private final int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Square clickedSquare = logicalBoard.getSquare(row, col);
            Piece clickedPiece = clickedSquare.getPiece();

            if (selectedPiece == null) {
                // Select a piece
                if (clickedPiece != null) {
                    if ((isWhiteTurn && clickedPiece.getColor().equalsIgnoreCase("White")) ||
                        (!isWhiteTurn && clickedPiece.getColor().equalsIgnoreCase("Black"))) {
                        selectedPiece = clickedPiece;
                        selectedRow = row;
                        selectedCol = col;
                        statusLabel.setText("Selected: " + clickedPiece.getSymbol());
                    } else {
                        statusLabel.setText("It's " + (isWhiteTurn ? "White's" : "Black's") + " turn!");
                    }
                } else {
                    statusLabel.setText("Empty square selected.");
                }
            } else {
                // Check if the move is valid
                if (selectedPiece.isValidMove(row, col, logicalBoard)) {
                    if (simulateMoveAndCheck(selectedRow, selectedCol, row, col)) {
                        statusLabel.setText("Invalid move: King would be in check.");
                    } else {
                        // Update LogBoard
                        logicalBoard.getSquare(row, col).setPiece(selectedPiece);
                        logicalBoard.getSquare(selectedRow, selectedCol).setPiece(null);
                        selectedPiece.setPosition(row, col);

                        // Update UI
                        updateBoardUI();

                        // Switch turn
                        isWhiteTurn = !isWhiteTurn;
                        statusLabel.setText((isWhiteTurn ? "White" : "Black") + "'s turn.");
                    }
                } else {
                    statusLabel.setText("Invalid move. Try again.");
                }

                // Clear selection
                selectedPiece = null;
            }
        }
    }
}
