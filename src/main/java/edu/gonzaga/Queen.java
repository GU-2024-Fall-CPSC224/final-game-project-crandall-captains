package edu.gonzaga;

public class Queen extends Piece {

    public Queen(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, LogBoard board) {
        int rowDiff = Math.abs(destRow - getRow());
        int colDiff = Math.abs(destCol - getCol());
    
        // Check if it's a valid bishop-like move
        if (rowDiff == colDiff) {
            int rowDirection = (destRow > getRow()) ? 1 : -1;
            int colDirection = (destCol > getCol()) ? 1 : -1;
            int currentRow = getRow() + rowDirection;
            int currentCol = getCol() + colDirection;
    
            while (currentRow != destRow && currentCol != destCol) {
                if (board.getSquare(currentRow, currentCol).getPiece() != null) {
                    return false; // Path obstructed
                }
                currentRow += rowDirection;
                currentCol += colDirection;
            }
        }
        // Check if it's a valid rook-like move
        else if (rowDiff == 0 || colDiff == 0) {
            int rowDirection = (destRow == getRow()) ? 0 : (destRow > getRow() ? 1 : -1);
            int colDirection = (destCol == getCol()) ? 0 : (destCol > getCol() ? 1 : -1);
    
            int currentRow = getRow() + rowDirection;
            int currentCol = getCol() + colDirection;
    
            while (currentRow != destRow || currentCol != destCol) {
                if (board.getSquare(currentRow, currentCol).getPiece() != null) {
                    return false; // Path obstructed
                }
                currentRow += rowDirection;
                currentCol += colDirection;
            }
        } else {
            return false; // Not a valid queen move
        }
    
        // Check the destination square
        Piece destPiece = board.getSquare(destRow, destCol).getPiece();
        return destPiece == null || !destPiece.getColor().equalsIgnoreCase(getColor());
    }
    
    @Override
    public String getSymbol() {
        return getColor().equalsIgnoreCase("White") ? "\u2655" : "\u265B"; // ♕ ♛
    }
}
