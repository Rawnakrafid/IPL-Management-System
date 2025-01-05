package org.example.javanetworking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Intro implements Initializable {

    @FXML
    private Button About;

    @FXML
    private Button GoToLogin;

    @FXML
    private Button Skip;

    @FXML
    private MediaView Mediaview;

    private Main application;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File file = new File("/Users/rawnakmubtasimrafid/Desktop/JavaNetworking Where transferlist works/src/main/resources/org/example/javanetworking/video.mp4");

        if (!file.exists()) {
            System.out.println("Video file not found at: " + file.getAbsolutePath());
            return;
        }

        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        Mediaview.setMediaPlayer(mediaPlayer);
        Skip.toFront();
        Skip.setVisible(true);

        About.setVisible(false);
        GoToLogin.setVisible(false);

        mediaPlayer.play();

        mediaPlayer.setOnEndOfMedia(() -> {
            About.setVisible(true);
            GoToLogin.setVisible(true);
            Skip.setVisible(false);
        });

        mediaPlayer.setOnError(() -> {
            System.out.println("Media Player Error: " + mediaPlayer.getError().getMessage());
        });

    }

    @FXML
    public void setGoToLogin() throws Exception {
        System.out.println("Login button clicked");
        if (application != null) {
            application.gotologin();
        }
    }

    public void gotoabout() throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(new Scene(root));

        stage.show();
    }

    @FXML
    public void handleSkip() {
        System.out.println("Skip button clicked");
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        About.setVisible(true);
        GoToLogin.setVisible(true);
        Skip.setVisible(false);
    }

    public void setApplication(Main application) {
        this.application = application;
    }
}
