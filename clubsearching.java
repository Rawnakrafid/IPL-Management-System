package org.example.javanetworking.Search;

import org.example.javanetworking.Player.Player;
import org.example.javanetworking.*;
import org.example.javanetworking.Utility.Util_menu2;
import java.math.BigDecimal;
import java.util.*;

public class clubsearching {

    {
        Scanner scanner = new Scanner(System.in);
        boolean search = true;
        while (search) {
            System.out.println("Club Searching Options: ");

            System.out.println("(1) Player(s) with the maximum salary: ");
            System.out.println("(2) Player(s) with the maximum age: ");
            System.out.println("(3) Player(s) with the maximum height: ");
            System.out.println("(4) Total yearly salary: ");
            System.out.println("(5) Countryrange: ");
            System.out.println("(6) Country wise salary range");
            System.out.println("(7) Back to Main Menu: ");
            int input_in_search;
            while (true) {
                try {
                    int input = scanner.nextInt();
                    scanner.nextLine();
                    if ((input >= 1) && (input <= 7)) {
                        input_in_search = input;
                        break;
                    } else {
                        System.out.println("Looks like it is not a valid option! Shall we try again?");
                        System.out.println("Enter your choice again");

                    }

                } catch (InputMismatchException e) {
                    System.out.println("Oops! Wrong format");
                    scanner.nextLine();
                }
            }

                switch (input_in_search) {
                    case 1: {
                        System.out.println("Which club are you looking for? ");
                        String name = scanner.nextLine();
                        List<Player> players = Util_menu2.searchbymaxsalary(name);
                        if (players.isEmpty()) {
                            System.out.println("No players found for the given club.");
                        } else {
                            for (Player p : players) {
                                p.print();
                                System.out.println();
                            }
                        }
                        System.out.println();
                        break;
                    }
                    case 2: {
                        System.out.println("Which club's players are you looking for?");
                        String country = scanner.nextLine();
                        List<Player> players = Util_menu2.searchbymaxage(country);

                        if (players.isEmpty()) {
                            System.out.println("No players found for the given club.");
                        } else {
                            for (Player p : players) {
                                p.print();
                                System.out.println();
                            }
                        }
                        System.out.println();
                        break;
                    }
                    case 3: {
                        System.out.println("Which club's players are you looking for?");
                        String country = scanner.nextLine();
                        List<Player> players = Util_menu2.searchbymaxheight(country);
                        if (players.isEmpty()) {
                            System.out.println("No players found for the given club.");
                        } else {
                            for (Player p : players) {
                                p.print();
                                System.out.println();
                            }
                        }
                        System.out.println();
                        break;
                    }
                    case 4: {
                        System.out.println("Which club's players are you looking for?");
                        String club =scanner.nextLine();
                       long result = Util_menu2.annualsal(club);
                       String answer =  BigDecimal.valueOf(result).stripTrailingZeros().toPlainString();
                        System.out.println("Total yearly salary for "+club+" is: "+result);
                        System.out.println();
                        break;
                    }
                    case 5: {
                        System.out.println("Enter country name:");
                        String country = scanner.nextLine();
                        System.out.println("Enter low range");
                        int low = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter high range");
                        int high = scanner.nextInt();
                        scanner.nextLine();
                        List<Player> Countryrange = Util_menu2.salaryRange(country, low, high);
                        System.out.println("Before loop");
                        if(Countryrange.isEmpty()) System.out.println("List is empty");
                        for(Player p: Countryrange)
                        {
                            p.print();
                        }
                        System.out.println("After loop");
                        break;

                    }
                    case 6:
                    {System.out.println("Country wise salary range");
                      Util_menu2.avgsalary();
                      break;
                    }

                    case 7:{
                        System.out.println("Exiting");
                        search = false;
                        break;
                    }
                    default:
                        throw new IllegalStateException("Unexpected value: " + input_in_search);
                }



        }
    }
}
