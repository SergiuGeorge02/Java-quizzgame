package com.example.thinquizzer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class GameloadController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label loadingLabel;

    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            try {
                Scene completeQuestionsScene = new Scene(FXMLLoader.load(getClass().getResource("completequestions.fxml")));
                Stage primaryStage = (Stage) rootPane.getScene().getWindow();
                primaryStage.setScene(completeQuestionsScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        timeline.play();
    }
}

