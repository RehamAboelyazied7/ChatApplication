package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatRoomListController implements Initializable {
    @FXML
    private Circle groupChatCircle;

    @FXML
    private Label groupChatName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setChatRoomData(String item) {
        groupChatCircle.setFill(new ImagePattern(new Image(getClass().getResource("/view/dialogue.png").toExternalForm())));
        groupChatName.setText(item);
    }
}
