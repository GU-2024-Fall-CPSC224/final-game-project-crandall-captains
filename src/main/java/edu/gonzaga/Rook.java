package edu.gonzaga;

public class Rook extends Piece {

    public Rook(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, Board board) {
        // Rook moves only vertically/horizontally 
        if (destRow == getRow() || destCol == getCol()) {
            int rowDirection = Integer.compare(destRow, getRow());
            int colDirection = Integer.compare(destCol, getCol());
            int currentRow = getRow() + rowDirection;
            int currentCol = getCol() + colDirection;

            while (currentRow != destRow || currentCol != destCol) {
                if (board.getPiece(currentRow, currentCol) != null) {
                    return false; // if path is obstructed
                }
                currentRow += rowDirection;
                currentCol += colDirection;
            }

            // Check destination square
            Piece destPiece = board.getPiece(destRow, destCol);
            return destPiece == null || !destPiece.getColor().equalsIgnoreCase(getColor());
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return getColor().equalsIgnoreCase("White") ? "\u2656" : "\u265C"; // white and black rooks
    }
}
