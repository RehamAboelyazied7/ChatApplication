package com.iti.chat.util;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;
import com.iti.chat.viewcontroller.ContactListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

abstract public class CustomListCell extends ListCell<User> {


    private ChatRoom room = new ChatRoom();
    private User user;

    abstract public void setCellAction(MouseEvent mouseEvent, User item);

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            user = item;
            if (item != null && !empty) {
                Parent parent = null;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SceneTransition.class.getResource("/view/contactListCell.fxml"));
                try {
                    parent = loader.load();
                    ContactListController contactListController = loader.getController();
                    contactListController.setUserData(item);
                    setPrefWidth(200);
                    setGraphic(parent);
                    setPrefHeight(60);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                setGraphic(null);
            }
            setOnMouseClicked(mouseEvent -> {
                setCellAction(mouseEvent, item);
            });
        }

    }


}
