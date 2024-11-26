package edu.gonzaga;

import javax.swing.*;

public class GameLaunch {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main game window
            JFrame frame = new JFrame("Chess Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600); // Adjust the size as needed

            // Add the chessboard (Board panel) to the frame
            Board chessBoard = new Board();
            frame.add(chessBoard);

            // Make the frame visible
            frame.setVisible(true);
        });
    }
}
