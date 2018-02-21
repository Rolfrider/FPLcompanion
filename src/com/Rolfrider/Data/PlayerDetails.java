package com.Rolfrider.Data;

public class PlayerDetails {

    private GameStats [] history;
    private Fixture [] fixtures;

    public GameStats[] getHistory() {
        return history;
    }

    public Fixture[] getFixtures() {
        return fixtures;
    }

    public int getFailedPasses(){
        int passes = 0;
        for (GameStats gs: history)
            passes += gs.getFailedPasses();
        return passes;
    }

    public int getCompletedPasses(){
        int passes = 0;
        for (GameStats gs: history)
            passes += gs.getCompleted_passes();
        return passes;
    }

    public int getSaves(){
        int saves = 0;
        for (GameStats gs: history)
            saves+= gs.getSaves();
        return saves;
    }

    public int getGoalErrors(){
        int errors = 0;
        for (GameStats gs: history)
            errors+= gs.getErrors_leading_to_goal();
        return errors;
    }

    public int getPenaltiesSaved(){
        int saves = 0;
        for (GameStats gs: history)
            saves+= gs.getSaves();
        return saves;
    }


}
