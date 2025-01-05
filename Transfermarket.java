package org.example.javanetworking;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.application.Platform;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.javanetworking.Player.Player;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Transfermarket {
private HomePage homePage;
    private Main application;
    private String club;
    private SocketWrapper socketWrapper;

    public void setHomePage(HomePage homePage)
    {
        this.homePage = homePage;
    }

    @FXML
    private TableView<Player> Transfertable;

    @FXML
    private TableColumn<Player, String> name;

    @FXML
    private TableColumn<Player, String> country;

    @FXML
    private TableColumn<Player, Integer> age;

    @FXML
    private TableColumn<Player, Double> height;

    @FXML
    private TableColumn<Player, String> position;

    @FXML
    private TableColumn<Player, Integer> jersey;

    @FXML
    private TableColumn<Player, Integer> salary;

    @FXML
    private TableColumn<Player, Integer> price;

    @FXML
    private TableColumn<Player, Integer> bid;

    @FXML
    private Button refresh;

    public void setClub(String club) {
        this.club = club;
        System.out.println("Club set to: " + club);
    }

private ArrayList<Player> updatedMarket = new ArrayList<>();

    public void setApplication(Main application) {
        this.application = application;
    }

    public Main getApplication() {
        return application;
    }
    public void setupTable() {
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        country.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        age.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());
        height.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getHeight()).asObject());
        position.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPosition()));
        jersey.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumber()).asObject());
        salary.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSalary()).asObject());
        price.setCellValueFactory(cellData -> new SimpleIntegerProperty(0).asObject());
        bid.setCellValueFactory(cellData -> new SimpleIntegerProperty(0).asObject());
    }
    public void initialize() {
        System.out.println("Initializing TransferMarket...");
        setupTable();

        if (socketWrapper != null) {
            System.out.println("Inside socketwrapper not null");
            fetchTransferMarketData();
             }


    }
    public void fetchTransferMarketData() {
        try {
            socketWrapper.write("GET_TRANSFER_LIST");
            socketWrapper.flush();
            Object response = socketWrapper.read();

            if (response instanceof ArrayList<?>) {
                ArrayList<?> transferMarketList = (ArrayList<?>) response;
                ObservableList<Player> playerList = FXCollections.observableArrayList();
                for (Object obj : transferMarketList) {
                    if (obj instanceof Player) {
                        playerList.add((Player) obj);
                    }
                }
                Platform.runLater(() -> Transfertable.setItems(playerList));
            } else {
                System.err.println("Invalid data received.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error fetching transfer market data: " + e.getMessage());
        }
    }
}

