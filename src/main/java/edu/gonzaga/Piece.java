package edu.gonzaga;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class Piece {
    private String color;
    private int row;
    private int col; 

    public Piece(String color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }
    public String getColor() {
        return color;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col; 
    }
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public abstract boolean isValidMove(int destRow, int destCol, Board board);
}
