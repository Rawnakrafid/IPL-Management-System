package org.example.javanetworking.DTO;

import org.example.javanetworking.Player.Player;

import java.io.Serializable;

    public class BuyReq implements Serializable {
        private Player player;
        private String playerCurrentClub;
        private double amount;

        public BuyReq(Player playerName, String playerCurrentClub, Double amount) {
            this.player = playerName;
            this.playerCurrentClub = playerCurrentClub;
            this.amount = amount;
        }

        public Player getPlayer() {
            return player;
        }

        public String getClub() {
            return playerCurrentClub;
        }

        public double getAmount() {
return amount ;       }

        String serialize()
        {
            return playerCurrentClub+","+player.getName()+","+amount;
        }
    }

