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
//    requires org.controlsfx.controls;
//    requires org.controlsfx.samples;


    //opens view to javafx.fxml;
    opens com.iti.chat.viewcontroller to javafx.fxml;
    exports com.iti.chat.service to java.rmi;
    exports com.iti.chat.viewcontroller;
    exports main;
    exports com.iti.chat.exception;
    exports com.iti.chat.model;
}