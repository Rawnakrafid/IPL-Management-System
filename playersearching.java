package org.example.javanetworking.Search;

import org.example.javanetworking.Player.Player;
import org.example.javanetworking.Utility.*;
import java.util.*;

public class playersearching {

            public static Player searchByName(String name) {
                return Util_menu1.searchByName(name);
            }

            public static List<Player> searchByClubAndCountry(String country, String club) {
                return Util_menu1.searchByClubandCountry(country, club);
            }

            public static List<Player> searchByPosition(String position) {
                return Util_menu1.searchByPostion(position);
            }

            public static List<Player> searchBySalary(int low, int high) {
                return Util_menu1.searchbySalary(low, high);
            }


        }


