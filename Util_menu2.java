package org.example.javanetworking.Utility;

import org.example.javanetworking.CricketManagementSystem.CricketManagementSystem;
import org.example.javanetworking.Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util_menu2 {
    private static CricketManagementSystem system;

    static {
        try {
            system = new CricketManagementSystem();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Util_menu2(CricketManagementSystem system)
    {
       this.system = system;
    }



    public static List<Player> searchbymaxsalary(String Club)
    {
        List<Player> maxsal = new ArrayList<>();
        double max=-1;
        for(Player p:system.getAllPlayers())
        {
            if(p.getClub().equalsIgnoreCase(Club))
            {

                 if(p.getSalary()>max)
                {
                    max = p.getSalary();
                    maxsal.clear();
                    maxsal.add(p);
                }
                 else if(p.getSalary()==max) {
                     maxsal.add(p);
                 }
            }
        }
        return maxsal;
    }
    public static List<Player> searchbymaxage(String Club)
    {
        List<Player> maxage = new ArrayList<>();
        int max=-1;
        for(Player p:system.getAllPlayers())
        {
            if(p.getClub().equalsIgnoreCase(Club))
            {

                 if(p.getAge()>max)
                {
                    max = p.getAge();
                    maxage.clear();
                    maxage.add(p);
                }
                 else if (p.getAge()==max) {
                maxage.add(p);
            }
            }
        }

        return maxage;
    }
    public static List<Player> searchbymaxheight(String Club)
    {
        List<Player> maxheight = new ArrayList<>();
        double height = -1;
        for(Player p:system.getAllPlayers())
        {
            if(p.getClub().equalsIgnoreCase(Club))
            {

                if(p.getHeight()>height)
                {
                    height = p.getHeight();
                    maxheight.clear();
                    maxheight.add(p);
                }
                else if (p.getHeight()==height) {
                    maxheight.add(p);
                }
            }
        }

        return maxheight;
    }
    public static long annualsal(String Club)
    {
        String clubName = Club.trim().toLowerCase();
        long sal=0;
        for(Player p:system.getAllPlayers())
        {
            if(p.getClub().equalsIgnoreCase(Club))
            {
               sal+=p.getSalary();
            }
        }
        return sal*52;
    }
    public static List<Player> salaryRange(String country, int low, int high)
    {
        List<Player> maxsalary = new ArrayList<>();
        for(Player p:system.getAllPlayers())
        {
            if(p.getCountry().equalsIgnoreCase(country) && p.getSalary()>=low && p.getSalary()<=high)
            {
                maxsalary.add(p);
            }
        }
        System.out.println("Exiting salary range in util2");
        return maxsalary;
    }

    public static void avgsalary() {
        Map<String, Integer> count = new HashMap<>();
        Map<String, Integer> salary = new HashMap<>();
        System.out.println("In util menu2");

        for (Player p : system.getAllPlayers()) {
            String country = p.getCountry();
            int sal = p.getSalary();
            if(!count.containsKey(country))
            {
                count.put(country,1);
            }
            else
            {
                int c = count.get(country);
                c=c+1;
                count.put(country,c);
            }
            if(!salary.containsKey(country))
            {
                salary.put(country, p.getSalary());
            }
            else {
                int totalsalary = salary.get(country);
                totalsalary += sal;
                salary.put(country, totalsalary);
            }
        }

        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            String country = entry.getKey();
            int playerCount = entry.getValue();
            int totalSalary = salary.get(country);

            int avgSalary = totalSalary / playerCount;
            System.out.println("Average salary of " + country + ": " + avgSalary);
        }
    }



    public static boolean hasClub(String Club)
    {
       return true;
    }



}
