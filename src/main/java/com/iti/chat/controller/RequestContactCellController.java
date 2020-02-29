package com.iti.chat.controller;

import com.iti.chat.delegate.PendingFriendRequestCellDelegate;
import com.iti.chat.model.User;
import com.iti.chat.model.UserStatus;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestContactCellController implements Initializable {
    @FXML
    private JFXButton acceptBtn;

    @FXML
    private JFXButton rejectBtn;

    @FXML
    private Circle userImage;

    @FXML
    private Circle userStatus;

    @FXML
    private Text userName;

    private User user;

    private ObservableList<User> containerList;

    private PendingFriendRequestCellDelegate pendingFriendRequestCellDelegate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void acceptFriend(ActionEvent event) {

        pendingFriendRequestCellDelegate.acceptFriendRequest(user);
        containerList.remove(user);

    }

    @FXML
    public void rejectFriend(ActionEvent event) {

        pendingFriendRequestCellDelegate.rejectFriendRequest(user);
        containerList.remove(user);

    }

    public void setUserData(User user) {
        userName.setText(user.getFirstName() + " " + user.getLastName());
        int status = user.getStatus();
        if (status == UserStatus.ONLINE) {
            userStatus.setFill(Color.GREEN);
        } else if (status == UserStatus.BUSY) {
            userStatus.setFill(Color.RED);
        } else {
            userStatus.setFill(Color.GREY);
        }
        //set user  image
        //userImage.setFill(new ImagePattern(user.getImage()));
    }

    /**
     * fxml current user data in the corresponding GUI node
     */
    public void setUserData() {

        if (user != null) {

            setUserData(user);

        }else{

            System.out.println("You are trying to set null user data in the " +
                    "RequestContactCellController into the GUI nodes");

        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PendingFriendRequestCellDelegate getPendingFriendRequestCellDelegate() {
        return pendingFriendRequestCellDelegate;
    }

    public void setPendingFriendRequestCellDelegate(PendingFriendRequestCellDelegate pendingFriendRequestCellDelegate) {
        this.pendingFriendRequestCellDelegate = pendingFriendRequestCellDelegate;
    }

    public ObservableList<User> getContainerList() {
        return containerList;
    }

    public void setContainerList(ObservableList<User> containerList) {
        this.containerList = containerList;
    }
}
