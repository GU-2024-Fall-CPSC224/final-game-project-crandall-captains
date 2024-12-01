package edu.gonzaga;

public class Queen extends Piece {

    public Queen(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, LogBoard board) {
        System.out.println("Queen move validation: from (" + getRow() + ", " + getCol() + ") to (" + destRow + ", " + destCol + ")");

        int rowDiff = Math.abs(destRow - getRow());
        int colDiff = Math.abs(destCol - getCol());

        // Prevent moving to the same square
        if (destRow == getRow() && destCol == getCol()) {
            System.out.println("Invalid move: Cannot move to the same square");
            return false;
        }

        // Check diagonal movement
        if (rowDiff == colDiff) {
            System.out.println("Diagonal move detected");
            int rowDirection = (destRow > getRow()) ? 1 : -1;
            int colDirection = (destCol > getCol()) ? 1 : -1;
            int currentRow = getRow() + rowDirection;
            int currentCol = getCol() + colDirection;

            // Check all squares before the destination
            while (currentRow != destRow && currentCol != destCol) {
                if (board.getSquare(currentRow, currentCol).getPiece() != null) {
                    System.out.println("Path obstructed at (" + currentRow + ", " + currentCol + ")");
                    return false;
                }
                currentRow += rowDirection;
                currentCol += colDirection;
            }
        }
        // Check straight-line movement
        else if (rowDiff == 0 || colDiff == 0) {
            System.out.println("Straight-line move detected");
            int rowDirection = (destRow > getRow()) ? 1 : (destRow < getRow()) ? -1 : 0;
            int colDirection = (destCol > getCol()) ? 1 : (destCol < getCol()) ? -1 : 0;

            int currentRow = getRow() + rowDirection;
            int currentCol = getCol() + colDirection;

            // Check all squares before the destination
            while (currentRow != destRow || currentCol != destCol) {
                if (board.getSquare(currentRow, currentCol).getPiece() != null) {
                    System.out.println("Path obstructed at (" + currentRow + ", " + currentCol + ")");
                    return false;
                }
                currentRow += rowDirection;
                currentCol += colDirection;
            }
        } else {
            System.out.println("Invalid Queen move: Not a straight-line or diagonal move");
            return false; // Not a valid Queen move
        }

        // Check the destination square
        Piece destPiece = board.getSquare(destRow, destCol).getPiece();
        if (destPiece != null) {
            System.out.println("Destination square contains: " + destPiece.getClass().getSimpleName());
        } else {
            System.out.println("Destination square is empty");
        }

        // Return true if the destination is empty or occupied by an opponent's piece
        return destPiece == null || !destPiece.getColor().equalsIgnoreCase(getColor());
    }
    
    @Override
    public String getSymbol() {
        return getColor().equalsIgnoreCase("White") ? "\u2655" : "\u265B"; // ♕ ♛
    }
}
