package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen {

    public StartScreen() {
        // Create a new JFrame for the start screen
        JFrame startFrame = new JFrame("Chess Game - Start Screen");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(400, 300);
        startFrame.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Welcome to Chess Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        startFrame.add(titleLabel, BorderLayout.CENTER);

        // Start Game button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the chess game board
                JFrame gameFrame = new JFrame("Chess Game");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setSize(800, 800);

                // Add the chessboard panel
                Board chessBoard = new Board();
                gameFrame.add(chessBoard);

                // Show the game board and close the start screen
                gameFrame.setVisible(true);
                startFrame.dispose();
            }
        });
        startFrame.add(startButton, BorderLayout.SOUTH);

        // Center the frame on the screen
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
    }
}
