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

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(8, 8));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();

                if ((row + col) % 2 == 0) {
                    button.setBackground(new Color(139, 69, 19)); // Dark brown
                } else {
                    button.setBackground(new Color(222, 184, 135)); // Light beige
                }

                Square square = logicalBoard.getSquare(row, col);
                Piece piece = square.getPiece();
                if (piece != null) {
                    button.setText(piece.getSymbol());
                    button.setFont(new Font("Serif", Font.BOLD, 36));
                    button.setForeground(piece.getColor().equalsIgnoreCase("White") ? Color.WHITE : Color.BLACK);
                }

                button.addActionListener(new ButtonClickListener(row, col));
                buttons[row][col] = button;
                buttonPanel.add(button);
            }
        }

        return buttonPanel;
    }

    private void updateBoardUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = buttons[row][col];
                Square square = logicalBoard.getSquare(row, col);
                Piece piece = square.getPiece();

                if (piece != null) {
                    button.setText(piece.getSymbol());
                    button.setFont(new Font("Serif", Font.BOLD, 36));
                    button.setForeground(piece.getColor().equalsIgnoreCase("White") ? Color.WHITE : Color.BLACK);
                } else {
                    button.setText("");
                }
            }
        }
    }

    private boolean isKingInCheck(String color) {
        int kingRow = -1, kingCol = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = logicalBoard.getSquare(row, col);
                if (square == null) continue; // Skip null squares

                Piece piece = square.getPiece();
                if (piece instanceof King && piece.getColor().equalsIgnoreCase(color)) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        if (kingRow == -1 || kingCol == -1) {
            return false; // No king found
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = logicalBoard.getSquare(row, col);
                if (square == null) continue;

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

    private boolean hasValidMoves(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = logicalBoard.getSquare(row, col);
                if (square == null) continue; // Skip null squares

                Piece piece = square.getPiece();
                if (piece != null && piece.getColor().equalsIgnoreCase(color)) {
                    for (int targetRow = 0; targetRow < 8; targetRow++) {
                        for (int targetCol = 0; targetCol < 8; targetCol++) {
                            if (piece.isValidMove(targetRow, targetCol, logicalBoard) &&
                                logicalBoard.canMoveWithoutLeavingKingInCheck(row, col, targetRow, targetCol, color)) {
                                return true; // Valid move found
                            }
                        }
                    }
                }
            }
        }
        return false; // No valid moves
    }

    private void checkGameOver() {
        String currentPlayerColor = isWhiteTurn ? "White" : "Black";

        if (isKingInCheck(currentPlayerColor)) {
            if (!hasValidMoves(currentPlayerColor)) {
                showGameOverDialog((isWhiteTurn ? "Black" : "White") + " wins by checkmate!");
            }
        } else {
            if (!hasValidMoves(currentPlayerColor)) {
                showGameOverDialog("Stalemate! It's a draw.");
            }
        }
    }

    private void showGameOverDialog(String message) {
        int choice = JOptionPane.showOptionDialog(
            this,
            message + "\nWould you like to restart or quit?",
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Restart", "Quit"},
            "Restart"
        );

        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0);
        }
    }

    private void restartGame() {
        // Reset the logical board
        logicalBoard.setupBoard();
        
        // Reset the turn and selection
        isWhiteTurn = true;
        selectedPiece = null;
        selectedRow = -1;
        selectedCol = -1;
    
        // Clear and update the UI to reflect the new board state
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = buttons[row][col];
                Square square = logicalBoard.getSquare(row, col);
                Piece piece = square.getPiece();
    
                if (piece != null) {
                    button.setText(piece.getSymbol());
                    button.setFont(new Font("Serif", Font.BOLD, 36));
                    button.setForeground(piece.getColor().equalsIgnoreCase("White") ? Color.WHITE : Color.BLACK);
                } else {
                    button.setText(""); // Clear the button if no piece exists
                }
            }
        }
    
        // Update the status label
        statusLabel.setText("White's turn");
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
                if (clickedSquare == null) return;
        
                Piece clickedPiece = clickedSquare.getPiece();
        
                // Handle piece selection
                if (selectedPiece == null) {
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
                    // Handle movement
                    if (selectedPiece.isValidMove(row, col, logicalBoard)) {
                        if (logicalBoard.canMoveWithoutLeavingKingInCheck(selectedRow, selectedCol, row, col, selectedPiece.getColor())) {
                            // En passant logic
                            if (selectedPiece instanceof Pawn && selectedPiece.isValidMove(row, col, logicalBoard)) {
                                if (Math.abs(selectedCol - col) == 1 && logicalBoard.getSquare(row, col).getPiece() == null) {
                                    int capturedPawnRow = selectedPiece.getColor().equalsIgnoreCase("White") ? row + 1 : row - 1;
                                    logicalBoard.getSquare(capturedPawnRow, col).setPiece(null);
                                }
                            }
        
                            // Handle pawn promotion
                            if (selectedPiece instanceof Pawn && (row == 0 || row == 7)) {
                                promotePawn(row, col);
                            } else {
                                // Update LogBoard for regular moves
                                logicalBoard.getSquare(row, col).setPiece(selectedPiece);
                                logicalBoard.getSquare(selectedRow, selectedCol).setPiece(null);
                                selectedPiece.setPosition(row, col);
                            }
        
                            updateBoardUI();
                            isWhiteTurn = !isWhiteTurn;
                            checkGameOver();
                            statusLabel.setText((isWhiteTurn ? "White's" : "Black's") + " turn");
                        } else {
                            statusLabel.setText("Invalid move: King would be in check.");
                        }
                    } else {
                        statusLabel.setText("Invalid move. Try again.");
                    }
                    selectedPiece = null; // Reset selection
                }
            }
        
            private void promotePawn(int row, int col) {
                String[] options = {"Queen", "Rook", "Bishop", "Knight"};
                String choice = (String) JOptionPane.showInputDialog(
                        null,
                        "Promote Your Pawn! Choose a Piece:",
                        "Pawn Promotion",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        "Queen"
                );
                if (choice == null || !Arrays.asList(options).contains(choice)) {
                    choice = "Queen";
                }
                Piece promotedPiece;
                switch (choice) {
                    case "Queen":
                    default:
                        promotedPiece = new Queen(selectedPiece.getColor(), row, col);
                        break;
                    case "Rook":
                        promotedPiece = new Rook(selectedPiece.getColor(), row, col);
                        break;
                    case "Bishop":
                        promotedPiece = new Bishop(selectedPiece.getColor(), row, col);
                        break;
                    case "Knight":
                        promotedPiece = new Knight(selectedPiece.getColor(), row, col);
                        break;
                }
                logicalBoard.getSquare(row, col).setPiece(promotedPiece);
                logicalBoard.getSquare(selectedRow, selectedCol).setPiece(null);
            }
        }
        
    }




