module com.jets {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires java.naming;

    opens com.jets to javafx.fxml;
    exports com.chat.db;
    exports com.jets;
}