package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;
import com.iti.chat.util.Animator;
import com.iti.chat.util.CustomListCell;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;

public class ContactListCell extends CustomListCell {

    private HomeController homeController;

    private ChatRoom room = new ChatRoom();
    private User user;

    public ContactListCell(HomeController homeController) {
        super();
        this.homeController = homeController;
        this.homeController.getEditableBoxLabel().setText("List of friends");
    }

    @Override
    public void setCellAction(MouseEvent mouseEvent, User item) {
        Animator.setIconAnimation(homeController.getSideBarController().getProfileImageView());
        ChatRoomController chatRoomController = SceneTransition.loadChatRoom(homeController.getRightVBox(), room , homeController);
        ChatRoom chatRoom = chatRoomController.createOrGetChatRoom(Arrays.asList(Session.getInstance().getUser(), item));
        chatRoomController.loadChatRoom(chatRoom);
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
