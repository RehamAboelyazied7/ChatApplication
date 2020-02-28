package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.FriendRequestDelegate;
import com.iti.chat.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class AddFriendListController implements Initializable {
    @FXML
    private Circle userImage;

    @FXML
    private Text userName;

    @FXML
    private ImageView plus;

    private User user;
    private FriendRequestDelegate friendRequestDelegate;
    private boolean added = false;

    public void setFriendRequestDelegate(FriendRequestDelegate friendRequestDelegate) {
        this.friendRequestDelegate = friendRequestDelegate;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        plus.setOnMouseClicked((e) -> {
            try {
                friendRequestDelegate.sendFriendRequest(user);
                plus.setDisable(true);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (NotBoundException ex) {
                ex.printStackTrace();
            }
            System.out.println("plus clicked");

        });
    }

    public void setUserData(User item) {
        user = item;
        userName.setText(item.getFirstName() + " " + item.getLastName());
        /*Image image = null;
        if (item.getRemoteImagePath() != "")
            image = new Image(getClass().getResource(item.getRemoteImagePath()).toExternalForm());
        if (image != null)
            userImage.setFill(new ImagePattern(image));*/
    }

}
