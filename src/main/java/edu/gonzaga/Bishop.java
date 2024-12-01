package edu.gonzaga;

public class Bishop extends Piece {

    // constructor for Bishop
    public Bishop(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, LogBoard board) {
        Square targetSquare = board.getSquare(targetRow, targetCol);
        
        // Check if the target square is valid and not occupied by a same-color piece
        if (targetSquare == null || (targetSquare.getPiece() != null && targetSquare.getPiece().getColor().equals(this.getColor()))) {
            return false; // Invalid move
        }
    
        // Diagonal movement validation
        int rowDiff = Math.abs(this.getRow() - targetRow);
        int colDiff = Math.abs(this.getCol() - targetCol);
        if (rowDiff != colDiff) {
            return false; // Not a diagonal move
        }
    
        // Check intermediate squares along the diagonal
        int rowDirection = (targetRow > this.getRow()) ? 1 : -1;
        int colDirection = (targetCol > this.getCol()) ? 1 : -1;
        int currentRow = this.getRow() + rowDirection;
        int currentCol = this.getCol() + colDirection;
    
        while (currentRow != targetRow && currentCol != targetCol) {
            Square intermediateSquare = board.getSquare(currentRow, currentCol);
            if (intermediateSquare == null || !intermediateSquare.isEmpty()) {
                return false; // Path is blocked
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }
    
        return true; // Valid move
    }     
    
    // Implementing the abstract getSymbol method
    @Override
    public String getSymbol() {
        // White bishop: ♗ (\u2657), Black bishop: ♝ (\u265D)
        return getColor().equalsIgnoreCase("White") ? "\u2657" : "\u265D";
    }
}
