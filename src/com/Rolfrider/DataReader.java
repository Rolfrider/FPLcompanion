package com.Rolfrider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DataReader {
    static ArrayList<Player> Read(){
        ArrayList<Player> players = null;
        try {
            URL url = new URL("https://fantasy.premierleague.com/drf/elements/");
            InputStreamReader reader = new InputStreamReader(url.openStream());
            Type playerListType = new TypeToken<ArrayList<Player>>() {
            }.getType();
            players = new Gson().fromJson(reader, playerListType);
        }
        catch (IOException e){
            System.out.println("Cant download the data");
            System.out.println("Returned a null list");
        }


        return players;
    }
}
