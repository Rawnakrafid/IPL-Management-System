package org.example.javanetworking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.javanetworking.Player.Player;

import java.io.InputStream;
import java.util.Map;

public class PlayerDetails {

    @FXML private Label nameLabel;
    @FXML private Label countryLabel;
    @FXML private Label positionLabel;
    @FXML private Label ageLabel;
    @FXML private Label clubLabel;
    @FXML private Label salaryLabel;
    @FXML private Label offeredFromLabel;
    @FXML private Label amountLabel;
    @FXML private ImageView ClubShow;

    private static final Map<String, String> CLUB_IMAGE_PATHS = Map.of(
            "Kolkata Knight Riders", "/org/example/javanetworking/kkr.png",
            "Royal Challengers Bangalore", "/org/example/javanetworking/rcb.jpeg",
            "Chennai Super Kings", "/org/example/javanetworking/csk.png",
            "Rajasthan Royals", "/org/example/javanetworking/rr.png",
            "Lucknow Super Giants", "/org/example/javanetworking/lsg.jpeg",
            "Delhi Capitals", "/org/example/javanetworking/dc.png",
            "Gujarat Titans", "/org/example/javanetworking/gt.png",
            "Mumbai Indians", "/org/example/javanetworking/mi.jpeg"
    );
    public void initialize(Player player) {
        setPlayerDetails(player);
        offeredFromLabel.setVisible(false);
        amountLabel.setVisible(false);
        setClubImage(player.getClub());
    }

    public void initialize(Player player, String offeredFrom, Double amount) {
        setPlayerDetails(player);
        if (offeredFrom != null && !offeredFrom.isEmpty()) {
            offeredFromLabel.setVisible(true);
            offeredFromLabel.setText("Offered From: " + offeredFrom);
        } else {
            offeredFromLabel.setVisible(false);
        }
        if (amount != null) {
            amountLabel.setVisible(true);
            amountLabel.setText("Offer Amount: " + amount);
        } else {
            amountLabel.setVisible(false);
        }

        setClubImage(player.getClub());
    }

    private void setPlayerDetails(Player player) {
        nameLabel.setText("Name: " + player.getName());
        countryLabel.setText("Country: " + player.getCountry());
        positionLabel.setText("Position: " + player.getPosition());
        ageLabel.setText("Age: " + player.getAge());
        clubLabel.setText("Club: " + player.getClub());
        salaryLabel.setText("Salary: " + player.getSalary());
    }

    private void setClubImage(String clubName) {
        String imagePath = CLUB_IMAGE_PATHS.get(clubName);
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream == null) {
            System.err.println("Image not found for path: " + imagePath);
            return;
        }
        Image clubImage = new Image(imageStream);
        ClubShow.setImage(clubImage);
    }
}
