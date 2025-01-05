package org.example.javanetworking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class loginController {
    private SocketWrapper socketWrapper;
    private static String SERVER_ADDRESS = "127.0.0.1";
    private static  int SERVER_PORT = 12349;
    Stage stage;
    public  Stage getStage()
    {
        return stage;
    }
    public void setStage()
    {
        this.stage=stage;
    }
    @FXML
    private Button EnterClub;
    @FXML
    private Text ForgotPassword;
    @FXML
    private AnchorPane Guest;
    @FXML
    private ToggleButton Guestlogin;
    @FXML
    private PasswordField Password;

    @FXML
    private TextField Username;
    public Main application;

    public Parent root;
    public Scene scene;
    @FXML
private String user;
    public Main getApplication()
    {
        return application;
    }
    public void setApplication(Main application)
    {
        this.application = application;
    }
    @FXML
    private Button pass;
    public void gotoguest(ActionEvent event) throws Exception {
application.gotoguest();
    }
    @FXML
    public void gotoForgotPassword(ActionEvent event) throws Exception {
        application.gotoForgotPassword();
    }
    @FXML
    public void initialize() {
        Username.clear();
        Password.clear();
    }

    @FXML
    public void gotoHomePage(ActionEvent event) throws Exception {
        String username = Username.getText().trim();
        String password = Password.getText().trim();

        if (socketWrapper == null) {
            socketWrapper = new SocketWrapper(SERVER_ADDRESS, SERVER_PORT);

        }
        socketWrapper.write(username + "," + password);
        Object response = socketWrapper.read();
        if (response instanceof String) {
            String serverResponse = (String) response;

            if ("SUCCESS".equalsIgnoreCase(serverResponse)) {
                application.gotoHomePage(username, socketWrapper);
            } else {
                System.out.println("Wrong credentials");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Invalid Credentials");
                alert.setContentText("The username or password you entered is incorrect.");
                alert.showAndWait();
            }
        }
    }
}
