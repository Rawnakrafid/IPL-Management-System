package org.example.javanetworking.File;
import org.example.javanetworking.Player.Player;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Filehandling {
    private static final String INPUT_FILE = "/Users/rawnakmubtasimrafid/Desktop/JavaNetworking Where transferlist works/src/main/java/org/example/javanetworking/File/players.txt";
    private static final String OUTPUT_FILE = "/Users/rawnakmubtasimrafid/Desktop/JavaNetworking Where transferlist works/src/main/java/org/example/javanetworking/File/players.txt";

    private Player Parseplayer(String player) {
        try {
            StringTokenizer token = new StringTokenizer(player, ",");
            if(token.countTokens()==7)
            {
                String name = token.nextToken();
                String country = token.nextToken();
                int age = Integer.parseInt(token.nextToken());
                double height = Double.parseDouble(token.nextToken());
                String club = token.nextToken();
                String position = token.nextToken();
                int salary = Integer.parseInt(token.nextToken());

                Player newPlayer = new Player(name, country, age, height, club, position, salary);
                return newPlayer;

            }
            else if(token.countTokens()==8) {
                String name = token.nextToken();
                String country = token.nextToken();
                int age = Integer.parseInt(token.nextToken());
                double height = Double.parseDouble(token.nextToken());
                String club = token.nextToken();
                String position = token.nextToken();
                int number = Integer.parseInt(token.nextToken());
                int salary = Integer.parseInt(token.nextToken());

                Player newPlayer = new Player(name, country, age, height, club, position, number, salary);
                return newPlayer;
            }
            else
            {
                return null;
            }


        }
        catch (Exception e)
        {
            System.err.println("Invalid data for a player");
            return null;
        }
    }

    private String makePlayer(Player player)
    {
        String formattedsalary = BigDecimal.valueOf(player.getSalary()).stripTrailingZeros().toPlainString();
        if(player.getNumber()!=-1) {
            return (player.getName() + "," + player.getCountry() + "," + player.getAge() + "," + player.getHeight() + "," + player.getClub() + "," + player.getPosition() + "," + player.getNumber() + "," + formattedsalary);
        }
        else return ((player.getName() + "," + player.getCountry() + "," + player.getAge() + "," + player.getHeight() + "," + player.getClub() + "," + player.getPosition() + "," + "," + formattedsalary));
    }

    public List<Player> loadPlayers() throws Exception {
        List<Player> playerList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
        try {
            while (true) {
                String line = br.readLine();
                if(line==null) break;

                Player player = Parseplayer(line);
                if(player!=null)
                {
                    playerList.add(player);
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("Error loading players");
        }

        return playerList;
    }
    public void putPlayers(List<Player> players) throws Exception
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE) )){
            for(Player p : players )
            {
                    bw.write(makePlayer(p));
                    bw.newLine();
            }

        }
        catch(Exception e)
        {
            System.err.println("Error adding player in the database");
        }
    }


}
