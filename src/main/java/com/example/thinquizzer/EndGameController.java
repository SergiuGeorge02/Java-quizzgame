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
import java.sql.SQLException;
import java.sql.ResultSet;


public class EndGameController {
    @FXML
    private Label scoreLabel;
    @FXML
    private Button backToMenu;
    @FXML
    private Button exitButton;

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
    public void backToMenuButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) backToMenu.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setScore(int score, int lives) {
        if (lives == 0) {
            scoreLabel.setText("Too many mistakes :(\nGame over");
        } else {
            scoreLabel.setText("Great job!\nYour score is: " + score + "/15");

            String username = UserData.getUsername();
            try {
                DatabaseConnectionlogin connection = new DatabaseConnectionlogin();
                Connection databaseLink = connection.getConnection();

                // Check if username exists in the database
                String selectQuery = "SELECT * FROM history WHERE username = ?";
                PreparedStatement selectStatement = databaseLink.prepareStatement(selectQuery);
                selectStatement.setString(1, username);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    String updateQuery = "UPDATE history SET highestscore = ? WHERE username = ?";
                    PreparedStatement updateStatement = databaseLink.prepareStatement(updateQuery);
                    updateStatement.setString(1, String.valueOf(score));
                    updateStatement.setString(2, username);
                    updateStatement.executeUpdate();
                    updateStatement.close();
                } else {
                    String insertQuery = "INSERT INTO history (username, highestscore) VALUES (?, ?)";
                    PreparedStatement insertStatement = databaseLink.prepareStatement(insertQuery);
                    insertStatement.setString(1, username);
                    insertStatement.setString(2, String.valueOf(score));
                    insertStatement.executeUpdate();
                    insertStatement.close();
                }

                resultSet.close();
                selectStatement.close();
                databaseLink.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
