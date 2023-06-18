package com.example.thinquizzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    String name;
    String pass;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private Button register;

    @FXML
    private Label credentialswrong;

    @FXML
    private Button exitButton;

    public void submit(ActionEvent event) throws SQLException, IOException {
        int ok = 1;
        this.name = username.getText();
        storeUsernameToFile(name);
        this.pass = password.getText();
        DatabaseConnectionlogin connect = new DatabaseConnectionlogin();
        Connection connectdb = connect.getConnection();
        Statement statement = null;
        String verify = "SELECT count(1) FROM users WHERE username='" + name + "' and password='" + pass + "'";
        try {
            statement = connectdb.createStatement();
            ResultSet queryResult = statement.executeQuery(verify);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    ok = 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (ok == 1) {
            credentialswrong.setText("Invalid credentials for login");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) login.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }



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
    public void handleregisterButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public String getName(){
        return this.name;
    }
    private void storeUsernameToFile(String username) {
        try {

            String filePath = "usernames.txt";
            FileWriter writer = new FileWriter(filePath, true);

            // Write the username to the file
            writer.write(username + "\n");

            // Close the writer
            writer.close();

            System.out.println("Username stored successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
