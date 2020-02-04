module com.jets {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires java.naming;
    requires com.jfoenix;

    //opens view to javafx.fxml;
    opens com.iti.chat.controller to javafx.fxml;
    exports com.iti.chat.dbservice;
    exports com.iti.chat.controller;
    exports main;
    exports com.iti.chat.exception;
    exports com.iti.chat.model;
}