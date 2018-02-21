package com.Rolfrider.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
To do:
change names to camelCase.
 */


public class Player {
    private int     id,
            team_code,
            code,
            squad_number,
            now_cost,
            chance_of_playing_this_round,
            chance_of_playing_next_round,
            cost_change_start,
            cost_change_event,
            cost_change_start_fall,
            cost_change_event_fall,
            dreamteam_count,
            transfers_out,
            transfers_in,
            transfers_out_event,
            transfers_in_event,
            loans_in,
            loans_out,
            loaned_in,
            loaned_out,
            total_points,
            event_points,
            minutes,
            goals_scored,
            assists,
            clean_sheets,
            goals_conceded,
            own_goals,
            penalties_saved,
            penalties_missed,
            yellow_cards,
            red_cards,
            saves,
            bonus,
            bps,
            ea_index,
            element_type,
            team;

    private boolean in_dreamteam,
            special;

    private String  photo,
            web_name,
            status,
            first_name,
            second_name,
            news,
            news_added,
            value_form,
            value_seson ,
            selected_by_percent,
            form,
            points_per_game,
            ep_this,
            ep_next,
            influence,
            creativity,
            threat,
            ict_index;



    public int getId(){return id;}

    public void setField(String name, Object value){
        try {
            Field field = this.getClass().getDeclaredField(name);
            field.set(this, value);
        }catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e){
        System.out.println(e.getMessage());
        }
    }



    @Override
    public String toString() {
        return "Name: " + this.web_name + "\t Cost: " +
                (float)this.now_cost/10 + "\t Points: " + this.total_points +
                "\t type: " + this.element_type +
                "\t Chance of playing: " + this.chance_of_playing_next_round +
//                "\t Cost change start: " + (float)this.cost_change_start/10+
//                "\t Cost change event: " + (float)this.cost_change_event/10 +
//                "\t Cost change eve fall: " + (float)this.cost_change_event_fall/10+
//                "\t Cost change start fall: " + (float)this.cost_change_start_fall/10
                "\t Status: " + this.status;
    }

    public ArrayList<String> getIntFieldsNames(){
        ArrayList<String> fieldsNames = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field f : fields){
            if(f.getType() == int.class){
                fieldsNames.add(f.getName());
            }
        }
        return fieldsNames;
    }

    public String getElement_type() {
        String position = "";
        switch (element_type){
            case 1:
                position = "GKP";
                break;
            case 2:
                position = "DEF";
                break;
            case 3:
                position = "MID";
                break;
            case 4:
                position = "FWD";
                break;
        }
        return position;
    }


    public String getFirst_name() {
        return first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public int getGoals_scored() {
        return goals_scored;
    }

    public int getAssists() {
        return assists;
    }

    public String getPhoto() {
        return photo;
    }

    public String getNews() {
        return news;
    }

    public float getPoints_per_game() {
        return Float.parseFloat(points_per_game);
    }

    public float getNow_cost() {
        return (float)now_cost/10;
    }

    public int getTotal_points() {
        return total_points;
    }

    public String getTeam() {
        return TeamMapper.teamKeyMap(team);
    }

    public String getWeb_name(){
        return web_name;
    }

    public Float getSelected_by_percent() {
        return Float.parseFloat(selected_by_percent);
    }

    public Float getForm() {
        return Float.parseFloat(form);
    }

    public Float getIct_index() {
        return Float.parseFloat(ict_index);
    }


    public ArrayList<String> getStringFieldsNames(){
        ArrayList<String> fieldsNames = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field f : fields){
            if(f.getType() == String.class){
                fieldsNames.add(f.getName());
            }
        }

        return fieldsNames;
    }

    public ArrayList<Integer> getIntData(){
        ArrayList<Integer> intData = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field f : fields){
            if(f.getType()==int.class)
                try {
                    intData.add((Integer) f.get(this));
                }catch (IllegalAccessException e){
                    System.out.println(e.getMessage());
                }

        }
        return intData;
    }

    public ArrayList<String> getStringData(){
        ArrayList<String> sData = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field f : fields){
            if(f.getType()==String.class)
                try {
                    sData.add((String) f.get(this));
                }catch (IllegalAccessException e){
                    System.out.println(e.getMessage());
                }

        }
        return sData;
    }




}
