package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.model.Notification;
import com.iti.chat.model.User;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.util.Session;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML ListView<Message> messagesListView;
    @FXML MessageTextAreaController messageTextAreaController;
    ClientServiceProvider model;
    ChatRoom room;


    public void setModel(ClientServiceProvider model) {
        this.model = model;
        try {
            User khaled = model.login("01228130430", "password");
            Session.getInstance().setUser(khaled);
            room = new ChatRoom();
            room.addUser(khaled);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messagesListView.setCellFactory(messageListView -> new MessageListCell());
        messageTextAreaController.sendImageView.setOnMouseClicked(this::sendMessage);
    }

    public void receiveMessage(Message message) {
        Platform.runLater(() -> {
            messagesListView.getItems().add(message);
        });
    }


    public void sendMessage(MouseEvent e) {
        Message message = new Message(messageTextAreaController.getMessage(), Session.getInstance().getUser());
        try {
            model.sendMessage(message, room);
            messageTextAreaController.clearText();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public void receiveNotification(Notification notification) {

    }

}
