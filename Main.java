package org.example.javanetworking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.javanetworking.CricketManagementSystem.CricketManagementSystem;
import java.io.IOException;
import static javafx.application.Application.launch;
public class Main extends Application {
    Stage stage;
    private Scene previousScene;
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Intro.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 460);

        Intro controller = fxmlLoader.getController();
        controller.setApplication(this);

        stage.setScene(scene);
        stage.show();
    }

    public void gotoguest() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Guest.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Guest controller = fxmlLoader.getController();
        controller.setApplication(this);
        stage.setScene(scene);
        stage.show();
    }
    public void gotologin() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        loginController controller = fxmlLoader.getController();
        controller.setApplication(this);
        stage.setScene(scene);
        stage.show();
    }
    public void gotoHomePage(String username, SocketWrapper socketWrapper) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HomePage homePageController = fxmlLoader.getController();
        homePageController.setApplication(this);
        homePageController.setClub(username);
        homePageController.setsocket(socketWrapper);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoGuestfromPlayerSearch() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Guest.fxml"));  // Correct path for PlayerSearch.fxml
        Scene scene = new Scene(fxmlLoader.load());
        Guest controller = fxmlLoader.getController();
        controller.setApplication(this);
        stage.setScene(scene);
        stage.show();
    }
    public void gotoGuestfromClubSearch() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Guest.fxml"));  // Correct path for PlayerSearch.fxml
        Scene scene = new Scene(fxmlLoader.load());

        Guest controller = fxmlLoader.getController();
        controller.setApplication(this);


        stage.setScene(scene);
        stage.show();
    }
    public void gotoAddPlayer() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddPlayer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        AddPlayer addPlayerController = fxmlLoader.getController();
        addPlayerController.system = new CricketManagementSystem(); // Initialize the system or inject it

        Stage newStage = new Stage();
        newStage.setTitle("Add Player");
        newStage.setScene(scene);

        newStage.initModality(Modality.APPLICATION_MODAL);

        newStage.show();
    }

    public void gotoPlayerSearchfromGuest() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayerSearch.fxml"));  // Correct path for PlayerSearch.fxml
        Scene scene = new Scene(fxmlLoader.load());

        PlayerSearch playerSearchController = fxmlLoader.getController();
        playerSearchController.setApplication(this);

        stage.setScene(scene);
        stage.show();
    }
    public void gotoClubSearchfromGuest() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClubSearch.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        ClubSearch clubSearchController = fxmlLoader.getController();
        clubSearchController.setApplication(this);

        stage.setScene(scene);
        stage.show();
    }

    public void gotoForgotPassword() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ForgotPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        ForgotPassword forgotPasswordController = fxmlLoader.getController();
        forgotPasswordController.setApplication(this);

        stage.setScene(scene);
        stage.show();
    }
    public void gotoLoginPage() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(loader.load());

        loginController controller = loader.getController();
        controller.setApplication(this);

        stage.setScene(scene);
        stage.show();
    }

    public void gotoHallOfFame() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HallOfFame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HallOfFame hallOfFameController = fxmlLoader.getController();
        hallOfFameController.setApplication(this);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoGuest() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Guest.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Guest guestController = fxmlLoader.getController();
        guestController.setApplication(this);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
