package edu.gonzaga;

public class LogBoard {
    private Square[][] squares;
    private Piece lastMovedPiece;
    private int lastMovedFromRow;
    private int lastMovedFromCol;
    private int lastMovedToRow;
    private int lastMovedToCol;


    public LogBoard() {
        // Initialize an 8x8 board with empty squares
        squares = new Square[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = new Square(row, col);
            }
        }
    }

    public Square getSquare(int row, int col) {
        return squares[row][col];
    }
    public void recordMove(Piece piece, int fromRow, int fromCol, int toRow, int toCol) {
        lastMovedPiece = piece;
        lastMovedFromRow = fromRow;
        lastMovedFromCol = fromCol;
        lastMovedToRow = toRow;
        lastMovedToCol = toCol;
    }
    public boolean isLastMoveDoubleStep(Pawn pawn) {
        // added for en passant
        if (lastMovedPiece instanceof Pawn &&
            lastMovedPiece.getColor().equalsIgnoreCase(pawn.getColor()) &&
            Math.abs(lastMovedFromRow - lastMovedToRow) == 2) {
            return true;
        }
        return false;
    }

    public void setSquare(int row, int col, Piece piece) {

        if(piece !=null && squares[row][col].getPiece() != piece) {
            // added for en passant
            recordMove(piece, piece.getRow(), piece.getCol(), row, col);
            piece.setPosition(row, col);
        }
        squares[row][col].setPiece(piece);
    }

    public boolean removePiece(Piece piece) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (squares[row][col].getPiece() == piece) {
                    squares[row][col].setPiece(null);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isKingInCheck(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getSquare(row, col).getPiece();
                if (piece instanceof King && piece.getColor().equalsIgnoreCase(color)) {
                    System.out.println("King found at: (" + row + ", " + col + ")");
                    boolean underAttack = isSquareUnderAttack(row, col, color);
                    System.out.println("King under attack: " + underAttack);
                    return underAttack;
                }
            }
        }
        return false;
    }

    public boolean isSquareUnderAttack(int row, int col, String color) {
        System.out.println("Checking if square (" + row + ", " + col + ") is under attack by opponent of color: " + color);
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = getSquare(r, c).getPiece();
                if (piece != null && !piece.getColor().equalsIgnoreCase(color)) {
                    System.out.println("Checking piece at (" + r + ", " + c + "): " + piece.getClass().getSimpleName());
                    boolean canAttack = piece.isValidMove(row, col, this);
                    System.out.println("Piece " + piece.getClass().getSimpleName() + " at (" + r + ", " + c + ") can attack: " + canAttack);
                    if (canAttack) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canMoveWithoutLeavingKingInCheck(int startRow, int startCol, int endRow, int endCol, String color) {
        Piece movingPiece = squares[startRow][startCol].getPiece();
        Piece capturedPiece = squares[endRow][endCol].getPiece();

        // Simulate the move
        squares[endRow][endCol].setPiece(movingPiece);
        squares[startRow][startCol].setPiece(null);
        movingPiece.setPosition(endRow, endCol);

        // Check if the king is in check
        boolean isKingInCheck = isKingInCheck(color);

        // Revert the move
        squares[startRow][startCol].setPiece(movingPiece);
        squares[endRow][endCol].setPiece(capturedPiece);
        movingPiece.setPosition(startRow, startCol);

        return !isKingInCheck; // True if the king is not in check
    }

    public void setupBoard() {
        // Place rooks
        setSquare(0, 0, new Rook("Black", 0, 0));
        setSquare(0, 7, new Rook("Black", 0, 7));
        setSquare(7, 0, new Rook("White", 7, 0));
        setSquare(7, 7, new Rook("White", 7, 7));

        // Place knights
        setSquare(0, 1, new Knight("Black", 0, 1));
        setSquare(0, 6, new Knight("Black", 0, 6));
        setSquare(7, 1, new Knight("White", 7, 1));
        setSquare(7, 6, new Knight("White", 7, 6));

        // Place bishops
        setSquare(0, 2, new Bishop("Black", 0, 2));
        setSquare(0, 5, new Bishop("Black", 0, 5));
        setSquare(7, 2, new Bishop("White", 7, 2));
        setSquare(7, 5, new Bishop("White", 7, 5));

        // Place queens
        setSquare(0, 3, new Queen("Black", 0, 3));
        setSquare(7, 3, new Queen("White", 7, 3));

        // Place kings
        setSquare(0, 4, new King("Black", 0, 4));
        setSquare(7, 4, new King("White", 7, 4));

        // Place pawns
        for (int col = 0; col < 8; col++) {
            setSquare(1, col, new Pawn("Black", 1, col)); // Black pawns
            setSquare(6, col, new Pawn("White", 6, col)); // White pawns
        }
    }
}
