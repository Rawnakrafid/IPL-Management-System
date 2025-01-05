package org.example.javanetworking.CricketManagementSystem;
import org.example.javanetworking.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class CricketManagementSystem {
    public static List<Player> playerList;
    private static Database database;
    public CricketManagementSystem() throws Exception {
        database = new Database();
        database.LoadPlayerfromFile();
    }
    public List<Player> SendList()
    {
        playerList = database.getPlayer();
        return playerList;
    }
    public void addplayer(Player player)
    {
        database.addPlayer(player);
    }
    public boolean addplayertodatabase(Player player)
    {
       if (database.addPlayer(player))
       {
          try
          {
              database.savePlayertoFile();
          }
          catch (Exception e)
          {
              System.out.println("error saving to file");
              return false;
          }
          return true;
       }
       return false;
    }
    public void savePlayers() throws Exception {
        database.savePlayertoFile();
    }

    public List<Player> getAllPlayers() {
        return database.getPlayer();
    }
    public boolean removePlayerfromdatabase (String name) throws Exception {
      return database.removefromfile(name);
    }


    public List<Player> getPlayersByClub(String clubName) {
        List<Player> filteredPlayers = new ArrayList<>();
        for (Player player : database.getPlayer()) {
            if (player.getClub().equalsIgnoreCase(clubName)) {
                filteredPlayers.add(player);
            }
        }
        return filteredPlayers;
    }
}
