package edu.gonzaga;

public class King extends Piece {

    public King(String color, int row, int col) {
        super(color, row, col);
    }

    private boolean isCastlingMove(int destRow, int destCol, LogBoard board) {
        if (destRow != getRow()) {
            return false;
        }

        int colDiff = destCol - getCol();
        // Castling requires moving two squares horizontally
        if (Math.abs(colDiff) != 2) {
            return false;
        }

        // Can't castle if the King has already moved
        if (this.getHasMoved()) {
            return false;
        }

        int rookCol = (colDiff > 0) ? 7 : 0; // Rook is either on the leftmost or rightmost
        Piece rook = board.getSquare(getRow(), rookCol).getPiece();
        // Validate the rook for castling
        if (rook == null || !(rook instanceof Rook) || rook.getHasMoved()) {
            return false;
        }

        // Check that all squares between the King and the Rook are empty
        int direction = colDiff > 0 ? 1 : -1;
        for (int col = getCol() + direction; col != rookCol; col += direction) {
            if (board.getSquare(getRow(), col).getPiece() != null) {
                return false; 
            }
        }

        /*
        for (int col = getCol(); col != destCol + direction; col += direction) {
            if (board.isSquareUnderAttack(getRow(), col, getColor())) {
                return false; // King cannot move through or into an attacked square
            }
        }
        */

        return true;
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, LogBoard board) {
        int rowDiff = Math.abs(destRow - getRow());
        int colDiff = Math.abs(destCol - getCol());

        // King moves one square in any direction
        Piece destPiece = board.getSquare(destRow, destCol).getPiece();
        if (rowDiff <= 1 && colDiff <= 1) {
            return (destPiece == null || !destPiece.getColor().equalsIgnoreCase(getColor()));
        }

        // Check for castling move
        if (isCastlingMove(destRow, destCol, board)) {
            // Move the Rook as part of the castling process
            int rookCol = (destCol > getCol()) ? 7 : 0; // Rook's original column (kingside or queenside)
            int rookNewCol = (destCol > getCol()) ? destCol - 1 : destCol + 1; // Rook's new position

            Piece rook = board.getSquare(getRow(), rookCol).getPiece();
            if (rook != null && rook instanceof Rook) {
                // Update the rook's position on the LogBoard
                board.getSquare(getRow(), rookCol).setPiece(null); // Remove the rook from its original position
                board.getSquare(getRow(), rookNewCol).setPiece(rook); // Place the rook next to the King
                rook.setPosition(getRow(), rookNewCol); // Update the rook's internal position
            }
            return true;
        }

        return false; // Any other move is invalid
    }

    @Override
    public String getSymbol() {
        return getColor().equalsIgnoreCase("White") ? "\u2654" : "\u265A"; // ♔ for White, ♚ for Black
    }
}
