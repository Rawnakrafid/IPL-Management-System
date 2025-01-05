package org.example.javanetworking;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.javanetworking.Player.Player;
import org.example.javanetworking.CricketManagementSystem.CricketManagementSystem;
public class AddPlayer {
public CricketManagementSystem system;
    @FXML
    private TextField age;

    @FXML
    private TextField club;

    @FXML
    private TextField country;

    @FXML
    private TextField height;

    @FXML
    private TextField name;

    @FXML
    private TextField position;

    @FXML
    private TextField position1;

    @FXML
    private TextField position11;

    @FXML
    private Label status;
    @FXML
    private Button addplayer;

    @FXML
    public boolean addPlayer() {
        String nameText = name.getText().trim();
        String countryText = country.getText().trim();
        String clubText = club.getText().trim();
        String positionText = position.getText().trim();
        String position1Text = position1.getText().trim();
        String position11Text = position11.getText().trim();

        int ageValue = 0;
        double heightValue = 0.0;
        int salaryValue = 0;
        int number = -1;

        if (nameText.isEmpty() || countryText.isEmpty() || clubText.isEmpty() || positionText.isEmpty()) {
            status.setText("Please fill in all required fields.");
            status.setStyle("-fx-text-fill: red;");
            return false;
        }

        try {
            ageValue = Integer.parseInt(age.getText().trim());
            if (ageValue <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            status.setText("Please enter a valid positive age.");
            status.setStyle("-fx-text-fill: red;");
            return false;
        }

        try {
            heightValue = Double.parseDouble(height.getText().trim());
            if (heightValue <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            status.setText("Please enter a valid positive height.");
            status.setStyle("-fx-text-fill: red;");
            return false;
        }

        try {
            salaryValue = Integer.parseInt(position11Text.trim());
            if (salaryValue <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            status.setText("Please enter a valid positive salary.");
            status.setStyle("-fx-text-fill: red;");
            return false;
        }

        if (!position1Text.isEmpty()) {
            try {
                number = Integer.parseInt(position1Text.trim());
                if (number < 0) {
                    status.setText("Jersey number cannot be negative.");
                    status.setStyle("-fx-text-fill: red;");
                    return false;
                }
            } catch (NumberFormatException e) {
                status.setText("Please enter a valid number for Jersey Number.");
                status.setStyle("-fx-text-fill: red;");
                return false;
            }
        }

        Player newPlayer;
        if (number != -1) {
            newPlayer = new Player(nameText, countryText, ageValue, heightValue, clubText, positionText, number, salaryValue);
        } else {
            newPlayer = new Player(nameText, countryText, ageValue, heightValue, clubText, positionText, salaryValue);
        }

        if (system.addplayertodatabase(newPlayer)) {
            status.setText("Player added successfully.");
            status.setStyle("-fx-text-fill: green;");
            return true;
        } else {
            status.setText("Failed to add player. Player with the same name might already exist.");
            status.setStyle("-fx-text-fill: red;");
            return false;
        }
    }
}
