package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;
import com.iti.chat.util.Animator;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Arrays;

public class ContactListCell extends ListCell<User> {

    private HomeController homeController;
    private ChatRoom room = new ChatRoom();
    private User user;

    public ContactListCell(HomeController homeController) {
        super();
        this.homeController = homeController;
    }

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        user = item;
        if (item != null && !empty) {
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/contactListCell.fxml"));
            try {
                parent = loader.load();
                ContactListController contactListController = loader.getController();
                contactListController.setUserData(item);
                setGraphic(parent);
                setPrefHeight(60);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            setGraphic(null);
        }
        setOnMouseClicked(mouseEvent -> {

            if (item != null) {

                Animator.setIconAnimation(homeController.getSideBarController().getProfileImageView());
                ChatRoomController chatRoomController = SceneTransition.loadChatRoom(homeController.getRightVBox(), room , homeController);
                chatRoomController.createOrSetChatRoom(Arrays.asList(Session.getInstance().getUser(), item));

            }

        });

    }

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
