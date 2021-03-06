package com.iti.chat.listcell;

import com.iti.chat.controller.ChatRoomController;
import com.iti.chat.controller.ChatRoomListController;
import com.iti.chat.controller.GroupChatController;
import com.iti.chat.controller.HomeController;
import com.iti.chat.model.ChatRoom;
import com.iti.chat.util.Animator;
import com.iti.chat.util.SceneTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import java.io.IOException;

public class ChatRoomCell extends ListCell<ChatRoom> {
    private GroupChatController groupChatController;
    private ChatRoom chatRoom;
    private HomeController homeController;

    public ChatRoomCell(GroupChatController groupChatController , HomeController homeController) {
        super();
        this.groupChatController = groupChatController;
        this.homeController = homeController;
    }

    @Override
    protected void updateItem(ChatRoom item, boolean empty) {
        super.updateItem(item, empty);
        //chatRoom = item;
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
            Animator.setIconAnimation(homeController.getSideBarController().getProfileImageView());
            if (item != null) {
                ChatRoomController chatRoomController = SceneTransition.loadChatRoom(homeController.getRightVBox(), item, homeController);
                chatRoomController.loadChatRoom(item);
                homeController.setChatRoomController(chatRoomController);
            }
        });

    }
}
