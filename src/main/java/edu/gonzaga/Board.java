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

    // Initialize pieces on the board
    private void initializePieces() {
        // Place white bishops
        board[7][2] = new Bishop("Black", 7, 2); // C1
        board[7][5] = new Bishop("White", 7, 5); // F1

        // Place black bishops
        board[0][2] = new Bishop("White", 0, 2); // C8
        board[0][5] = new Bishop("Black", 0, 5); // F8
    }

    // Create the chessboard UI
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 8)); // 8x8 chess grid

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                char columnLabel = (char) ('A' + col); // A-H
                int rowLabel = 8 - row;               // 1-8
                String positionLabel = columnLabel + String.valueOf(rowLabel);

                JButton button = new JButton();

                // Set alternating colors for chessboard pattern
                if ((row + col) % 2 == 0) {
                    button.setBackground(Color.BLACK);
                    button.setForeground(Color.WHITE);
                } else {
                    button.setBackground(Color.WHITE);
                    button.setForeground(Color.BLACK);
                }

                // Render the piece if there is one on this tile
                if (board[row][col] != null) {
                    button.setText(board[row][col].getSymbol()); // Use the Unicode symbol
                    button.setFont(new Font("Serif", Font.BOLD, 36)); // Large and bold font
                    button.setForeground(board[row][col].getColor().equalsIgnoreCase("White") ? Color.WHITE : Color.BLACK);
                    button.setHorizontalAlignment(SwingConstants.CENTER);
                    button.setVerticalAlignment(SwingConstants.CENTER);
                } else {
                    // Default to showing the tile's position label
                    button.setText(positionLabel);
                    button.setFont(new Font("Arial", Font.PLAIN, 12));
                    button.setHorizontalAlignment(SwingConstants.CENTER);
                    button.setVerticalAlignment(SwingConstants.CENTER);
                }

                button.addActionListener(new ButtonClickListener(positionLabel));
                buttons.add(button);
                buttonPanel.add(button);
            }
        }

        return buttonPanel;
    }

    private class ButtonClickListener implements ActionListener {
        private final String positionLabel;

        public ButtonClickListener(String positionLabel) {
            this.positionLabel = positionLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            statusLabel.setText("You Clicked: " + positionLabel);
        }
    }
}
