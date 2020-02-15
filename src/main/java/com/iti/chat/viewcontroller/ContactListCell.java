package com.iti.chat.viewcontroller;

import com.iti.chat.model.User;
import com.iti.chat.util.SceneTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ContactListCell extends ListCell<User> {

    public ContactListCell() {
        super();

    }

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            Parent parent = null;
            //load fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/contactListCell.fxml"));
            try {
                parent = loader.load();
                ContactListController contactListController = loader.getController();
                contactListController.setUserData(item);
                // set graphic bel view
                setGraphic(parent);
                setPrefHeight(60);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // set view based on user

        } else {
            setGraphic(null);
        }
    }
}
