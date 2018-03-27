package edu.psu.behrend.cs.yjy5094.models;

public class O implements BoardPiece
{
    //constructor
    public O()
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
        return  "|  O  |";
    }
    public String getValue(){return "O";}
}
