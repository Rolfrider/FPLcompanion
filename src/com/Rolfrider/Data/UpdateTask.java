package com.Rolfrider.Data;

import com.Rolfrider.GUI.WindowController;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.ArrayList;

public class UpdateTask extends Task<Void>{


    ProgressBar progressBar;
    Label label;
    WindowController con;

    public UpdateTask(ProgressBar pb, Label label, WindowController con){
        this.progressBar = pb;
        this.label = label;
        this.con = con;

    }


    @Override
    protected Void call() throws Exception {
        ArrayList<Player> players = DataReader.ReadPlayers();
        if(progressBar != null){
            for(Player p : players){
              DataBase.updateData(p);
               updateProgress(p.getId(), players.size());
            }

            progressBar.setVisible(false);
        } else {
            for(Player p : players){
                DataBase.updateData(p);
            }
        }
        DataBase.insertOrUpdateTime();
        con.updateDate();
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
