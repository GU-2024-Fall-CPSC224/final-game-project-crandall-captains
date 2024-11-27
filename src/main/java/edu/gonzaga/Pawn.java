package edu.gonzaga;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isValidMove(int destRow, int destCol, Board board) {
        int direction = getColor().equalsIgnoreCase("White") ? -1 : 1; // White moves up, Black moves down
        int rowDiff = destRow - getRow();
        int colDiff = destCol - getCol();

        Piece destPiece = board.getPiece(destRow, destCol);

        // Move forward
        if (colDiff == 0 && destPiece == null) {
            // Single step
            if (rowDiff == direction) {
                return true;
            }
            // Double step from starting position
            if ((getRow() == 1 && getColor().equalsIgnoreCase("Black") && rowDiff == 2 * direction) ||
                (getRow() == 6 && getColor().equalsIgnoreCase("White") && rowDiff == 2 * direction)) {
                return board.getPiece(getRow() + direction, getCol()) == null; // Ensure no blocking
            }
        }

        // Capture diagonally
        if (Math.abs(colDiff) == 1 && rowDiff == direction && destPiece != null && !destPiece.getColor().equalsIgnoreCase(getColor())) {
            return true;
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return getColor().equalsIgnoreCase("White") ? "\u2659" : "\u265F"; // ♙ for white, ♟ for black
    }
}

