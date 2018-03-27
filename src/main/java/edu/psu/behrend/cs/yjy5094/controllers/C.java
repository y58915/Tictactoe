package edu.psu.behrend.cs.yjy5094.controllers;
import edu.psu.behrend.cs.yjy5094.models.*;
import edu.psu.behrend.cs.yjy5094.views.TTTBoard;
import javafx.scene.control.Button;

import java.sql.*;
import java.util.ArrayList;

public class C
{

    //define variables
    private BoardPiece[][] board ;
    public Connection connection = null;
    public ArrayList<Button> buttonList = new ArrayList<Button>();
    boolean changed = true;
    public TTTBoard TTT;


    /**
     *  Constructor
     * @param TTT the TTTBoard class
     */
    public C(TTTBoard TTT) //constructor
    {
        this.board = new BoardPiece[3][3];
        this.TTT = TTT;

        this.initialize(); // fill the board with all *
    }

    // the following method is to fill the board with all star sign
    public void initialize()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                board[i][j] = new Star();
            }
        }
    }

    /**
     * check if there's a winner
     * @return true if there is a winner
     */
    public boolean hasWinner()
    {
        boolean finished = false;
        //check row
        for(int i =0;i<3;i++)
        {
            if(board[i][0].getValue() == board[i][1].getValue() &&
                    board[i][1].getValue() == board[i][2].getValue() &&
                    board[i][0].getValue() != "*" )
            {
                finished = true;
            }
        }
        //check column
        for(int j =0;j<3;j++)
        {
            if(board[0][j].getValue() == board[1][j].getValue() &&
                    board[1][j].getValue() == board[2][j].getValue() &&
                    board[0][j].getValue() != "*" )
            {
                finished = true;

            }
        }
        //check cross
        if( (board[1][1].getValue() == board[0][0].getValue() &&
                board[1][1].getValue() == board[2][2].getValue() &&
                board[1][1].getValue() !="*") ||
                (board[1][1].getValue() == board[2][0].getValue() &&
                        board[1][1].getValue() == board[0][2].getValue() &&
                        board[1][1].getValue() !="*")  )
        {
            finished = true;
        }

        return finished;
    }

    /**
     * set the text of button, set the board.
     * @param index the index of the button
     */
    public void putPiece(int index)
    {
        int row = index/3;
        int column = index%3;

        if(valid(row,column))
        {
            if (getWait())
            {
                board[row][column] = new O();
                update(row, column, getWait());
                setWait(false);
            }
            else
            {
                board[row][column] = new X();
                update(row, column, getWait());
                setWait(true);
            }
            changed = false;
        }
    }

    /**
     * ckeck if the input is valid
     * @param row the row number
     * @param column the column number
     * @return true if the input is valid
     */
    public boolean valid(int row,int column)
    {
        boolean flag = true;

        if(!board[row][column].getValue().equals("*"))
        {
            flag = false;
        }

        return flag;
    }

    public boolean getWait()
    {
        boolean content = false;
        try
        {
            String sql = "SELECT * FROM space WHERE space.row = ? AND space.column = ?";
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setInt( 1, -99 );
            preparedStatement.setInt( 2, -99 );
            ResultSet resultSet = preparedStatement.executeQuery();
            content = resultSet.getBoolean( 3 );
            resultSet.close();
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
        return content;
    }


    /**
     * keep refreshing the button
     */
    public void refreshButton()
    {

        int i, j;

            try
            {
                for (i = 0; i < 3; i++)
                {
                    for (j = 0; j < 3; j++)
                    {
                        if (board[i][j].getValue().equals("*"))
                        {
                            String sql = "SELECT * FROM space WHERE space.row = ? AND space.column = ?";
                            PreparedStatement preparedStatement = connection.prepareStatement( sql );
                            preparedStatement.setInt( 1, i+1 );
                            preparedStatement.setInt( 2, j+1 );
                            ResultSet resultSet = preparedStatement.executeQuery();
                            String content = resultSet.getString( 3 );
                            resultSet.close();
                            checkFinish();
                            if (!content.equals("0"))
                            {
                                if (content.equals("1"))
                                {
                                    buttonList.get(i * 3 + j).setText("O");
                                    board[i][j] = new O();
                                }
                                else
                                {
                                    buttonList.get(i * 3 + j).setText("X");
                                    board[i][j] = new X();
                                }
                                changed = true;
                                break;
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    /**
     * call the thread
     * @param tb the TTTBoard class
     */
    public void printMsg(TTTBoard tb){
        myRunnableThread mrt = new myRunnableThread(this,tb);
        Thread t = new Thread(mrt);
        t.start();
    }

    /**
     * update the database
     * @param row the row number
     * @param column the column number
     * @param value the O player or the X player
     */
    public void update(int row, int column, boolean value)
    {
        String sql = "UPDATE space SET contents = ? WHERE row = ? AND column = ?";
        row++;
        column++;
        int temp;

        if (value)
        {
            temp = 1;
        }
        else
        {
            temp = -1;
        }

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setInt(1, temp);
            preparedStatement.setInt(2, row);
            preparedStatement.setInt(3, column);

            preparedStatement.executeUpdate();
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * initialize the database
     */
    public void reset()
    {

        try
        {
            String url = "jdbc:sqlite:E:\\Sophomore #1\\CMPSC221\\homework11\\src\\main\\resources\\tictactoe.sqlite";

            connection = DriverManager.getConnection( url );

            System.out.println("Connection Established");
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        String sql = "DELETE FROM space";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.executeUpdate();

            for (int row = 1; row < 4; row++)
                for (int column = 1; column < 4; column++)
                {
                    sql = "INSERT INTO space VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement( sql );
                    preparedStatement.setInt(1, row);
                    preparedStatement.setInt(2, column);
                    preparedStatement.setInt(3, 0);
                    preparedStatement.executeUpdate();
                }
            preparedStatement.setInt(1, -99);
            preparedStatement.setInt(2, -99);
            preparedStatement.setBoolean(3, true);
            preparedStatement.executeUpdate();
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
    }

    // main method
    public static void main(String[] args)
    {
        new TTTBoard().run();
    }

    public boolean full()
    {
        boolean flag = true;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3 ; j++)
            {
                if (board[i][j].getValue().equals("*"))
                    flag = false;
            }
        }
        return flag;
    }

    /**
     * set the player name
     * @param wait true for O, false for X
     */
    public void setWait(boolean wait)
    {
        String sql = "UPDATE space SET contents = ? WHERE row = ? AND column = ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setBoolean(1, wait);
            preparedStatement.setInt(2, -99);
            preparedStatement.setInt(3, -99);

            preparedStatement.executeUpdate();
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * check if it is finished and output
     */
    public void checkFinish()
    {
        if (hasWinner())
        {
            if (!getWait())
            {
                TTT.changeText("Congratulations! O player wins!");
            }
            else
            {
                TTT.changeText("Congratulations! X player wins!");
            }
        }

        if(full() && !hasWinner())
        {
            TTT.changeText("DRAW GAME!");
        }
    }
}
