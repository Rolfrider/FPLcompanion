package com.Rolfrider.Data;

import com.Rolfrider.GUI.WindowController;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

import java.util.ArrayList;

public class UpdateTask extends Task<Void>{

    WindowController controller;
    ProgressBar progressBar;

    public UpdateTask(WindowController con, ProgressBar pb){
        this.controller = con;
        this.progressBar = pb;
    }
    @Override
    protected Void call() throws Exception {
        ArrayList<Player> players = DataReader.Read();
        for(Player p : players){
          DataBase.updateData(p);
           updateProgress(p.getId(), players.size());
        }
        DataBase.updateTime();
        progressBar.setVisible(false);
        return null;
    }


    @Override
    protected void failed() {
        System.out.println("failed");
        super.failed();
    }

    @Override
    protected void succeeded() {
        System.out.println("succeeded");
        super.succeeded();
    }
}
