package edu.gonzaga;

public class Bishop extends Piece {

    // Constructor for Bishop
    public Bishop(String color, int row, int col) {
        super(color, row, col);
    }

    // Determines if a move is valid (not implemented yet)
    @Override
    public boolean isValidMove(int destRow, int destCol, Board board) {
        return true; // Placeholder logic for now
    }

    // Gets the correct Unicode symbol based on the bishop's color
    @Override
    public String getSymbol() {
        // White bishop: ♗ (\u2657), Black bishop: ♝ (\u265D)
        return getColor().equalsIgnoreCase("White") ? "\u2657" : "\u265D";
    }
}
