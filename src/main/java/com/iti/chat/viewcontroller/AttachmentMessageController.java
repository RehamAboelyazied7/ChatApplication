package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.ChatRoomDelegate;
import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.model.MessageStyle;
import com.iti.chat.util.ColorUtils;
import com.iti.chat.util.FileReceiver;
import com.iti.chat.util.ImageCache;
import com.iti.chat.util.Session;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class AttachmentMessageController implements Initializable{
    @FXML
    JFXButton downloadButton;

    @FXML
    public Label fileName;

    @FXML
    VBox messageVBox;
    @FXML
    Label userName;
    @FXML
    Circle userImageView;
    @FXML
    GridPane gridPane;
    @FXML
    AnchorPane rootAnchorPane;
    @FXML
    VBox userDetailsVBox;

    ChatRoomDelegate delegate;

    public void setDelegate(ChatRoomDelegate delegate) {
        this.delegate = delegate;
    }

    private Message message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userImageView.setFill(Color.TRANSPARENT);
        Image image = new Image(getClass().getResource("/view/sender.png").toExternalForm());
        userImageView.setFill(new ImagePattern(image));
    }

    public void displayMessage(Message message) {
        this.message = message;
        indentMessage(message);
        message.setStyle(new MessageStyle());
        userName.setText(message.getSender().getFirstName());
        fileName.setText(message.getContent());
        if(Session.getInstance().getUser().equals(message.getSender())) {
            displayOnRight();
            message.setInRightDirection(true);
        }
        else {
            displayOnLeft(message);
            message.setInRightDirection(false);
        }
    }

    public void displayOnLeft(Message message) {
        Image image = ImageCache.getInstance().getImage(message.getSender());
        if(image == null) {
            image = ImageCache.getInstance().getDefaultImage(message.getSender());
        }
        userImageView.setFill(new ImagePattern(image));
        message.setBubbleColor(MessageController.LEFT_MESSAGE_BUBBLE_COLOR);
    }
    public void displayOnRight() {
        AnchorPane.setRightAnchor(gridPane, 10.0);
        AnchorPane.setLeftAnchor(gridPane, null);
        gridPane.getChildren().remove(0);
        messageVBox.setStyle("-fx-border-color: #ffff; -fx-background-radius: 2em; -fx-background-color: #DCDCDC;");
        message.setBubbleColor(MessageController.RIGHT_MESSAGE_BUBBLE_COLOR);
    }

    private void indentMessage(Message message) {
        ChatRoom chatRoom = message.getChatRoom();
        int messageId = chatRoom.getMessages().indexOf(message);
        if(messageId > 0) {
            Message lastMessage = chatRoom.getMessages().get(messageId - 1);
            if(message.getSender().equals(lastMessage.getSender())) {
                gridPane.getChildren().get(0).setVisible(false);
                AnchorPane.setTopAnchor(gridPane, -10.0);
            }
        }
    }
}
