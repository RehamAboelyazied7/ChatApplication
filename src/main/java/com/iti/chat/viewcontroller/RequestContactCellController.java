package com.iti.chat.viewcontroller;

import com.iti.chat.model.User;
import com.iti.chat.model.UserStatus;
import com.jfoenix.controls.JFXButton;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void acceptFriend() {

    }

    @FXML
    public void rejectFriend() {

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
}
