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

public class ContactListCell extends ListCell<User> {

    private HomeController homeController;
    private ChatRoom room = new ChatRoom();
    private User user;

//    public ContactListCell() {
//        super();
//
//    }

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

                try {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SceneTransition.class.getResource("/view/Chat_Room.fxml"));
                    Parent parent = loader.load();
                    ChatRoomController chatRoomController = loader.getController();
                    homeController.setChatRoomController(chatRoomController);
                    chatRoomController.setModel(homeController.getModel());
                    chatRoomController.setRoom(homeController.getRoom());
                    chatRoomController.getContactBarController().getNameLabel().setText(item.getFirstName() + " " + item.getLastName());
                    VBox.setVgrow(parent , Priority.ALWAYS);
                    room.addUser(Session.getInstance().getUser());
                    room.addUser(item);
                    homeController.getRightVBox().getChildren().clear();
                    homeController.getRightVBox().getChildren().add(parent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
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
