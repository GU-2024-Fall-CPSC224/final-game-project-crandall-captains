package edu.gonzaga;

public class Queen extends Piece {

    public Queen(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, Board board) {
        int rowDiff = Math.abs(destRow - getRow());
        int colDiff = Math.abs(destCol - getCol());

        // Queen moves diagonally, vertically, or horizontally
        if (rowDiff == colDiff || destRow == getRow() || destCol == getCol()) {
            int rowDirection = Integer.compare(destRow, getRow());
            int colDirection = Integer.compare(destCol, getCol());
            int currentRow = getRow() + rowDirection;
            int currentCol = getCol() + colDirection;

            while (currentRow != destRow || currentCol != destCol) {
                if (board.getPiece(currentRow, currentCol) != null) {
                    return false; // Path is obstructed
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
        return getColor().equalsIgnoreCase("White") ? "\u2655" : "\u265B"; // ♕ ♛
    }
}
