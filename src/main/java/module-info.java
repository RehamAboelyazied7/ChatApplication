module com.jets {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires java.naming;
    requires com.jfoenix;
    requires java.rmi;
    requires javafx.web;
    requires chatter.bot.api;
    requires rmiio;
    requires org.controlsfx.controls;
    requires jakarta.activation;
    requires java.xml.bind;

    //opens fxml to javafx.fxml;
    opens com.iti.chat.controller to javafx.fxml;

    //opens model for JAXB API
    opens com.iti.chat.model to java.xml.bind;

    exports com.iti.chat.service to java.rmi;
    exports com.iti.chat.controller;
    exports main;
    exports com.iti.chat.exception;
    exports com.iti.chat.model;
}