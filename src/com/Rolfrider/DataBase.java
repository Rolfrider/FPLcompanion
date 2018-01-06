package com.Rolfrider;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    final String PATH = "jdbc:sqlite:D:/stud/Java/Project/sql_liteDB/";
    String DBname = "PlayersData.db";


    DataBase(){


    }

    private Connection connectToDatabase(){
        String url = PATH + DBname;
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);

            }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;

    }

    public void insertData(Player player){
        ArrayList<String> sData;
        ArrayList<Integer> intData;
        try (Connection con = this.connectToDatabase();
        PreparedStatement pstmt = con.prepareStatement(sqlInsert)){

                 sData = player.getStringData();
                 int i = 1;
                 for (String s : sData){
                     if(s == null){
                         pstmt.setString(i,"unknown");
                     }else
                        pstmt.setString(i,s);
                     i++;
                 }
                 intData = player.getIntData();
                 for (Integer num : intData){
                     pstmt.setInt(i,num);
                     i++;
                 }
                pstmt.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void createTable(){
        String url = PATH + DBname;
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sqlTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    String sqlInsert = "INSERT INTO " + DBname.substring(0, DBname.length()-3) +
            "(web_name,status,first_name,second_name,news," +
            "news_added,value_form,value_seson,selected_by_percent," +
            "form,points_per_game,ep_this,ep_next,influence,creativity," +
            "threat,ict_index," +
            "team_code,code,squad_number,now_cost,chance_of_playing_this_round," +
            "chance_of_playing_next_round,cost_change_start,cost_change_event," +
            "cost_change_start_fall,cost_change_event_fall,dreamteam_count," +
            "transfers_out,transfers_in,transfers_out_event,transfers_in_event," +
            "loans_in,loans_out,loaned_in,loaned_out,total_points,event_points,minutes," +
            "goals_scored,assists,clean_sheets,goals_conceded,own_goals,penalties_saved," +
            "penalties_missed,yellow_cards,red_cards,saves,bonus,bps,ea_index,element_type," +
            "team)" +
            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
            ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
            ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    String sqlTable = "CREATE TABLE IF NOT EXISTS " + DBname.substring(0, DBname.length()-3) + "(\n"
            + "	id integer PRIMARY KEY AUTOINCREMENT,\n"

            + " web_name text NOT NULL, \n "
            + " status text NOT NULL, \n "
            + " first_name text NOT NULL, \n "
            + " second_name text NOT NULL, \n "
            + " news text NOT NULL, \n "
            + " news_added text NOT NULL, \n "
            + " value_form text NOT NULL, \n "
            + " value_seson text NOT NULL, \n "
            + " selected_by_percent text NOT NULL, \n "
            + " form text NOT NULL, \n "
            + " points_per_game text NOT NULL, \n "
            + " ep_this text NOT NULL, \n "
            + " ep_next text NOT NULL, \n "
            + " influence text NOT NULL, \n "
            + " creativity text NOT NULL, \n "
            + " threat text NOT NULL, \n "
            + " ict_index text NOT NULL, \n "

            + " team_code integer, \n "
            + " code integer, \n "
            + " squad_number integer, \n "
            + " now_cost integer, \n "
            + " chance_of_playing_this_round integer, \n "
            + " chance_of_playing_next_round integer, \n "
            + " cost_change_start integer, \n "
            + " cost_change_event integer, \n "
            + " cost_change_start_fall integer, \n "
            + " cost_change_event_fall integer, \n "
            + " dreamteam_count integer, \n "
            + " transfers_out integer, \n "
            + " transfers_in integer, \n "
            + " transfers_out_event integer, \n "
            + " transfers_in_event integer, \n "
            + " loans_in integer, \n "
            + " loans_out integer, \n "
            + " loaned_in integer, \n "
            + " loaned_out integer, \n "
            + " total_points integer, \n "
            + " event_points integer, \n "
            + " minutes integer, \n "
            + " goals_scored integer, \n "
            + " assists integer, \n "
            + " clean_sheets integer, \n "
            + " goals_conceded integer, \n "
            + " own_goals integer, \n "
            + " penalties_saved integer, \n "
            + " penalties_missed integer, \n "
            + " yellow_cards integer, \n "
            + " red_cards integer, \n "
            + " saves integer, \n "
            + " bonus integer, \n "
            + " bps integer, \n "
            + " ea_index integer, \n "
            + " element_type integer, \n "
            + " team integer \n "
            + ");";
}
