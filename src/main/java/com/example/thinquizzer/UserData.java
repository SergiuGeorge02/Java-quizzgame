package com.example.thinquizzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserData {
    private static String username;

    public static String getUsername() {
        if (UserData.username != null) {
            return UserData.username;
        } else {
            String lastUsername = null;

            try (BufferedReader reader = new BufferedReader(new FileReader("usernames.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lastUsername = line;
                }
            } catch (IOException e) {
                // Handle the exception as needed
                e.printStackTrace();
            }

            UserData.username = lastUsername != null ? lastUsername.trim() : "";
            return UserData.username;
        }
    }


    public static void setUsername(String username) {
        UserData.username = username;

        // Save username to file
        try (FileWriter writer = new FileWriter("usernames.txt", true)) {
            writer.write(username + System.lineSeparator());
        } catch (IOException e) {
            // Handle the exception as needed
            e.printStackTrace();
        }
    }
}
