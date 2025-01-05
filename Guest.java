package org.example.javanetworking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Guest {

    private Stage stage;

    @FXML private Button HallofFame;
    @FXML private Text ReturnToLogin;
    @FXML private Button SearchFromData;
    @FXML private Button backtologin;

    public Main application = getApplication();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setApplication(Main application) {
        this.application = application;
    }

    public Main getApplication() {
        return application;
    }

    public void gotologin(ActionEvent event) throws Exception {
        if (application == null) {
            System.err.println("Application instance is null in Guest");
            return;
        }
        System.out.println("Navigating to login...");
        application.gotologin();
    }
    @FXML
    public void gotoPlayerSearch(ActionEvent event) throws Exception {
        application.gotoPlayerSearchfromGuest();
    }

    @FXML
    public void gotoClubSearch(ActionEvent event) throws Exception {
        application.gotoClubSearchfromGuest();
    }
    @FXML
    public void gotoHallOfFame(ActionEvent event) throws Exception {
        application.gotoHallOfFame();
    }

}
