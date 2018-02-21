package Test;

import com.Rolfrider.Data.DataReader;
import com.Rolfrider.Data.PlayerDetails;

public class Test {
    public static void main(String[] args) {
        PlayerDetails playerDetails = DataReader.ReadPlayersDetails("22");
        System.out.println(playerDetails.getFixtures()[0].getOpponent_short_name());
    }
}
