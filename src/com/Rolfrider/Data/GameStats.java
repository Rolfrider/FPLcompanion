package com.Rolfrider.Data;

public class GameStats {
    private int id,teamHScore,teamAScore,
        round, totalPoints, value,
        transferBalance, selected,
        transfersIn, transfersOut,
        minutes, goalsScored, assists,
        cleanSheets, goalsConceded,
        ownGoals, penaltiesSaved,
        penaltiesMissed, yellowCards,
        redCards, saves, bonus, bps,
        eaIndex, openPlayCrosses,
        bigChancesCreated, clearancesBlocksInterceptions,
        recoveries, keyPasses, tackles,
        winningGoals, attemptedPasses,
        completedPasses, penaltiesConceded,
        bigChancesMissed, errorsLeadingToGoal,
        errorsLeadingToGoalAttempt, tackled,
        offsides, targetMissed, fouls,
        dribbles, element, fixture,
        oponentTeam;

    private boolean wasHome;

    private String kickoffTimeFormatted, influence,
            creativity, threat, ictIndex;

    public int getId() {
        return id;
    }
}
