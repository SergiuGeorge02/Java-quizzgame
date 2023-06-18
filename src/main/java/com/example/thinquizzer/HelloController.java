package com.example.thinquizzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    private String username;
    @FXML
    private Button information;
    @FXML
    private Button play;
    @FXML
    private Button exitButton;
    @FXML
    private Button statistics;

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
     public void handleInformationButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("information.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handlePlayButtonClicked(ActionEvent event) throws IOException {

        String username = UserData.getUsername();


        boolean usernameExists = checkIfUsernameExists(username);

        if (usernameExists) {
            updateTimesPlayed(username);
        } else {
            addNewUser(username);
        }

        Parent gameLoadParent = FXMLLoader.load(getClass().getResource("gameload.fxml"));
        Scene gameLoadScene = new Scene(gameLoadParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(gameLoadScene);
        window.show();
    }


    public boolean checkIfUsernameExists(String username) {
        DatabaseConnectionlogin connection = new DatabaseConnectionlogin();
        try (Connection databaseLink = connection.getConnection()) {
            String query = "SELECT COUNT(*) FROM history WHERE username = ?";
            PreparedStatement statement = databaseLink.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void updateTimesPlayed(String username) {
        DatabaseConnectionlogin connection = new DatabaseConnectionlogin();
        try (Connection databaseLink = connection.getConnection()) {
            String query = "UPDATE history SET timesplayed = timesplayed + 1 WHERE username = ?";
            PreparedStatement statement = databaseLink.prepareStatement(query);
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void addNewUser(String username) {
        DatabaseConnectionlogin connection = new DatabaseConnectionlogin();
        try (Connection databaseLink = connection.getConnection()) {
            String query = "INSERT INTO history (username, timesplayed, highestscore) VALUES (?, ?, ?)";
            PreparedStatement statement = databaseLink.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, "1");
            statement.setString(3, "0");
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void handleStatisticsButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("statistics.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



}