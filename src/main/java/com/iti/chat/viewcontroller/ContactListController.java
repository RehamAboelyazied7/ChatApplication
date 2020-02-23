package com.iti.chat.viewcontroller;

import com.iti.chat.model.User;
import com.iti.chat.model.UserStatus;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;


public class ContactListController implements Initializable {

    @FXML
    private Circle userImage;

    @FXML
    private Circle userStatus;

    @FXML
    private Text userName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUserData(User user) {
        userName.setText(user.getFirstName() + " " + user.getLastName());
        int status = user.getStatus();
        if (status == UserStatus.ONLINE) {
            userStatus.setFill(Color.GREEN);
        } else if (status == UserStatus.BUSY) {
            userStatus.setFill(Color.RED);
        } else if(status==UserStatus.OFFLINE){
            userStatus.setFill(Color.GREY);
        }else{
            userStatus.setFill(Color.YELLOW);
        }
    }

}
