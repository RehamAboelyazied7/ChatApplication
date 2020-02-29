package com.iti.chat.controller;

import com.iti.chat.delegate.FriendRequestDelegate;
import com.iti.chat.listcell.AddFriendCell;
import com.iti.chat.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class AddFriendListController implements Initializable {

    @FXML
    private HBox motherHBox;

    @FXML
    private Circle userImage;

    @FXML
    private Text userName;

    @FXML
    private ImageView plus;

    private User user;
    private FriendRequestDelegate friendRequestDelegate;
    private AddFriendCell addFriendCell;

    private HomeController homeController;

    public void setAddFriendCell(AddFriendCell addFriendCell) {
        this.addFriendCell = addFriendCell;
    }

    public void setFriendRequestDelegate(FriendRequestDelegate friendRequestDelegate) {
        this.friendRequestDelegate = friendRequestDelegate;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        plus.setOnMouseClicked((e)->{
            try {
                friendRequestDelegate.sendFriendRequest(user);
                homeController.getUserListView().getItems().remove(user);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (NotBoundException ex) {
                ex.printStackTrace();
            }
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

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}
