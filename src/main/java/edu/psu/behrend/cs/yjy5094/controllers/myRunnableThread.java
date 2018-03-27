package edu.psu.behrend.cs.yjy5094.controllers;

import javafx.application.Platform;
import edu.psu.behrend.cs.yjy5094.views.TTTBoard;

import java.sql.SQLException;

public class myRunnableThread implements Runnable
{
    public C controller;
    public TTTBoard TTT;

    public myRunnableThread(C controller, TTTBoard TTT){this.controller = controller; this.TTT = TTT;}

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run()
    {
        while(true){
            try {
                Platform.runLater(new Runnable() {
                    public void run() {
                            controller.refreshButton();
                    }
                });
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
