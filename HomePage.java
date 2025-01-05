package org.example.javanetworking;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.javanetworking.DTO.BuyReq;
import org.example.javanetworking.DTO.TransferReq;
import org.example.javanetworking.Player.Player;
import org.example.javanetworking.CricketManagementSystem.CricketManagementSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomePage {
    @FXML
    private Label State;
    @FXML private Button SendOffer, ShowMarket, ShowTransfer, showall, bycon, bysal, seachbyname, searchbyposition, ClubSearch, Playersearch, addplayer;
    @FXML private TextField country, maxsal, minsal, name;
    @FXML private Label Username;
    @FXML private TableView<Player> ShowlPlayers;
    @FXML private TableColumn<Player, String> nameColumn, countryColumn, positionColumn;
    @FXML
    private TableColumn<Player, Void> details;
    @FXML
    private TableColumn<Player, Void> status;

    private String club;
    private SocketWrapper socketWrapper;


    private CricketManagementSystem cms = new CricketManagementSystem();
    public Main application;
private List<Player> PlayerList;
    public HomePage() throws Exception {
        cms = new CricketManagementSystem();
        PlayerList = cms.getPlayersByClub(club);
        for(Player p: PlayerList) {
            System.out.println(p);
        }
        for(Player p: cms.getPlayersByClub(club)) {
            System.out.println("CLUB");
            System.out.println(p );
        }
    }

    public void setApplication(Main application) {
        this.application = application;
    }

    public Main getApplication() {
        return application;
    }

    public void setClub(String club) {
        this.club = club;
        Username.setText(club);
    }
    public void setsocket(SocketWrapper socketWrapper) {
        this.socketWrapper = socketWrapper;
    }
    @FXML
    public void initialize() {
        System.out.println("Initialsing homepage..");
        }
    @FXML
    public void showAllPlayers(ActionEvent event) {
        try {
            socketWrapper.write("Show All");
            socketWrapper.flush();
List<Player> clubPlayerList = new ArrayList<>();
            Object receivedObject = socketWrapper.read();
            if (receivedObject instanceof List<?>) {
                List<Player> fullPlayerList = (List<Player>) receivedObject;
                for(Player p: fullPlayerList)
                {
                    if(p.getClub().equalsIgnoreCase(club)) clubPlayerList.add(p);
                }
                resetFilters();
                ShowlPlayers.getItems().clear();
                ObservableList<Player> observablePlayers = FXCollections.observableArrayList(clubPlayerList);

                nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
                countryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCountry()));
                positionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPosition()));
                details.setCellFactory(column -> new TableCell<Player, Void>() {
                    private final Button detailsButton = new Button("Details");

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(detailsButton);
                            detailsButton.setOnAction(e -> {

                                Player player = getTableRow().getItem();
                                if (player != null) {
                                    openPlayerDetailsWindow(player);
                                }
                            });
                        }
                    }
                });
                status.setCellFactory(column -> new TableCell<Player, Void>() {
                    private final Button transferButton = new Button("Transfer");

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(transferButton);
                            transferButton.setOnAction(e -> {
                                // Get the Player from the current row
                                Player player = getTableRow().getItem();
                                if (player != null) {

                                    System.out.println("Transfer initiated for Player: " + player.getName() + ", Club: " + player.getClub());
                                    // Send the player to the server (transfer market)
                                    sendToTransfer(player);
                                }
                            });
                        }
                    }
                });


                State.setText("All Players");
                ShowlPlayers.setItems(observablePlayers);

            } else {
                System.err.println("Invalid data received from the server.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openPlayerDetailsWindow(Player player, String offeredFrom, Double amount) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerDetails.fxml"));
            Parent root = loader.load();
            PlayerDetails controller = loader.getController();

            if (offeredFrom != null && amount != null) {
                controller.initialize(player, offeredFrom, amount);
            } else {
                controller.initialize(player);
            }

            Stage stage = new Stage();
            stage.setTitle("Player Details");
            stage.setScene(new Scene(root, 300, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddPlayer  (ActionEvent event) throws Exception
    {
        application.gotoAddPlayer();
    }


    private void openPlayerDetailsWindow(Player player) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerDetails.fxml"));
            Parent root = loader.load();

            PlayerDetails controller = loader.getController();
            controller.initialize(player);

            Stage stage = new Stage();
            stage.setTitle("Player Details");
            stage.setScene(new Scene(root, 300, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private synchronized void sendToTransfer(Player selectedPlayer) {
        try {
            if (socketWrapper != null && selectedPlayer != null) {
                socketWrapper.write(selectedPlayer);
                socketWrapper.flush();
                System.out.println("Sent player to transfer market: " + selectedPlayer.getName());

                String serverResponse = (String) socketWrapper.read();

                showResponseWindow(serverResponse);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error sending player to transfer market: " + e.getMessage());
        }
    }

    private void showResponseWindow(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Transfer Market Status");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    private void openBidWindow(Player player) {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        TextField amountField = new TextField();
        amountField.setPromptText("Enter Bid Amount");
        Button sendButton = new Button("Send Bid");

        sendButton.setOnAction(event -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                socketWrapper.write(new BuyReq(player, club, amount));
                socketWrapper.flush();
                System.out.println("Bid sent for " + player.getName() + ": " + amount);
                stage.close();
            } catch (Exception e) {
                System.err.println("Invalid bid input");
            }
        });
        vbox.getChildren().addAll(new Text("Enter Bid Amount"), amountField, sendButton);
        vbox.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(vbox, 300, 150));
        stage.setTitle("Bid for Player");
        stage.show();
    }

    @FXML
    public void handleoffer(ActionEvent event) {
        try {
            System.out.println("Inside handle offer");
            socketWrapper.write("SHOW_OFFER");
            socketWrapper.flush();

            Object receivedObject = socketWrapper.read();
            if (receivedObject instanceof List<?>) {
                List<BuyReq> offerList = (List<BuyReq>) receivedObject;
                List<Player> playersWithClubInfo = new ArrayList<>();
                Map<Player, String> playerClubMap = new HashMap<>();
                Map<Player, Double> playerAmountMap = new HashMap<>();
                for (BuyReq offer : offerList) {
                    Player player = offer.getPlayer();
                    if(player.getClub().equalsIgnoreCase(club)) {
                        playersWithClubInfo.add(player);
                        playerClubMap.put(player, offer.getClub());
                        playerAmountMap.put(player, offer.getAmount());
                    }
                }
                for (Player p : playersWithClubInfo) {
                    System.out.println("Player: " + p + ", Club: " + playerClubMap.get(p) + ", Amount: " + playerAmountMap.get(p));
                }
                ShowlPlayers.getItems().clear();
                ObservableList<Player> observablePlayers = FXCollections.observableArrayList(playersWithClubInfo);

                nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
                countryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCountry()));
                positionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPosition()));

                TableColumn<Player, String> offeredFromColumn = new TableColumn<>("Offered From");
                offeredFromColumn.setCellValueFactory(data -> new SimpleStringProperty(playerClubMap.get(data.getValue())));

                TableColumn<Player, Double> amountColumn = new TableColumn<>("Amount");
                amountColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(playerAmountMap.get(data.getValue())));

                ShowlPlayers.getColumns().addAll(offeredFromColumn, amountColumn);

                details.setCellFactory(column -> new TableCell<Player, Void>() {
                    private final Button detailsButton = new Button("Details");

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(detailsButton);
                            detailsButton.setOnAction(e -> {
                                Player player = getTableRow().getItem();
                                if (player != null) {
                                    openPlayerDetailsWindow(player,
                                            playerClubMap.get(player),
                                            playerAmountMap.get(player));
                                }
                            });
                        }
                    }
                });

                status.setCellFactory(column -> new TableCell<Player, Void>() {
                    private final Button confirmButton = new Button("Confirm");

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(confirmButton);
                            confirmButton.setOnAction(e -> {
                                Player player = getTableRow().getItem();
                                if (player != null) {
                                    String offeredClub = playerClubMap.get(player);
                                    System.out.println("Confirmed offer for Player: " + player.getName());
                                    double amount = playerAmountMap.get(player);

                                    TransferReq transferReq = new TransferReq(offeredClub, player);
                                    try {
                                        synchronized (transferReq) {
                                            socketWrapper.write(new TransferReq(offeredClub, player));
                                            socketWrapper.flush();
                                            System.out.println("Transfer request sent for " + player.getName() + " to " + offeredClub);
                                        }

                                        System.out.println("Transfer request sent for Player: " + player.getName() + " to " + offeredClub);

                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    try {
                                        socketWrapper.flush();
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    showConfirmationWindow(player.getName(), offeredClub, amount);
                                    System.out.println("Transfer request sent for Player: " + player.getName() + " to " + offeredClub);

                                }
                            });
                        }
                    }
                });
