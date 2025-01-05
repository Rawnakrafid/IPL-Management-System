package org.example.javanetworking.DTO;

import org.example.javanetworking.Player.Player;

import java.io.Serializable;

public class TransferReq implements Serializable {
    public String NewClub;
    public Player p;

    public TransferReq(String NewClub,Player p){
        this.NewClub=NewClub;
        this.p = p;
    }
}