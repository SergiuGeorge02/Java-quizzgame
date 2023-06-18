package com.example.thinquizzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.Properties;
public class RegisterController {
    private DatabaseConnectionlogin databaseConnection;
    @FXML
    private Button exitButton;

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;
    @FXML
    private Label message;
    public RegisterController() {

        databaseConnection = new DatabaseConnectionlogin();
    }

    @FXML
    private void registerButtonOnAction(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();
        String emailText = email.getText();

        if (user.isEmpty() || pass.isEmpty() || emailText.isEmpty()) {
            showMessage("Please fill in all fields");
            return;
        }

        if (!emailText.contains("@")) {
            showMessage("Invalid email format");
            return;
        }

        try (Connection connection = databaseConnection.getConnection()) {
            // Check if the user already exists
            String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, user);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            resultSet.close();
            checkStatement.close();

            if (count > 0) {
                showMessage("Username already exists");
                return;
            }

            // Perform the registration
            String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, user);
            statement.setString(2, pass);
            statement.executeUpdate();
            statement.close();

            showMessage("Registration successful");

            // Send email
            //sendEmail(user, emailText);

            // Load login.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Registration failed");
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Failed to load login page");
        }
    }


    private void showMessage(String mess) {

        message.setText(mess);
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
//    private void sendEmail(String username, String email) {
//        final String senderEmail = "thinquizzergame@gmail.com";
//        final String senderPassword = "GameQuizzer1234";
//
//        String recipientEmail = email;
//        String subject = "Registration Confirmation";
//        String content = "Dear " + username + ",\n\n"
//                + "Thank you for registering with our application. Your account has been successfully created. Please don't forget the password because you won't be able to reset it.";
//
//        // Set up mail server properties
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        // Create a mail session with authentication
//        Session session = Session.getInstance(props, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(senderEmail, senderPassword);
//            }
//        });
//
//        try {
//            // Construct the email message
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(senderEmail));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
//            message.setSubject(subject);
//            message.setText(content);
//
//            // Send the email
//            Transport.send(message);
//
//            System.out.println("Email sent successfully!");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            System.out.println("Failed to send email");
//        }
//    }
}
