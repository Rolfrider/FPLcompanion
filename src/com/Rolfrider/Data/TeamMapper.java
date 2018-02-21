package com.Rolfrider.Data;

import java.util.HashMap;
import java.util.Map;

public class TeamMapper {

    private static final Map<Integer, String> teamMap = new HashMap<Integer, String>(){
        {
            put(1, "ARS");
            put(2, "BOU");
            put(3, "BHA");
            put(4, "BUR");
            put(5, "CHE");
            put(6, "CRY");
            put(7, "EVE");
            put(8, "HUD");
            put(9, "LEI");
            put(10, "LIV");
            put(11, "MCI");
            put(12, "MUN");
            put(13, "NEW");
            put(14, "SOU");
            put(15, "STK");
            put(16, "SWA");
            put(17, "TOT");
            put(18, "WAT");
            put(19, "WBA");
            put(20, "WHU");
        }
    };

    public static String teamKeyMap(int team){
        String teamName;
        teamName = teamMap.get(team);
        return teamName;
    }

    public static int teamNameMap(String team){
        int key = 0;
        for(int i = 1 ; i <= 20 ; i++){
            if(team.equals(teamMap.get(i))){
                key = i;
                break;
            }
        }
        return key;
    }
}
