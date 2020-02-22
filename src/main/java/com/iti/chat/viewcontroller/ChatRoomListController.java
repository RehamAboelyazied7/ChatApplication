package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    public void setChatRoomData(ChatRoom item) {
        groupChatName.setText(item.getName());
    }
}
