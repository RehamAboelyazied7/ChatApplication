package com.iti.chat.viewcontroller;

import com.iti.chat.model.*;
import com.iti.chat.util.ColorUtils;
import com.iti.chat.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatPageController implements Initializable {

    @FXML
    public AnchorPane anchorChatPage;
    @FXML
    public VBox chatVBox, messageVbox;
    @FXML
    public HBox messageHBox;
    @FXML
    public StackPane ImageStackPane;
    @FXML
    public Circle CircleImageView;
    @FXML
    public Label recieverName, recieverMessage;

    public static final String LEFT_MESSAGE_BUBBLE_COLOR = "#4c84ff";
    public static final String RIGHT_MESSAGE_BUBBLE_COLOR = "#DCDCDC";
    public String bubbleColor;
    private ChatRoom currentChatRoom = new ChatRoom();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CircleImageView.setFill(Color.TRANSPARENT);
        Image image = new Image(getClass().getResource("/view/sender.png").toExternalForm());
        CircleImageView.setFill(new ImagePattern(image));
        chatVBox.maxWidthProperty().bind(anchorChatPage.widthProperty().multiply(0.8));
        currentChatRoom.addUser(Session.getInstance().getUser());

    }

    public ChatRoom getCurrentChatRoom() {

        return currentChatRoom;
    }

    public void setCurrentChatRoom(ChatRoom currentChatRoom) {

        this.currentChatRoom = currentChatRoom;
    }


    public void displayOnRight(Message message) {
        bubbleColor = RIGHT_MESSAGE_BUBBLE_COLOR;
        AnchorPane.clearConstraints(chatVBox);
        AnchorPane.setRightAnchor(chatVBox, 10.0);
        AnchorPane.setTopAnchor(chatVBox, 10.0);
        messageHBox.getChildren().remove(ImageStackPane);
        messageVbox.getChildren().remove(recieverName);
        recieverName.setVisible(false);
        if (ColorUtils.areSimilarColors(RIGHT_MESSAGE_BUBBLE_COLOR, message.getStyle().getColor())) {
            bubbleColor = ColorUtils.invertedColor(message.getStyle().getColor());
        }
    }

    public void displayOnLeft(Message message) {
        bubbleColor = LEFT_MESSAGE_BUBBLE_COLOR;
        if (ColorUtils.areSimilarColors(LEFT_MESSAGE_BUBBLE_COLOR, message.getStyle().getColor())) {
            bubbleColor = ColorUtils.invertedColor(message.getStyle().getColor());
        }
    }

    public void displayMessage(Message message) {
        //add code to display msg in chats
        recieverName.setText(message.getSender().getFirstName());
        recieverMessage.setText(message.getContent());
        recieverMessage.setStyle(message.getStyle().toString());
        if (Session.getInstance().getUser().equals(message.getSender())) {
            displayOnRight(message);
            message.setInRightDirection(true);
        } else {
            message.setInRightDirection(false);
            displayOnLeft(message);
        }
        messageVbox.setStyle("-fx-border-color: #ffff;-fx-background-radius:2em;-fx-background-color: " + bubbleColor);
    }

}




