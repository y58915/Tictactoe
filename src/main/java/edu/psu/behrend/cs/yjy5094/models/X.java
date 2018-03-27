package edu.psu.behrend.cs.yjy5094.models;

public class X implements BoardPiece
{
    //constructor
    public X()
    {

    }
    /**
     * Prints the piece to the screen
     *
     * @returns String The String representation of the piece
     */
    @Override
    public String printToken()
    {
        return  "|  X  |";
    }
    public String getValue(){return "X";}
}
