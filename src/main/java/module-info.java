module com.example.thinquizzer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;



    opens com.example.thinquizzer to javafx.fxml;
    exports com.example.thinquizzer;
}