package org.example.javanetworking;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HallOfFame {
    private Main application;
    @FXML
    private Button Back;
    public void setApplication(Main application) {
        this.application = application;
    }

    public Main getApplication() {
        return application;
    }

    @FXML
    public void backToGuest() throws Exception {
        application.gotoGuest();
    }

}
