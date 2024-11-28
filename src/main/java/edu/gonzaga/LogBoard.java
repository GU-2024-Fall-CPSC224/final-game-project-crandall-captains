package edu.gonzaga;

public class LogBoard {
    private Square[][] squares;

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

    public void setSquare(int row, int col, Piece piece) {
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
