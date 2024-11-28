package edu.gonzaga;

public class Knight extends Piece {

    public Knight(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, LogBoard board) {
        int rowDiff = Math.abs(destRow - getRow());
        int colDiff = Math.abs(destCol - getCol());
    
        // Knight moves in "L" shape
        Piece destPiece = board.getSquare(destRow, destCol).getPiece();
        return (rowDiff == 2 && colDiff == 1 || rowDiff == 1 && colDiff == 2) &&
               (destPiece == null || !destPiece.getColor().equalsIgnoreCase(getColor()));
    }
    

    @Override
    public String getSymbol() {
        return getColor().equalsIgnoreCase("White") ? "\u2658" : "\u265E"; // White knight♘ black knight♞
    }
}
