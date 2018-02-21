package com.Rolfrider.Data;

public class Fixture {
    private int id, difficulty, code, minutes,
        event, teamA, teamH;

    private String kickoff_time_formatted, event_name,
        opponent_name, opponent_short_name;

    private boolean is_home, finished, provisional_start_time,
        finished_provisional;

    public int getId() {
        return id;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getEvent() {
        return  event;
    }

    public String getOpponent_short_name() {
        String home ;
        if(is_home)
            home = " (H)";
        else
            home = " (A)";
        return opponent_short_name + home;
    }
}