State.setText("Your Offers");
                ShowlPlayers.setItems(observablePlayers);

            } else {
                System.err.println("Invalid data received from the server.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showConfirmationWindow(String playerName, String offeredClub, double amount) {

        Stage stage = new Stage();
        stage.setTitle("Transfer Confirmation");

        Label confirmationMessage = new Label(
                "Transfer for '" + playerName + "' to '" + offeredClub + "' for $" + amount + " is confirmed.");

        VBox layout = new VBox(10, confirmationMessage);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 600, 100);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    public void handleShowTransfer(ActionEvent event) {
        try {
            socketWrapper.write("SHOW_TRANSFER");
            System.out.println("Request sent to server for transfer market list.");
            Object receivedObject = socketWrapper.read();
            if (receivedObject instanceof List) {
                List<Player> transferMarket = (List<Player>) receivedObject;
                System.out.println("Received transfer market list from server.");
                for(Player p:transferMarket)
                {
                    System.out.println(p);
                }
                List<Player> filteredPlayers = new ArrayList<>();
                for(Player p: transferMarket)
                {
                    if(!p.getClub().equalsIgnoreCase(club)) filteredPlayers.add(p);
                }
                Platform.runLater(() -> {
                    for (Player p: filteredPlayers)
                    {
                        System.out.println("Market Place:");
                        System.out.println(p);
                    }
                    ShowlPlayers.getItems().clear();
                    ObservableList<Player> observablePlayers = FXCollections.observableArrayList(filteredPlayers);

                    nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
                    countryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCountry()));
                    positionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPosition()));

                    details.setCellFactory(column -> new TableCell<Player, Void>() {
                        private final Button detailsButton = new Button("Details");

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(detailsButton);
                                detailsButton.setOnAction(e -> {
                                    Player player = getTableRow().getItem();
                                    if (player != null) {
                                        openPlayerDetailsWindow(player);
                                    }
                                });
                            }
                        }
                    });

                    // Set up bid button
                    status.setCellFactory(column -> new TableCell<Player, Void>() {
                        private final Button bidButton = new Button("Bid");

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(bidButton);
                                bidButton.setOnAction(e -> {
                                    Player player = getTableRow().getItem();
                                    if (player != null) {
                                        openBidWindow(player);
                                    }
                                });
                            }
                        }
                    });
                    State.setText("Transfer Market");
                    ShowlPlayers.setItems(observablePlayers);
                });
            } else {
                System.err.println("Unexpected data received from server.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error fetching transfer market list: " + e.getMessage());
        }
    }

    @FXML
    public void searchByName (ActionEvent event) throws IOException, ClassNotFoundException {
        String searchName = name.getText().trim();
        List<Player> clubPlayerList = new ArrayList<>();
        socketWrapper.write("Show All");
        socketWrapper.flush();

        Object receivedObject = socketWrapper.read();
        List<Player> result = new ArrayList<>();
        if (receivedObject instanceof List<?>) {
            List<Player> fullPlayerList = (List<Player>) receivedObject;
for(Player p: fullPlayerList)
{
    if(p.getClub().equalsIgnoreCase(club)) clubPlayerList.add(p);
}
        }
        for(Player p: clubPlayerList)
        {
            if(p.getName().equalsIgnoreCase(searchName))
            {
                System.out.println(p);
                result.add(p);
            }
        }
        resetFilters();
        ObservableList<Player> observablePlayers = FXCollections.observableArrayList(result);
        ShowlPlayers.getItems().clear();
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        countryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCountry()));
        positionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPosition()));

        details.setCellFactory(column -> new TableCell<Player, Void>() {
            private final Button detailsButton = new Button("Details");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(detailsButton);
                    detailsButton.setOnAction(e -> {

                        Player player = getTableRow().getItem();
                        if (player != null) {
                            openPlayerDetailsWindow(player);
                        }
                    });
                }
            }
        });
        status.setCellFactory(column -> new TableCell<Player, Void>() {
            private final Button transferButton = new Button("Transfer");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(transferButton);
                    transferButton.setOnAction(e -> {
                        // Get the Player from the current row
                        Player player = getTableRow().getItem();
                        if (player != null) {

                            System.out.println("Transfer initiated for Player: " + player.getName() + ", Club: " + player.getClub());
                            sendToTransfer(player);
                        }
                    });
                }
            }
        });

        ShowlPlayers.setItems(observablePlayers);

        }
    @FXML
    public void searchByCountry(ActionEvent event) throws IOException, ClassNotFoundException {

        String searchCountry = country.getText().trim();
        List<Player> clubPlayerList = new ArrayList<>();
        socketWrapper.write("Show All");
        socketWrapper.flush();

        Object receivedObject = socketWrapper.read();
        if (receivedObject instanceof List<?>) {
            List<Player> fullPlayerList = (List<Player>) receivedObject;
            for(Player p: fullPlayerList)
            {
                if(p.getClub().equalsIgnoreCase(club)) clubPlayerList.add(p);
            }
        }
        List<Player> result = new ArrayList<>();
        for(Player p: clubPlayerList)
        {
            if(p.getCountry().equalsIgnoreCase(searchCountry))
            {
               result.add(p);
            }
        }
        resetFilters();
        ObservableList<Player> observablePlayers = FXCollections.observableArrayList(result);
        ShowlPlayers.getItems().clear();
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        countryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCountry()));
        positionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPosition()));

        details.setCellFactory(column -> new TableCell<Player, Void>() {
            private final Button detailsButton = new Button("Details");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(detailsButton);
                    detailsButton.setOnAction(e -> {

                        Player player = getTableRow().getItem();
                        if (player != null) {
                            openPlayerDetailsWindow(player);
                        }
                    });
                }
            }
        });
        status.setCellFactory(column -> new TableCell<Player, Void>() {
            private final Button transferButton = new Button("Transfer");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(transferButton);
                    transferButton.setOnAction(e -> {
                        // Get the Player from the current row
                        Player player = getTableRow().getItem();
                        if (player != null) {

                            System.out.println("Transfer initiated for Player: " + player.getName() + ", Club: " + player.getClub());

                            sendToTransfer(player);
                        }
                    });
                }
            }
        });
        ShowlPlayers.setItems(observablePlayers);
        for(Player p: result)
        {
            System.out.println(p);
        }
        State.setText("Search Result");
    }

    @FXML
    private TextField position;
    @FXML
    public void searchByPosition (ActionEvent event) throws IOException, ClassNotFoundException {
        String searchPosition = position.getText().trim();
        List<Player> clubPlayerList = new ArrayList<>();
        socketWrapper.write("Show All");
        socketWrapper.flush();

        Object receivedObject = socketWrapper.read();
        if (receivedObject instanceof List<?>) {
            List<Player> fullPlayerList = (List<Player>) receivedObject;
            for(Player p: fullPlayerList)
            {
                if(p.getClub().equalsIgnoreCase(club)) clubPlayerList.add(p);
            }
        }
        List<Player> result = new ArrayList<>();
        for(Player p: clubPlayerList)
        {
            if(p.getPosition().equalsIgnoreCase(searchPosition))
            {
                System.out.println(p);
                result.add(p);
            }
        }
        resetFilters();
        ObservableList<Player> observablePlayers = FXCollections.observableArrayList(result);
        ShowlPlayers.getItems().clear();
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        countryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCountry()));
        positionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPosition()));
        details.setCellFactory(column -> new TableCell<Player, Void>() {
            private final Button detailsButton = new Button("Details");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(detailsButton);
                    detailsButton.setOnAction(e -> {

                        Player player = getTableRow().getItem();
                        if (player != null) {
                            openPlayerDetailsWindow(player);
                        }
                    });
                }
            }
        });
        status.setCellFactory(column -> new TableCell<Player, Void>() {
            private final Button transferButton = new Button("Transfer");
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(transferButton);
                    transferButton.setOnAction(e -> {
                        // Get the Player from the current row
                        Player player = getTableRow().getItem();
                        if (player != null) {

                            System.out.println("Transfer initiated for Player: " + player.getName() + ", Club: " + player.getClub());
                            // Send the player to the server (transfer market)
                            sendToTransfer(player);
                        }
                    });
                }
            }
        });
        State.setText("Search Result");
        ShowlPlayers.setItems(observablePlayers);
    }
    private void resetFilters() {
        name.clear();
        country.clear();
        position.clear();
        minsal.clear();
        maxsal.clear();
    }

    @FXML
    public void searchBySalaryRange (ActionEvent event){
        try {
            resetFilters();
            int minSalary = Integer.parseInt(minsal.getText().trim());
            int maxSalary = Integer.parseInt(maxsal.getText().trim());
            List<Player> filteredPlayers = PlayerList.stream()
                    .filter(player -> player.getSalary() >= minSalary && player.getSalary() <= maxSalary)
                    .toList();

            ShowlPlayers.getItems().addAll(filteredPlayers);
            State.setText("Search Result");
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary range entered. Please enter valid numbers.");
        }
    }
    @FXML
    private void handleLogout (ActionEvent event) throws Exception {
        club = null;
        application.gotoLoginPage();
    }
}


