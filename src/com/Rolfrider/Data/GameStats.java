package com.Rolfrider.Data;

public class GameStats {
    private int id,team_h_score,team_a_score,
        round, total_points, value,
        transfers_balance, selected,
        transfers_in, transfers_out,
        minutes, goals_scored, assists,
        clean_sheets, goals_conceded,
        own_goals, penalties_saved,
        penalties_missed, yellow_cards,
        red_cards, saves, bonus, bps,
        ea_index, open_play_crosses,
        big_chances_created, clearances_blocks_interceptions,
        recoveries, key_passes, tackles,
        winning_goals, attempted_passes,
        completed_passes, penalties_conceded,
        big_chances_missed, errors_leading_to_goal,
        errors_leading_to_goal_attempt, tackled,
        offsides, target_missed, fouls,
        dribbles, element, fixture,
        opponent_team;

    private boolean was_home;

    private String kickoff_time_formatted, influence,
            creativity, threat, ict_index;

    public int getId() {
        return id;
    }

    public int getBig_chances_missed() {
        return big_chances_missed;
    }

    public int getFailedPasses(){
        return getAttempted_passes() - getCompleted_passes();
    }


    public int getTarget_missed() {
        return target_missed;
    }

    public int getBig_chances_created() {
        return big_chances_created;
    }

    public int getKey_passes() {
        return key_passes;
    }

    public int getDribbles() {
        return dribbles;
    }

    public int getClearances_blocks_interceptions() {
        return clearances_blocks_interceptions;
    }

    public int getTackles() {
        return tackles;
    }

    public int getErrors_leading_to_goal() {
        return errors_leading_to_goal;
    }

    public int getAttempted_passes() {
        return attempted_passes;
    }

    public int getCompleted_passes() {
        return completed_passes;
    }

    public int getTeam_h_score() {
        return team_h_score;
    }

    public int getTeam_a_score() {
        return team_a_score;
    }

    public int getTotal_points() {
        return total_points;
    }

    public float getValue() {
        return (float) value/10;
    }

    public int getTransfers_balance() {
        return transfers_balance;
    }

    public int getSelected() {
        return selected;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getGoals_scored() {
        return goals_scored;
    }

    public int getAssists() {
        return assists;
    }

    public int getClean_sheets() {
        return clean_sheets;
    }

    public int getGoals_conceded() {
        return goals_conceded;
    }

    public int getOwn_goals() {
        return own_goals;
    }

    public int getPenalties_saved() {
        return penalties_saved;
    }

    public int getPenalties_missed() {
        return penalties_missed;
    }

    public int getYellow_cards() {
        return yellow_cards;
    }

    public int getRed_cards() {
        return red_cards;
    }

    public int getSaves() {
        return saves;
    }

    public int getBonus() {
        return bonus;
    }

    public String getOpponent_team() {
        String opp  = TeamMapper.teamKeyMap(opponent_team);
        if(was_home)
            opp+= " (H)";
        else
            opp+= " (A)";
        opp+= " " + getTeam_h_score() + "-" + getTeam_a_score();

        return opp;
    }

    public float getIct_index() {
        return Float.parseFloat(ict_index);
    }

    public int getRound() {
        return round;
    }
}
