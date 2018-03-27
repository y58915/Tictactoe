package edu.psu.behrend.cs.yjy5094.models;

/**
 BoardPiece.java:
 Interface to represent a token on a Tic-Tac-Toe board

 Created By: Thomas Rossi
 */

public interface BoardPiece
{
    /**
     Prints the piece to the screen

     @returns String The String representation of the piece
     */
    public String printToken();
    public String getValue();
}
