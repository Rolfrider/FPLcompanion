package com.Rolfrider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        //ArrayList<Player> players = DataReader.Read();
        DataBase db = new DataBase();
        ArrayList<Player> players = db.selectData(1,"=","team");
        System.out.println(players.get(0).toString());

        //System.out.println(players.get(15).toString());

    }

}
