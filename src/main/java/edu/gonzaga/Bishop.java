package edu.gonzaga;

public class Bishop extends Piece {

    // Constructor for Bishop
    public Bishop(String color, int row, int col) {
        super(color, row, col);
    }

    // Implementing the abstract isValidMove method
    @Override
    public boolean isValidMove(int destRow, int destCol, Board board) {
        // Your existing isValidMove logic
        int rowDiff = Math.abs(destRow - getRow());
        int colDiff = Math.abs(destCol - getCol());
        
        // Ensure the destination is within board bounds
        if (destRow < 0 || destRow >= 8 || destCol < 0 || destCol >= 8) {
            return false; // Destination is out of bounds
        }
        
        if (rowDiff != colDiff) {
            return false; // Not a diagonal move
        }
        
        // Check if the path is clear
        int rowDirection = (destRow > getRow()) ? 1 : -1;
        int colDirection = (destCol > getCol()) ? 1 : -1;
        int currentRow = getRow() + rowDirection;
        int currentCol = getCol() + colDirection;
        
        while (currentRow != destRow && currentCol != destCol) {
            if (board.getPiece(currentRow, currentCol) != null) {
                return false; // Path is obstructed
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }
        
        // Check the destination square
        Piece destPiece = board.getPiece(destRow, destCol);
        return destPiece == null || !destPiece.getColor().equalsIgnoreCase(getColor());
    }

    // Implementing the abstract getSymbol method
    @Override
    public String getSymbol() {
        // White bishop: ♗ (\u2657), Black bishop: ♝ (\u265D)
        return getColor().equalsIgnoreCase("White") ? "\u2657" : "\u265D";
    }
}
