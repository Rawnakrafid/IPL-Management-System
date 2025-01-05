package org.example.javanetworking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.javanetworking.Player.Player;
import org.example.javanetworking.Search.playersearching;
import org.example.javanetworking.Utility.Util_menu1;

import java.util.List;
import java.util.Map;

public class PlayerSearch {

    @FXML
    private Button ByClubandCountry;

    @FXML
    private TextField Byname;

    @FXML
    private Button SearchByName;

    @FXML
    private Button Searchbyposition;

    @FXML
    private TextField byclib;

    @FXML
    private TextField bycountry;

    @FXML
    private Button bysal;

    @FXML
    private Button Back;

    @FXML
    private Button count;

    @FXML
    private ListView<Text> displayresult;

    @FXML
    private TextField maxsal;

    @FXML
    private TextField minsal;

    @FXML
    private TextField position;

    @FXML
    public void onSearchByName(ActionEvent event) {
        byclib.clear();
        bycountry.clear();
        maxsal.clear();
        minsal.clear();
        position.clear();

        String name = Byname.getText();
        if (name == null || name.isEmpty()) {
            displayresult.getItems().clear();
            displayresult.getItems().add(new Text("Please enter a player name."));
            return;
        }

        Player player = playersearching.searchByName(name);
        displayresult.getItems().clear();
        if (player == null) {
            displayresult.getItems().add(new Text("No player found with the name: " + name));
        } else {
            displayresult.getItems().add(new Text(player.toString()));
        }
    }

    @FXML
    public void onSearchByClubAndCountry(ActionEvent event) {
        maxsal.clear();
        minsal.clear();
        position.clear();
        Byname.clear();

        String country = bycountry.getText();
        String club = byclib.getText();

        displayresult.getItems().clear();

        if ((country == null || country.isEmpty()) || (club == null || club.isEmpty())) {
            displayresult.getItems().add(new Text("Please enter both country and club."));
            return;
        }

        List<Player> players = playersearching.searchByClubAndCountry(country, club);
        if (players.isEmpty()) {
            displayresult.getItems().add(new Text("No players found for the given country and club."));
        } else {
            for (Player player : players) {
                displayresult.getItems().add(new Text(player.toString()));
            }
        }
    }

    @FXML
    public void onSearchByPosition(ActionEvent event) {
        byclib.clear();
        bycountry.clear();
        maxsal.clear();
        minsal.clear();
        Byname.clear();

        String pos = position.getText();
        displayresult.getItems().clear();

        if (pos == null || pos.isEmpty()) {
            displayresult.getItems().add(new Text("Please enter a position."));
            return;
        }

        List<Player> players = playersearching.searchByPosition(pos);
        if (players.isEmpty()) {
            displayresult.getItems().add(new Text("No players found for the position: " + pos));
        } else {
            for (Player player : players) {
                displayresult.getItems().add(new Text(player.toString()));
            }
        }
    }

    @FXML
    public void onSearchBySalary(ActionEvent event) {
        byclib.clear();
        bycountry.clear();
        position.clear();
        Byname.clear();
        displayresult.getItems().clear();
        int minSalary, maxSalary;

        try {
            minSalary = Integer.parseInt(minsal.getText());
            maxSalary = Integer.parseInt(maxsal.getText());
        } catch (NumberFormatException e) {
            displayresult.getItems().add(new Text("Please enter valid salary values."));
            return;
        }

        if (minSalary > maxSalary) {
            displayresult.getItems().add(new Text("Minimum salary cannot be greater than maximum salary."));
            return;
        }

        List<Player> players = playersearching.searchBySalary(minSalary, maxSalary);
        if (players.isEmpty()) {
            displayresult.getItems().add(new Text("No players found within the salary range."));
        } else {
            for (Player player : players) {
                displayresult.getItems().add(new Text(player.toString()));
            }
        }
    }
    @FXML
    public void onSearchCountrywise(ActionEvent event) {
        clearAllFields();
        Map<String, Integer> players = Util_menu1.countrywiseCount();
        displayresult.getItems().clear();

        if (players.isEmpty()) {
            displayMessage("No players found.");
        } else {
            players.forEach((country, count) -> {
                displayMessage(country + ": " + count + " players");
            });
        }
    }

    private String formatPlayerDetails(Player player) {
        return String.format("%-15s | %-15s | %-4d | %-10s | %-12s | %-12s | %s",
                player.getName(),
                player.getClub(),
                player.getAge(),
                player.getCountry(),
                player.getPosition(),
                (player.getNumber() == -1 ? "Not available" : player.getNumber()),
                player.getSalary());
    }

    private void displayMessage(String message) {
        displayresult.getItems().add(new Text(message));
    }

    private void clearAllFields() {
        byclib.clear();
        bycountry.clear();
        maxsal.clear();
        minsal.clear();
        position.clear();
        Byname.clear();
    }
public void gotoGuest() throws Exception
{
    application.gotoGuestfromPlayerSearch();
}

    public Main application;

    public void setApplication(Main application) {
        this.application = application;
    }

    public Main getApplication() {
        return application;
    }
}
