package edu.gonzaga;

public class Bishop extends Piece {

    // constructor for Bishop
    public Bishop(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, LogBoard board) {
        // Ensure the move is diagonal
        if (Math.abs(destRow - getRow()) != Math.abs(destCol - getCol())) {
            return false; // Not a diagonal move
        }
    
        // Determine the direction of movement
        int rowDirection = (destRow > getRow()) ? 1 : -1;
        int colDirection = (destCol > getCol()) ? 1 : -1;
    
        // Check for obstructions along the path
        int currentRow = getRow() + rowDirection;
        int currentCol = getCol() + colDirection;
        while (currentRow != destRow && currentCol != destCol) {
            if (board.getSquare(currentRow, currentCol).getPiece() != null) {
                return false; // Path is obstructed
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }
    
        // Check the destination square
        Piece destPiece = board.getSquare(destRow, destCol).getPiece();
        return destPiece == null || !destPiece.getColor().equalsIgnoreCase(getColor());
    }    
    
    // Implementing the abstract getSymbol method
    @Override
    public String getSymbol() {
        // White bishop: ♗ (\u2657), Black bishop: ♝ (\u265D)
        return getColor().equalsIgnoreCase("White") ? "\u2657" : "\u265D";
    }
}
