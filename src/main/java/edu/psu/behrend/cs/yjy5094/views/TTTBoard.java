package edu.psu.behrend.cs.yjy5094.views;

import edu.psu.behrend.cs.yjy5094.controllers.BtnHandler;
import edu.psu.behrend.cs.yjy5094.controllers.C;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class TTTBoard extends Application
{
    C controller;
    TextField text;// used to put information like informing the winner

    public void start(Stage stage)
    {
        controller = new C(this);
        controller.reset();

        this.text = new TextField();

        this.text.setPrefSize(100,100);
        this.text.setEditable(false);

        BorderPane mainPane = new BorderPane();
        mainPane.setPrefHeight(300);
        mainPane.setPrefWidth(300);
        mainPane.setBottom( text);
        mainPane.setCenter( this.basicBoard());
        stage.setTitle("Tic-Tac-Toe");
        //set the title

        stage.setScene(new Scene(mainPane));
        stage.show();

        controller.printMsg(this);
    }

    /**
     * main board
     * @return pane for the stage
     */
    private Pane basicBoard()
    {
        TilePane tile = new TilePane();

        tile.setPrefColumns(3);
        tile.setPrefRows(3);


        for(int i=0;i<9;i++)
        {
            Button btn = new Button();
            btn.setMaxHeight( Double.MAX_VALUE );
            btn.setMaxWidth( Double.MAX_VALUE );
            btn.setMinSize( 100, 100 );
            btn.setId(String.valueOf(i));

            BtnHandler btnH = new BtnHandler(this, controller);

            btn.addEventHandler(ActionEvent.ANY,btnH);

            controller.buttonList.add(btn);

            tile.getChildren().add(btn);


        }


        TilePane pane = new TilePane();


        pane.getChildren().addAll(tile);
        pane.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        return pane;
    }

    /**
     * change the text bottom
     * @param change the changed text
     */
    public void changeText(String change)
    {
        text.setText(change);
    }


    public void run()
    {
        Application.launch();
    }
}
/*
Yiyan Wei:
Created and wrote the rough TTTBoard.java file, and helped to build the connection btw TTTboard and the BtnHandler.
(Contributions of the other group members are in the BtnHnadler file)
 */