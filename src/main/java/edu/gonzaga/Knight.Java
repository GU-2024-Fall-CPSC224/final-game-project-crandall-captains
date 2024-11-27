package edu.gonzaga;

public class Knight extends Piece {

    public Knight(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, Board board) {
        int rowDiff = Math.abs(destRow - getRow());
        int colDiff = Math.abs(destCol - getCol());

        // Knight moves in L shape
        return (rowDiff == 2 && colDiff == 1 || rowDiff == 1 && colDiff == 2) &&
               (board.getPiece(destRow, destCol) == null ||
                !board.getPiece(destRow, destCol).getColor().equalsIgnoreCase(getColor()));
    }

    @Override
    public String getSymbol() {
        return getColor().equalsIgnoreCase("White") ? "\u2658" : "\u265E"; // white knight♘ black knight♞
    }
}
