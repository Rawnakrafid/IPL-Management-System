package org.example.javanetworking;
import org.example.javanetworking.CricketManagementSystem.CricketManagementSystem;
import org.example.javanetworking.Search.*;

import java.util.Scanner;

import org.example.javanetworking.Utility.*;

public class Mainofprev {
    public static void main(String[] args) throws Exception {
        CricketManagementSystem newsystem = new CricketManagementSystem();
        Scanner scanner = new Scanner(System.in);
        boolean status=true;
        while(status)
        {
            System.out.println();
            System.out.println("Main Menu: ");
            System.out.println("(1) Search Players: ");
            System.out.println("(2) Search Clubs: ");
            System.out.println("(3) Add Players: ");
            System.out.println("(4) Exit System: ");
            System.out.println("(5) Remove Player:  ");
            int userInput = scanner.nextInt();
            scanner.nextLine();
            switch(userInput)
            {
                case 1:
                {
                    new playersearching();
                    System.out.println();
                    break;
                }
                case 2:
                {
                    new clubsearching();
                    System.out.println();
                    break;
                }
                case 3:
                {
                  boolean add_marker = new Util_menu3(newsystem).addPlayer();
                   if(add_marker) {
                       newsystem.savePlayers();
                       System.out.println("Player added successfully!");
                   }
                    System.out.println();
                    break;
                }
                case 4:
                {
                    System.out.println("Exiting the system. Remember to come back again!");
                    System.out.println();
                    newsystem.savePlayers();
                    status=false;
                    break;
                }
                case 5:
                {
                    boolean remove = new Util_menu3(newsystem).removePlayer();

                }

                default:
                    System.out.println("Default in main");
            }
        }
    }
    }

