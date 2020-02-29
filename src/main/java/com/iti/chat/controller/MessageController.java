package com.iti.chat.controller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.util.ColorUtils;
import com.iti.chat.util.ImageCache;
import com.iti.chat.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageController implements Initializable {
    @FXML
    VBox messageVBox;
    @FXML
    Label messageContent;
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
    public static final String LEFT_MESSAGE_BUBBLE_COLOR = "#4c84ff";
    public static final String RIGHT_MESSAGE_BUBBLE_COLOR = "#DCDCDC";
    public String bubbleColor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userImageView.setFill(Color.TRANSPARENT);
        Image image = new Image(getClass().getResource("/view/icons/sender.png").toExternalForm());
        userImageView.setFill(new ImagePattern(image));
        messageVBox.maxWidthProperty().bind(rootAnchorPane.widthProperty().multiply(0.8));
    }

    public void displayOnRight(Message message) {
        bubbleColor = RIGHT_MESSAGE_BUBBLE_COLOR;
        AnchorPane.setRightAnchor(gridPane, 10.0);
        AnchorPane.setLeftAnchor(gridPane, null);
        gridPane.getChildren().remove(0);
        if (ColorUtils.areSimilarColors(RIGHT_MESSAGE_BUBBLE_COLOR, message.getStyle().getColor())) {
            bubbleColor = ColorUtils.invertedColor(message.getStyle().getColor());
        }
    }

    public void displayOnLeft(Message message) {
        bubbleColor = LEFT_MESSAGE_BUBBLE_COLOR;
        Image image = ImageCache.getInstance().getImage(message.getSender());
        if(image == null) {
            image = ImageCache.getInstance().getDefaultImage(message.getSender());
        }
        userImageView.setFill(new ImagePattern(image));
        if (ColorUtils.areSimilarColors(LEFT_MESSAGE_BUBBLE_COLOR, message.getStyle().getColor())) {
            bubbleColor = ColorUtils.invertedColor(message.getStyle().getColor());
        }
    }

    public void displayMessage(Message message) {
        //add code to display msg in chats
        userName.setText(message.getSender().getFirstName());
        messageContent.setText(message.getContent());
        messageContent.setStyle(message.getStyle().toString());
        indentMessage(message);
        if (Session.getInstance().getUser().equals(message.getSender())) {
            displayOnRight(message);
            message.setInRightDirection(true);
        } else {
            message.setInRightDirection(false);
            displayOnLeft(message);
        }
        messageVBox.setStyle("-fx-border-color: #ffff;-fx-background-radius:2em;-fx-background-color: " + bubbleColor);
        userImageView.setStroke(Color.web(bubbleColor));
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
