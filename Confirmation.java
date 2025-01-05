package org.example.javanetworking.DTO;
import org.example.javanetworking.Player.Player;

import java.io.Serializable;

public class Confirmation implements Serializable {
    public Player p;
    public String newCLub;

    public Confirmation(Player p, String Club) {
        this.newCLub = Club;
        this.p = p;
    }
    public Player getPlayer()
    {
        return p;
    }
    public String getClub()
    {
        return newCLub;
    }

}