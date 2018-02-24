package com.Rolfrider.Data;

import com.Rolfrider.GUI.WindowController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

import java.util.ArrayList;
import java.util.Date;

public class UpdateTask extends Task<Void>{

    private ProgressBar progressBar;
    private WindowController con;

    public UpdateTask(ProgressBar pb, WindowController con){
        this.progressBar = pb;

        this.con = con;

    }

    @Override
    protected Void call() throws Exception {
        ArrayList<Player> players = DataReader.ReadPlayers();
        Platform.runLater(
                ()-> {con.getPlayerTable().setItems(con.getPlayers(players));
                con.updateDate(new  Date(System.currentTimeMillis()));});
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
        Platform.runLater(() -> con.updateDate());
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
