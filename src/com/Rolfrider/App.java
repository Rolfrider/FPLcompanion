package com.Rolfrider;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ArrayList<Player> players = DataReader.Read();
        DataBase db = new DataBase();
        db.createTable();
        for (Player player:players)
            db.insertData(player);

        //System.out.println(players.get(15).toString());

    }

}
