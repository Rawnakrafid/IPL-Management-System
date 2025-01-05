package org.example.javanetworking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ForgotPassword {

    @FXML
    private TextField AnswerPW;

    @FXML
    private Button EnterPW;

    @FXML
    private TextField UsernamePW;

    @FXML
    private Label passwordLabel;

    private Main application;

    private Map<String, String> clubToPassword = new HashMap<>();
    private Map<String, String> clubToOldestPlayer = new HashMap<>();

    public void setApplication(Main application) {
        this.application = application;
    }

    public Main getApplication() {
        return application;
    }

    public void initialize() {

        clubToOldestPlayer.put("Kolkata Knight Riders", "Andre Russell");
        clubToOldestPlayer.put("Chennai Super Kings", "MS Dhoni");
        clubToOldestPlayer.put("Royal Challengers Bangalore", "Faf du Plessis");
        clubToOldestPlayer.put("Mumbai Indians", "Rohit Sharma");
        clubToOldestPlayer.put("Rajasthan Royals", "Trent Boult");
        clubToOldestPlayer.put("Gujarat Titans", "Rashid Khan");
        clubToOldestPlayer.put("Delhi Capitals", "David Warner");
        clubToOldestPlayer.put("Lucknow Super Giants", "Marcus Stoinis");

        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/rawnakmubtasimrafid/Desktop/JavaNetworking/src/main/java/org/example/javanetworking/loginhandler.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    clubToPassword.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading passwords: " + e.getMessage());
        }

        EnterPW.setOnAction(event -> handleEnterPW());
    }

    private void handleEnterPW() {
        String username = UsernamePW.getText();
        String answer = AnswerPW.getText();

        if (clubToOldestPlayer.containsKey(username)) {
            String correctAnswer = clubToOldestPlayer.get(username);
            if (correctAnswer.equalsIgnoreCase(answer)) {
                String password = clubToPassword.get(username);
                if (password != null) {
                    passwordLabel.setText(password);
                    System.out.println("Password given");
                } else {
                    System.out.println("Wrong");
                    passwordLabel.setText("Password not found for this username.");
                }
            } else {
                passwordLabel.setText("Incorrect answer.");
            }
        } else {
            passwordLabel.setText("Invalid club name.");
        }
    }
    public void gotologin(ActionEvent event) throws Exception {
        application.gotologin();
    }
}
