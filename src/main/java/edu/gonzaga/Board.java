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
                // Attempt to move the selected piece
                if (selectedPiece.isValidMove(row, col, logicalBoard)) {
                    // Handle pawn promotion
                    if (selectedPiece instanceof Pawn && (row == 0 || row == 7)) {
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
                        selectedPiece = promotedPiece;
                    } else {
                        // Update LogBoard
                        logicalBoard.getSquare(row, col).setPiece(selectedPiece);
                        logicalBoard.getSquare(selectedRow, selectedCol).setPiece(null);
                        selectedPiece.setPosition(row, col);
                    }

                    // Update UI
                    updateBoardUI();

                    // Switch turn
                    isWhiteTurn = !isWhiteTurn;
                    statusLabel.setText((isWhiteTurn ? "White" : "Black") + "'s turn.");
                } else {
                    statusLabel.setText("Invalid move. Try again.");
                }

                // Clear selection in either case
                selectedPiece = null;
            }
        }
    }
}
