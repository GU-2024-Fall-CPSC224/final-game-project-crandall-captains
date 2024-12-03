package edu.gonzaga;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



public class LogBoardTest {

    @Test
    public void testKingMovesOutOfCheck() {
        LogBoard board = new LogBoard();
        board.setupBoard();

        // Place the White King at E1 (row 7, col 4)
        board.getSquare(7, 4).setPiece(new King("White", 7, 4));

        // Place a Black Queen at D2 (row 6, col 3), attacking diagonally
        board.getSquare(6, 3).setPiece(new Queen("Black", 6, 3));

        // Assert King is in check initially
        assertTrue(board.isKingInCheck("White"));

        // Move the King to F2 (row 6, col 5) to escape check
        board.getSquare(7, 4).setPiece(null); // Remove King from E1
        board.getSquare(6, 5).setPiece(new King("White", 6, 5));

        // Assert King is no longer in check
        assertFalse(board.isKingInCheck("White"));
    }

    @Test
    public void testKingInCheckByQueen() {
        LogBoard board = new LogBoard();
        board.setupBoard(); // Reset the board

        // Place the White King at E1 (row 7, col 4)
        board.getSquare(7, 4).setPiece(new King("White", 7, 4));

        // Place a Black Queen at E5 (row 3, col 4) attacking the king
        board.getSquare(3, 4).setPiece(new Queen("Black", 3, 4));

        // White king should be in check
        assertTrue(board.isKingInCheck("White"));

        // Black king should not be in check
        assertFalse(board.isKingInCheck("Black"));
    }

    @Test
    public void testKingInCheckByKnight() {
        LogBoard board = new LogBoard();
        board.setupBoard();

        // Place the White King at D4 (row 4, col 3)
        board.getSquare(4, 3).setPiece(new King("White", 4, 3));

        // Place a Black Knight at B5 (row 3, col 1) attacking the king
        board.getSquare(3, 1).setPiece(new Knight("Black", 3, 1));

        // White king should be in check
        assertTrue(board.isKingInCheck("White"));

        // Black king should not be in check
        assertFalse(board.isKingInCheck("Black"));
    }

    @Test
    public void testKingNotInCheck() {
        LogBoard board = new LogBoard();
        board.setupBoard();

        // Place the White King at D4 (row 4, col 3)
        board.getSquare(4, 3).setPiece(new King("White", 4, 3));

        // Place a Black Rook at H8 (row 0, col 7) far from the king
        board.getSquare(0, 7).setPiece(new Rook("Black", 0, 7));

        // White king should not be in check
        assertFalse(board.isKingInCheck("White"));

        // Black king should not be in check either
        assertFalse(board.isKingInCheck("Black"));
    }

    @Test
    public void testKingInCheckBlockedByPiece() {
        LogBoard board = new LogBoard();
        board.setupBoard();

        // Place the White King at D4 (row 4, col 3)
        board.getSquare(4, 3).setPiece(new King("White", 4, 3));

        // Place a Black Rook at D8 (row 0, col 3)
        board.getSquare(0, 3).setPiece(new Rook("Black", 0, 3));

        // Place a White Pawn at D6 (row 2, col 3) blocking the rook
        board.getSquare(2, 3).setPiece(new Pawn("White", 2, 3));

        // White king should not be in check because the pawn blocks the attack
        assertFalse(board.isKingInCheck("White"));
    }

    @Test
    public void testKingInCheckDiagonal() {
        LogBoard board = new LogBoard();
        board.setupBoard();

        // Place the White King at D4 (row 4, col 3)
        board.getSquare(4, 3).setPiece(new King("White", 4, 3));

        // Place a Black Bishop at G7 (row 1, col 6) attacking diagonally
        board.getSquare(1, 6).setPiece(new Bishop("Black", 1, 6));

        // White king should be in check
        assertTrue(board.isKingInCheck("White"));

        // Black king should not be in check
        assertFalse(board.isKingInCheck("Black"));
    }
}