package org.example.javanetworking;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.example.javanetworking.Player.Player;
import org.example.javanetworking.Utility.Util_menu2;

import java.math.BigDecimal;
import java.util.List;

public class ClubSearch {
    @FXML
    private Button Back;

    @FXML
    private Label ClubName;

    @FXML
    private TextFlow CommandName;

    @FXML
    private TextField EnterClubName;

    @FXML
    private ListView<String> Result; // Change the type to ListView<String>

    @FXML
    private Button SearchByMaxAge;

    @FXML
    private Button SearchByMaxHeight;

    @FXML
    private Button SearchByMaxSalary;

    @FXML
    private Button SearchByTotalSalary;

    private Main application;

    public void setApplication(Main application) {
        this.application = application;
    }

    public Main getApplication() {
        return application;
    }

    @FXML
    public void handleSearchByMaxAge() {
        String clubName = EnterClubName.getText();
        List<Player> players = Util_menu2.searchbymaxage(clubName);
        displaySearchResults(players);
    }

    @FXML
    public void handleSearchByMaxHeight() {
        String clubName = EnterClubName.getText();
        List<Player> players = Util_menu2.searchbymaxheight(clubName);
        displaySearchResults(players);
    }

    @FXML
    public void handleSearchByTotalSalary() {
        String clubName = EnterClubName.getText();
        long result = Util_menu2.annualsal(clubName);
        String answer = BigDecimal.valueOf(result).stripTrailingZeros().toPlainString();
        Result.getItems().clear();
        Result.getItems().add("Total yearly salary for " + clubName + " is: \n" + answer);
    }

    @FXML
    public void handleSearchBymaxsal() {
        String country = EnterClubName.getText();
        List<Player> players = Util_menu2.searchbymaxsalary(country);
        displaySearchResults(players);
    }

    private void displaySearchResults(List<Player> players) {
        Result.getItems().clear();

        Result.getItems().add("Name                            | Club                        | Age | Country     | Position    | Jersey No| Salary");

        if (players.isEmpty()) {
            Result.getItems().add("No players found for the given club.");
        } else {
            for (Player player : players) {
                String playerDetails = formatPlayerDetails(player);
                Result.getItems().add(playerDetails);
            }
        }
    }
    public void gotoGuest() throws Exception
    {
        application.gotoGuestfromClubSearch();
    }
    private String formatPlayerDetails(Player player) {
        return String.format("%-20s | %-15s | %-4d | %-10s | %-12s | %-12s | %s",
                player.getName(),
                player.getClub(),
                player.getAge(),
                player.getCountry(),
                player.getPosition(),
                player.getNumber() == -1 ? "Not available" : player.getNumber(),
                BigDecimal.valueOf(player.getSalary()).stripTrailingZeros().toPlainString());
    }
}


