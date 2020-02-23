package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;
import com.iti.chat.util.Animator;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;
import java.util.Arrays;

public class ChatRoomCell extends ListCell<ChatRoom> {
    private GroupChatController groupChatController;
    private ChatRoom chatRoom;

    public ChatRoomCell(GroupChatController groupChatController) {
        super();
        this.groupChatController = groupChatController;
    }

    @Override
    protected void updateItem(ChatRoom item, boolean empty) {
        super.updateItem(item, empty);
        chatRoom = item;
        if (item != null && !empty) {
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/chatRoomCell.fxml"));
            try {
                parent = loader.load();
                ChatRoomListController chatRoomListController = loader.getController();
                chatRoomListController.setChatRoomData(item);
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

            /*if (item != null) {
                Animator.setIconAnimation(homeController.getSideBarController().getProfileImageView());
                ChatRoomController chatRoomController = SceneTransition.loadChatRoom(homeController.getRightVBox(), room, homeController);
                chatRoomController.createOrSetChatRoom(Arrays.asList(Session.getInstance().getUser(), item));

            }*/
        });

    }
}
