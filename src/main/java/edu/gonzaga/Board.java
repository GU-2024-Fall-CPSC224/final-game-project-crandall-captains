package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel {

    private ArrayList<JButton> buttons; // Board tiles
    private JLabel statusLabel;        // Status display
    private Piece[][] board;           // Holds all pieces
    private Piece selectedPiece = null; // Track the currently selected piece
    private int selectedRow = -1;
    private int selectedCol = -1;

    public Board() {
        // Use BorderLayout for the main panel
        this.setLayout(new BorderLayout());

        buttons = new ArrayList<>();
        board = new Piece[8][8]; // Create an 8x8 chessboard
        statusLabel = new JLabel("Game On", SwingConstants.CENTER);

        initializePieces(); // Place the bishops on the board
        this.add(statusLabel, BorderLayout.NORTH);
        this.add(createButtonPanel(), BorderLayout.CENTER);
    }

    public Piece getPiece(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return null; // Out of bounds
        }
        return board[row][col];
    }    

    // Initialize pieces on the board
    private void initializePieces() {
        // Place white bishops
        board[7][2] = new Bishop("White", 7, 2); // C1
        board[7][5] = new Bishop("White", 7, 5); // F1

        // Place black bishops
        board[0][2] = new Bishop("Black", 0, 2); // C8
        board[0][5] = new Bishop("Black", 0, 5); // F8

        // Place white pawns
        for (int col = 0; col < 8; col++) {
            board[6][col] = new Pawn("White", 6, col);
        }

        // Place black pawns
        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn("Black", 1, col);
        }
    }

    // Create the chessboard UI
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 8)); // 8x8 chess grid

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();

                // Set alternating colors for chessboard pattern
                if ((row + col) % 2 == 0) {
                    button.setBackground(new Color(139, 69, 19));
                    button.setForeground(Color.CYAN);
                } else {
                    button.setBackground(new Color(222, 184, 135));
                    button.setForeground(Color.GREEN);
                }

                // Render the piece if there is one on this tile
                if (board[row][col] != null) {
                    button.setText(board[row][col].getSymbol()); // Use the Unicode symbol
                    button.setFont(new Font("Serif", Font.BOLD, 36)); // Large and bold font
                    button.setForeground(board[row][col].getColor().equalsIgnoreCase("White") ? Color.WHITE : Color.BLACK);
                }

                button.addActionListener(new ButtonClickListener(row, col));
                buttons.add(button);
                buttonPanel.add(button);
            }
        }

        return buttonPanel;
    }

    // Refresh the board UI after a move
    private void updateBoardUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = buttons.get(row * 8 + col);
                Piece piece = board[row][col];

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

    private class ButtonClickListener implements ActionListener {
        private final int row;
        private final int col;
    
        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedPiece == null) {
                // Select a piece
                Piece piece = getPiece(row, col);
                if (piece != null) {
                    selectedPiece = piece;
                    selectedRow = row;
                    selectedCol = col;
                    statusLabel.setText("Selected: " + piece.getSymbol() + " at " + (char) ('A' + col) + (8 - row));
                } else {
                    statusLabel.setText("No piece selected.");
                }
            } else {
                // Try to move the piece
                if (selectedPiece.isValidMove(row, col, Board.this)) {
                    // Move the piece
                    board[row][col] = selectedPiece;
                    board[selectedRow][selectedCol] = null;
                    selectedPiece.setPosition(row, col);
    
                    // Update UI
                    updateBoardUI();
                    statusLabel.setText("Moved to " + (char) ('A' + col) + (8 - row));
                } else {
                    statusLabel.setText("Invalid move. Try again.");
                }
    
                // Clear selection in either case
                selectedPiece = null;
                selectedRow = -1;
                selectedCol = -1;
            }
        }
    }
    
}
