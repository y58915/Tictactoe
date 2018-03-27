package edu.psu.behrend.cs.yjy5094.models;

public class Star implements BoardPiece
{
    //constructor
    public Star()
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
        return  "|  *  |";
    }
    public String getValue(){return "*";}
}
