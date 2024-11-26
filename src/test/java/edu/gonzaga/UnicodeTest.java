package edu.gonzaga;

import javax.swing.*;
import java.awt.*;

public class UnicodeTest {
    public static void main(String[] args) {
        // create a simple jframe to test Unicode rendering; supposed to show just the bishops
        JFrame testFrame = new JFrame("Unicode Test");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(300, 200);

        // unicodes for the black and white bishops
        JLabel label = new JLabel("\u2657 White Bishop    \u265D Black Bishop", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 36)); // Font size and style
        testFrame.add(label);

        // Make the frame visible
        testFrame.setVisible(true);
    }
}
