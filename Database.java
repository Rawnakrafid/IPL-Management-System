package org.example.javanetworking.CricketManagementSystem;
import org.example.javanetworking.File.Filehandling;
import org.example.javanetworking.Player.Player;
import java.util.ArrayList;
import java.util.List;

public class Database {
   private  ArrayList<Player> Players;
   private Filehandling file;
   public Database() throws Exception {
       Players = new ArrayList<Player>();
       file = new Filehandling();
     }

   public List<Player> sendList()
   {
       return Players;
   }

   public void removefromlist(Player a)
   {
       for(Player p: Players)

       {
           if(p.getName().equalsIgnoreCase(a.getName()))
               Players.remove(p);
           return;
       }
   }

    public  boolean addPlayer(Player a)
    {
        for(Player p: Players)
        {
            if(a.getName().equalsIgnoreCase(p.getName()))
            {
                System.out.println("Player with same name exists! Cannot add the player");
                return false;
            }
        }
        Players.add(a);
        return true;
    }
    public List<Player> getPlayer()
    {
        return new ArrayList<>(Players);
    }
    public void LoadPlayerfromFile() throws Exception
    {
        Players = new ArrayList<>(file.loadPlayers());

    }
    public void savePlayertoFile() throws Exception{
       file.putPlayers(Players);
    }
    public boolean removefromfile(String name) throws Exception {
        for(Player p:Players)
        {
            if(p.getName().equalsIgnoreCase(name))
            {
                Players.remove(p);
                file.putPlayers(Players);
                return true;

            }
        }
        return false;
    }



}
