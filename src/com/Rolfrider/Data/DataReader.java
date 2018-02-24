package com.Rolfrider.Data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;


// url for players https://fantasy.premierleague.com/drf/elements/
// url for detailed player info https://fantasy.premierleague.com/drf/element-summary/{player id}
// url for photos https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p{photo id}.png


public class DataReader {
    private final static String playersUrl = "https://fantasy.premierleague.com/drf/elements/",
                        detailsUrl = "https://fantasy.premierleague.com/drf/element-summary/",
                        photoUrl = "https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p";

    public static ArrayList<Player> ReadPlayers(){
        ArrayList<Player> players = null;
        try {
            URL url = new URL(playersUrl);
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

    public static PlayerDetails ReadPlayersDetails(String id){
        PlayerDetails player = new PlayerDetails();
        try {
            URL url = new URL( detailsUrl + id);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            player = new Gson().fromJson(reader, PlayerDetails.class);
        }
        catch (IOException e){
            System.out.println("Cant download the data");
            System.out.println("Returned a null list");
        }


        return player;
    }

    public static Image getPhoto(String photoId){
        return new Image( photoUrl + photoId, true );
    }

}
