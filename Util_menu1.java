package org.example.javanetworking.Utility;

import org.example.javanetworking.CricketManagementSystem.CricketManagementSystem;
import org.example.javanetworking.CricketManagementSystem.Database;
import org.example.javanetworking.Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util_menu1 {
    private static CricketManagementSystem system;

    static {
        try {
            system = new CricketManagementSystem();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Util_menu1(CricketManagementSystem System)
    {
        system=System;
    }

    public static Player searchByName(String name)
    {
        for(Player p: system.getAllPlayers())
        {
            if(p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    public static List <Player> searchByClubandCountry(String Country, String Club)
    {
        List<Player> clubandcountry = new ArrayList<>();
        if(Club.equalsIgnoreCase("ANY"))
        {
            for(Player p: system.getAllPlayers())
            {
            if(p.getCountry().equalsIgnoreCase(Country)) clubandcountry.add(p);
            }
        }
        else
        {
            for(Player p: system.getAllPlayers())
            {
                if(p.getCountry().equalsIgnoreCase(Country) && p.getClub().equalsIgnoreCase(Club)) clubandcountry.add(p);

            }
        }
        if(clubandcountry.isEmpty())
        {
            System.out.println("No player from "+Country+" plays in " + Club);
        }
        return clubandcountry;
    }
    public static List <Player> searchByPostion(String Position)
    {
        List<Player> position = new ArrayList<>();


            for(Player p: system.getAllPlayers())
            {
                if(p.getPosition().equalsIgnoreCase(Position)) position.add(p);
            }
        return position;
    }
    public static List<Player> searchbySalary(int low, int high)
    {
        List<Player> salary = new ArrayList<>();
        for(Player p: system.getAllPlayers())
        {
            if(p.getSalary()>=low && p.getSalary()<=high) salary.add(p);
        }
        return salary;
    }
    public static Map<String, Integer> countrywiseCount() {
        Map<String, List<Player>> countrywise = new HashMap<>();

        for (Player p : system.getAllPlayers()) {
            if (!countrywise.containsKey(p.getCountry())) {
                countrywise.put(p.getCountry(), new ArrayList<>());
            }
            countrywise.get(p.getCountry()).add(p);
        }

        Map<String, Integer> countryCounts = new HashMap<>();

        for (Map.Entry<String, List<Player>> entry : countrywise.entrySet()) {
            countryCounts.put(entry.getKey(), entry.getValue().size());
        }

        return countryCounts;
    }




}
