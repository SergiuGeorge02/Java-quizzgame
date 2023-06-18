package com.example.thinquizzer;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnectionlogin {
    public Connection databaseLink;

    public Connection getConnection(){
        String databasename="thinquizzer";
        String databaseuser="root";
        String databasepassword="admin";
        String url="jdbc:mysql://localhost/"+databasename;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink=DriverManager.getConnection(url,databaseuser,databasepassword);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Could not find the JDBC driver");
            System.out.println("May the mysql.jar file is not added to the project");
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("Error: Could not establish a connection to the database");
            System.out.println("Maybe the database server is not turned on or the connection names are not passed correctly");
            System.exit(1);
        }
        return databaseLink;
    }
}
