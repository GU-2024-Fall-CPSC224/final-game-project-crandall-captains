package edu.gonzaga;

import javax.swing.*;

public abstract class Piece {
    private String color; // Piece color (White or Black)
    private int row;      // Row position
    private int col;      // Column position
    private boolean hasMoved;

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
    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
        this.hasMoved = true;
    }

    
    // Abstract method for validating moves (to be implemented by subclasses)
    public abstract boolean isValidMove(int destRow, int destCol, LogBoard board);

    // Abstract method to retrieve the Unicode symbol for the piece
    public abstract String getSymbol();
}
