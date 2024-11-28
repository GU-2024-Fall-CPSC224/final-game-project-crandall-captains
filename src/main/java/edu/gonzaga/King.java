package edu.gonzaga;

public class King extends Piece {

    public King(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, LogBoard board) {
        int rowDiff = Math.abs(destRow - getRow());
        int colDiff = Math.abs(destCol - getCol());
    
        // King moves one square in any direction
        Piece destPiece = board.getSquare(destRow, destCol).getPiece();
        return (rowDiff <= 1 && colDiff <= 1) &&
               (destPiece == null || !destPiece.getColor().equalsIgnoreCase(getColor()));
    }
    

    @Override
    public String getSymbol() {
        return getColor().equalsIgnoreCase("White") ? "\u2654" : "\u265A"; // ♔ ♚
    }
}
