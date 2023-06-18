package com.example.thinquizzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.thinquizzer.UserData.getUsername;

public class StatisticsController {
    @FXML
    private Button back;
    @FXML
    private Button exitButton;
    @FXML
    private Label username;

    @FXML
    private Label timeplayed;

    @FXML
    private Label highestscore;

    @FXML
    public void exitButtonOnAction(ActionEvent e) {
        Stage s = (Stage) exitButton.getScene().getWindow();
        s.close();

        try {

            File file = new File("usernames.txt");
            FileWriter writer = new FileWriter(file, false);

            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    @FXML
    public void handleBackButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize() {

        String username = getUsername();
        this.username.setText(username);

        DatabaseConnectionlogin databaseConnection = new DatabaseConnectionlogin();
        Connection connection = databaseConnection.getConnection();

        String query = "SELECT timesplayed, highestscore FROM history WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String timesPlayedStr = resultSet.getString("timesplayed");
                String highestScoreStr = resultSet.getString("highestscore");

                int timesPlayed = Integer.parseInt(timesPlayedStr);
                int highestScore = Integer.parseInt(highestScoreStr);

                timeplayed.setText(String.valueOf(timesPlayed));
                highestscore.setText(String.valueOf(highestScore));
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


}
