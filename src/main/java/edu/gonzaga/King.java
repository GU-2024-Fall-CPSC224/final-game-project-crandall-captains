package edu.gonzaga;

public class King extends Piece {

    public King(String color, int row, int col) {
        super(color, row, col);
    }
    private boolean isCastlingMove(int destRow, int destCol, Board board) {
        if (destRow != getRow()) {
            return false;
        }
        int colDiff = destCol - getCol();
        if (Math.abs(colDiff) != 2) {
            return false;
        }
        if(this.getHasMoved() == true) {
            return false;
        }
        int rookCol = (colDiff > 0) ? 7 : 0;
        Piece rook = board.getPiece(getRow(), rookCol);
        if(rook == null || !(rook instanceof Rook) || (rook.getHasMoved() == true)) {
            return false;
        }
        int direction = colDiff > 0 ? 1  : -1; 
        for (int col = getCol() + direction; col != rookCol; col +=direction ) {
            if(board.getPiece(getRow(), col) != null) {
                return false;
            }
        }
         /* 
        for(int col = getCol(); col != destCol + direction; col += direction) {
            if(board.isSquareUnderAttack(getRow(), col, getColor()) == true){
                return false;
            }
        }
        */
        return true;
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, Board board) {
        int rowDiff = Math.abs(destRow - getRow());
        int colDiff = Math.abs(destCol - getCol());

        // King moves one square in any direction
        if (rowDiff <= 1 && colDiff <= 1){
               return (board.getPiece(destRow, destCol) == null ||
                !board.getPiece(destRow, destCol).getColor().equalsIgnoreCase(getColor()));
        }
        if (isCastlingMove(destRow, destCol, board)) {
            return true;
        }
        return false;
    }

    @Override
    public String getSymbol() {
        return getColor().equalsIgnoreCase("White") ? "\u2654" : "\u265A"; // ♔ ♚
    }
}
