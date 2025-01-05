package org.example.javanetworking.Utility;

import org.example.javanetworking.CricketManagementSystem.CricketManagementSystem;
import org.example.javanetworking.CricketManagementSystem.Database;
import org.example.javanetworking.Player.Player;
import java.util.Scanner;

public class Util_menu3 {
    private CricketManagementSystem system;

    public Util_menu3(CricketManagementSystem system) {
        this.system = system;
    }

    public boolean addPlayer() {
        System.out.println("Please provide the following info for new player");
        Scanner scanner = new Scanner(System.in);
        String name;
        while (true) {
            try {
                System.out.println("Name:");
                name = scanner.nextLine().trim();
                if (!name.isEmpty()) break;
                System.out.println("Please put a valid name, name cannot be empty!");
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid name.");
            }
        }
        String country;
        while (true) {
            try {
                System.out.println("Which country is the player from?");
                country = scanner.nextLine().trim();
                if (!country.isEmpty()) break;
                System.out.println("Please enter a valid country. Country name cannot be empty!");
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid country.");
            }
        }

        int age;
        while (true) {
            try {
                System.out.println("How old is he?");
                age = scanner.nextInt();
                if (age > 0) break;
                System.out.println("Please enter a valid age. Age cannot be negative!");
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid integer for age.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        double height;
        while (true) {
            try {
                System.out.println("What is his height (in meters)?");
                height = scanner.nextDouble();
                if (height >= 0) break;
                System.out.println("Please enter a valid height. Height cannot be negative!");
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid height.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        String club;

        while (true) {
            try {
                System.out.println("Which club does he play in?");
                club = scanner.nextLine().trim();
                if (!club.isEmpty()) break;
                System.out.println("Please enter a valid club name. Club name cannot be empty!");
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid club.");
            }
        }
        String position;
        while (true) {
            try {
                System.out.println("What position does he play in?");
                position = scanner.nextLine().trim();
                if (!position.isEmpty()) break;
                System.out.println("Please enter a valid position. Position cannot be empty!");
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid position.");
            }
        }
        int number = -1;

        while (true) {
            try {
                System.out.println("What is his jersey number? (or to skip it, press enter)");
                String input = scanner.nextLine();
                if(input.isEmpty())
                {
                    break;
                }
                else {
                    try {
                        number = Integer.parseInt(input);
                        if(number<0)
                        {
                            System.out.println("Jersey number cannot be negative");
                            number=-1;
                        }
                        else
                            break;
                    } catch (Exception e) {
                        System.out.println();
                        System.out.println("Invalid input! Please enter a valid number for Jersey Number.");
                        scanner.nextLine();
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number for Jersey Number.");
                scanner.nextLine();
            }
        }



        int salary;
        while (true) {
            try {
                System.out.println("What is his weekly salary?");
                salary = scanner.nextInt();
                if (salary > 0) break;
                System.out.println("Salary must be non-negative. Please try again.");
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number for salary.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        if (number != -1) {
            Player newPlayer = new Player(name, country, age, height, club, position, number, salary);
            return system.addplayertodatabase(newPlayer);

        } else {
            Player newPlayer = new Player(name, country, age, height, club, position, salary);
            return system.addplayertodatabase(newPlayer);
        }

    }
    public boolean removePlayer() throws Exception {
        System.out.println("The name of the player you want to remove:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
       if( system.removePlayerfromdatabase(name)){ System.out.println("Player removed"); return true;}
       else {System.out.println("No player found"); return false;}
    }


}
