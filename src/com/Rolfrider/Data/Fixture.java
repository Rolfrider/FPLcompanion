package com.Rolfrider.Data;

public class Fixture {
    private int id, difficulty, code, minutes,
        event, teamA, teamH;

    private String kickoffTimeFormatted, eventName,
        opponentName, opponentShortName;

    private boolean isHome, finished, provisionalStartTime,
        finishedProvisional;

    public int getId() {
        return id;
    }
}
