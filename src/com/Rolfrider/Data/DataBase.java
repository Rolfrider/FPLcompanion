package com.Rolfrider.Data;

import com.Rolfrider.GUI.WindowController;
import javafx.scene.control.Label;


import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



public class DataBase {
    final static String PATH = "jdbc:sqlite:sql_liteDB/";
    final static String  DBname = "PlayersData.db";


    public DataBase(Label label, WindowController con){
        createTable();
        createTimeTable();
        if(needUpdate()) {
            UpdateTask task = new UpdateTask(null, label, con);
            Thread thread = new Thread(task);
            thread.start();
        }


    }

    private static Connection connectToDatabase(){
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
        try (Connection con = connectToDatabase();
        PreparedStatement pstmt = con.prepareStatement(sqlInsert)){

                 sData = player.getStringData();
                 int i = 1;
            i = insertStringData(sData, pstmt, i);
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

    public static void updateData(Player player){
        ArrayList<String> sData = player.getStringData();
        ArrayList<Integer> intData = player.getIntData();
        int id = player.getId();


        try (Connection conn = connectToDatabase()){
            PreparedStatement pstmt;
            if(selectData(id, "=", "id").isEmpty()){
                pstmt = conn.prepareStatement(sqlInsert);
                int i = 1;
                i = insertStringData(sData, pstmt, i);
                for (Integer num : intData){
                    pstmt.setInt(i,num);
                    i++;
                }

            }else {

                pstmt = conn.prepareStatement(sqlUpdate);
                intData.remove(0); // removes a id value because we don't update it
                int i = 1;
                i = insertStringData(sData, pstmt, i);
                for (Integer num : intData){
                    pstmt.setInt(i,num);
                    i++;
                }
                pstmt.setInt(i,id);
            }


            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    private static int insertStringData(ArrayList<String> sData, PreparedStatement pstmt, int i) throws SQLException {
        for (String s : sData){
            if(s == null){
                pstmt.setString(i,"unknown");
            }else
                pstmt.setString(i,s);
            i++;
        }
        return i;
    }


    public static ArrayList<Player> selectData(){// Returns all players
        ArrayList<Player> players = new ArrayList<>();
        Player player = new Player();
        ArrayList<String> intNames = player.getIntFieldsNames(),
                stringNames = player.getStringFieldsNames();
        try (Connection con = connectToDatabase();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sqlSelect)){
            parseToPlayer(players, player, intNames, stringNames, rs);

        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Returned null value");
        }
        return players;
    }

    public static ArrayList<Player> selectData(int value, String operator, String field){


        String sqlSelect = "SELECT * FROM " + DBname.substring(0, DBname.length()-3) +
                " WHERE " + field + " " + operator + " ?";
        ArrayList<Player> players = new ArrayList<>();
        Player player = new Player();
        ArrayList<String> intNames = player.getIntFieldsNames(),
                stringNames = player.getStringFieldsNames();

        try (Connection con = connectToDatabase();
             PreparedStatement pstmt  = con.prepareStatement(sqlSelect)){
            pstmt.setInt(1,value);
            ResultSet rs = pstmt.executeQuery();
            parseToPlayer(players, player, intNames, stringNames, rs);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Returned null value");
        }

        return players;
    }

    public static ArrayList<Player> selectData(String value, String operator, String field){
        String sqlSelect = "SELECT * FROM " + DBname.substring(0, DBname.length()-3) +
                " WHERE " + field + " " + operator + " ?";
        ArrayList<Player> players = new ArrayList<>();
        Player player = new Player();
        ArrayList<String> intNames = player.getIntFieldsNames(),
                stringNames = player.getStringFieldsNames();

        try (Connection con = connectToDatabase();
             PreparedStatement pstmt  = con.prepareStatement(sqlSelect)){
            pstmt.setString(1,value);
            ResultSet rs = pstmt.executeQuery();
            parseToPlayer(players, player, intNames, stringNames, rs);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Returned null value");
        }
        return players;
    }

    private static void parseToPlayer(ArrayList<Player> players, Player player, ArrayList<String> intNames,
                               ArrayList<String> stringNames, ResultSet rs) throws SQLException {
        while (rs.next()){
            for(String i : intNames)
                player.setField(i,rs.getInt(i));
            for(String s : stringNames)
                player.setField(s, rs.getString(s));
            players.add(player);
            player = new Player();
        }
    }

    private void createTimeTable(){
        String sql = "CREATE TABLE " +
                "  IF NOT EXISTS dateTime (d int)";
        try(Connection con = connectToDatabase();
            Statement stmt = con.createStatement()){
                stmt.execute(sql);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static int selectTime(){
        String sql = "SELECT d " +
                "FROM dateTime";
        int time = 0;
        try (Connection con = connectToDatabase();
             PreparedStatement pstmt  = con.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                time = rs.getInt("d");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Returned null value");
        }
        return time;
    }

    public static void insertOrUpdateTime(){
        int time = selectTime();
        if(time == 0){
            insertTime();
        }else
            updateTime();
    }

    private boolean needUpdate(){
        final int oneDay = 86400;
        return selectTime() + oneDay < TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }


    private static void insertTime(){
        String sql = "INSERT INTO dateTime (d)" +
                "VALUES (strftime('%s', 'now'))";
        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateTime(){
        String sql = "UPDATE dateTime " +
                "SET d = strftime('%s', 'now')";
        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
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

    private static String sqlSelect = "SELECT * " +
            "FROM " + DBname.substring(0, DBname.length()-3) ;

    private static String sqlInsert = "INSERT OR REPLACE INTO " + DBname.substring(0, DBname.length()-3) +
            "(photo,web_name,status,first_name,second_name,news," +
            "news_added,value_form,value_seson,selected_by_percent," +
            "form,points_per_game,ep_this,ep_next,influence,creativity," +
            "threat,ict_index," +
            "id,team_code,code,squad_number,now_cost,chance_of_playing_this_round," +
            "chance_of_playing_next_round,cost_change_start,cost_change_event," +
            "cost_change_start_fall,cost_change_event_fall,dreamteam_count," +
            "transfers_out,transfers_in,transfers_out_event,transfers_in_event," +
            "loans_in,loans_out,loaned_in,loaned_out,total_points,event_points,minutes," +
            "goals_scored,assists,clean_sheets,goals_conceded,own_goals,penalties_saved," +
            "penalties_missed,yellow_cards,red_cards,saves,bonus,bps,ea_index,element_type," +
            "team)" +
            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
            ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
            ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static String sqlUpdate = "UPDATE PlayersData " +
            "SET photo = ?, web_name  = ?,status  = ?,first_name  = ?,second_name  = ?,news  = ?," +
            "news_added  = ?,value_form  = ?,value_seson  = ?,selected_by_percent  = ?," +
            "form  = ?,points_per_game  = ?,ep_this  = ?,ep_next  = ?,influence  = ?,creativity  = ?," +
            "threat  = ?,ict_index  = ?," +
            "team_code  = ?,code  = ?,squad_number  = ?,now_cost  = ?,chance_of_playing_this_round  = ?," +
            "chance_of_playing_next_round  = ?,cost_change_start  = ?,cost_change_event  = ?," +
            "cost_change_start_fall  = ?,cost_change_event_fall  = ?,dreamteam_count  = ?," +
            "transfers_out  = ?,transfers_in  = ?,transfers_out_event  = ?,transfers_in_event  = ?," +
            "loans_in  = ?,loans_out  = ?,loaned_in  = ?,loaned_out  = ?,total_points  = ?,event_points  = ?,minutes  = ?," +
            "goals_scored  = ?,assists  = ?,clean_sheets  = ?,goals_conceded  = ?,own_goals  = ?,penalties_saved  = ?," +
            "penalties_missed  = ?,yellow_cards  = ?,red_cards  = ?,saves  = ?,bonus  = ?,bps  = ?,ea_index  = ?,element_type  = ?," +
            "team  = ?" + " WHERE id = ?";

    private String sqlTable = "CREATE TABLE IF NOT EXISTS " + DBname.substring(0, DBname.length()-3) + "(\n"
            + "	id integer PRIMARY KEY AUTOINCREMENT,\n"

            + " photo text NOT NULL, \n"
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
