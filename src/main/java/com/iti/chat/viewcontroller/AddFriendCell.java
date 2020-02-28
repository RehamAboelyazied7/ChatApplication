package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;
import com.iti.chat.util.SceneTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class AddFriendCell extends ListCell<User> {
    private ContactsSearchBox contactsSearchBox;
    private User user;
    private HomeController homeController;

    public AddFriendCell(ContactsSearchBox contactsSearchBox, HomeController homeController) {
        super();
        this.contactsSearchBox = contactsSearchBox;
        this.homeController = homeController;
    }

    @Override
    protected void updateItem(User item, boolean empty) {

        super.updateItem(item, empty);
        user = item;
        if (item != null && !empty) {
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/AddFriendCell.fxml"));
            try {
                parent = loader.load();
                AddFriendListController addFriendListController = loader.getController();
                addFriendListController.setUserData(item);
                addFriendListController.setFriendRequestDelegate(contactsSearchBox.getFriendRequestDelegate());
                //setPrefWidth(200);
                setGraphic(parent);
                setPrefHeight(60);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            setGraphic(null);
        }
    }
}
