package edu.psu.behrend.cs.yjy5094.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import edu.psu.behrend.cs.yjy5094.views.TTTBoard;

public class BtnHandler implements EventHandler<ActionEvent> {
    TTTBoard TTT = new TTTBoard();
    C controller = new C(TTT);

    public BtnHandler(TTTBoard TTT, C controller) {
        this.TTT = TTT;
        this.controller = controller;
    }

    /**
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(ActionEvent event) {
        Button b = (Button) event.getSource();


        if (!controller.hasWinner()) {
            if (controller.changed) {
                if (controller.getWait()) {
                    b.setText("O");
                } else {
                    b.setText("X");
                }
                controller.putPiece(Integer.parseInt(b.getId())); // convert the string in ID to integer
            }


        }
        controller.checkFinish();
    }
}
/*
Jianfeng Sun: Helped with merge the functions in the old view class into the new Btnhandler class
Yao Yao: Helped with debugging and finding the exceptions like the case of having a draw when the board is filled.
 */