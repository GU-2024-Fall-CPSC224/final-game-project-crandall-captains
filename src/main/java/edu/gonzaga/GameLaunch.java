/*
 * Final project main driver class
 * 
 * 
 * Project Description:
 * 
 * 
 * Contributors:
 * 
 * 
 * Copyright: 2023
 */
package edu.gonzaga;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/** Main program class for launching your team's program. */
public class GameLaunch {
    public static void main(String[] args) {
        System.out.println("Hello Team Game");
        JFrame frame = new JFrame("Examle Board");
        Board board = new Board();
        frame.add(board);
        frame.setSize(600,600);
        frame.setVisible(true);

        // Your code here. Good luck!
    }
}
