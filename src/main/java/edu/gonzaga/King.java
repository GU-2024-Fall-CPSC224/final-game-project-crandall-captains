package edu.gonzaga;

public class King extends Piece {

    public King(String color, int row, int col) {
        super(color, row, col);
    }

    private boolean isCastlingMove(int destRow, int destCol, LogBoard board) {
        //System.out.println("Checks begin");
        // King must remain in the same row
        if (destRow != getRow()) {
            //System.out.println("destRow != getRow");
            return false;
        }
    
        int colDiff = destCol - getCol();
        // Castling requires moving exactly two squares horizontally
        if (Math.abs(colDiff) != 2) {
            //System.out.println("Math.abs(colDiff) != 2");
            return false;
        }
    
        // King must not have moved previously
        if (this.getHasMoved()) {
            //System.out.println("KingHasMoved");
            return false;
        } 
    
        // Determine the Rook's column based on direction
        int rookCol = (colDiff > 0) ? 7 : 0;
        Piece rook = board.getSquare(getRow(), rookCol).getPiece();
    
        // Validate the Rook
        if (rook == null || !(rook instanceof Rook) || rook.getHasMoved() || !rook.getColor().equals(this.getColor())) {
            //System.out.println("Rook not valid");
            return false;
        } 
    
        // Ensure all squares between the King and Rook are empty
        int direction = colDiff > 0 ? 1 : -1;
        for (int col = getCol() + direction; col != rookCol; col += direction) {
            if (board.getSquare(getRow(), col).getPiece() != null) {
                //System.out.println("Squares not empty");
                return false;
            }
        }
    
        // Ensure King does not move through or into a square under attack
        for (int col = getCol(); col != destCol + direction; col += direction) {
            if (board.isSquareUnderAttack(getRow(), col, getColor())) {
                //System.out.println("Moving into square under attack");
                return false;
            }
        }
    
        return true;
    }    

    @Override
    public boolean isValidMove(int destRow, int destCol, LogBoard board) {
        int rowDiff = Math.abs(destRow - getRow());
        int colDiff = Math.abs(destCol - getCol());

        // King moves one square in any direction
        Piece destPiece = board.getSquare(destRow, destCol).getPiece();
        if (rowDiff <= 1 && colDiff <= 1) {
            // Check if the move puts the King in check
            if (board.canMoveWithoutLeavingKingInCheck(getRow(), getCol(), destRow, destCol, getColor())) {
                return (destPiece == null || !destPiece.getColor().equalsIgnoreCase(getColor()));
            }
            return false;
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

            //System.out.println("Castling move true");
            return true;
        } else {
            //System.out.println("Castling move false");
        }

        return false; // Any other move is invalid
    }

    @Override
    public String getSymbol() {
        return getColor().equalsIgnoreCase("White") ? "\u2654" : "\u265A"; // ♔ for White, ♚ for Black
    }
}
