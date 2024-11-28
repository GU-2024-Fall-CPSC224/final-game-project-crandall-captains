package edu.gonzaga;

public class Rook extends Piece {

    public Rook(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, LogBoard board) {
        // Rook moves only vertically or horizontally
        if (destRow == getRow() || destCol == getCol()) {
            int rowDirection = Integer.compare(destRow, getRow());
            int colDirection = Integer.compare(destCol, getCol());
            int currentRow = getRow() + rowDirection;
            int currentCol = getCol() + colDirection;

            // Check all squares along the path
            while (currentRow != destRow || currentCol != destCol) {
                // Use board.getSquare to get the Square, then check if it contains a piece
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

        return false; // Not a valid rook move
    }

    @Override
    public String getSymbol() {
        return getColor().equalsIgnoreCase("White") ? "\u2656" : "\u265C"; // ♖ for white, ♜ for black
    }
}
