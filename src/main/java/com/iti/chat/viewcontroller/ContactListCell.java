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
    private GroupChatController groupChatController;
    private ChatRoom room = new ChatRoom();
    private User user;
    boolean isGroupChatController;

    public ContactListCell(HomeController homeController) {
        super();
        this.homeController = homeController;
        this.homeController.getEditableBoxLabel().setText("List of friends");
        isGroupChatController = false;
    }
    public ContactListCell(GroupChatController groupChatController) {
        super();
        this.groupChatController = groupChatController;
        isGroupChatController = true;
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

            if (item != null ) {
                if(!isGroupChatController){
                    Animator.setIconAnimation(homeController.getSideBarController().getProfileImageView());
                    ChatRoomController chatRoomController = SceneTransition.loadChatRoom(homeController.getRightVBox(), room , homeController);
                    ChatRoom chatRoom = chatRoomController.createOrGetChatRoom(Arrays.asList(Session.getInstance().getUser(), item));
                    chatRoomController.loadChatRoom(chatRoom);
                    homeController.setChatRoomController(chatRoomController);
                }
                else{
                    groupChatController.contactListAction(mouseEvent,item);
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
