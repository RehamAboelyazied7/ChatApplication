package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.ChatRoomDelegate;
import com.iti.chat.model.Message;
import com.iti.chat.util.ColorUtils;
import com.iti.chat.util.Session;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class AttachmentMessageController implements Initializable {
    @FXML
    JFXButton downloadButton;

    @FXML
    public VBox chatVBox, messageVbox;;

    @FXML
    public Circle CircleImageView;

    @FXML
    public Label recieverName;

    @FXML
    public HBox messageHBox;

    @FXML
    public StackPane ImageStackPane;

    @FXML
    public Label fileName;

    ChatRoomDelegate delegate;

    public void setDelegate(ChatRoomDelegate delegate) {
        this.delegate = delegate;
    }

    private Message message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CircleImageView.setFill(Color.TRANSPARENT);
        Image image = new Image(getClass().getResource("/view/sender.png").toExternalForm());
        CircleImageView.setFill(new ImagePattern(image));
    }

    public void displayMessage(Message message) {
        this.message = message;
        recieverName.setText(message.getSender().getFirstName());
        fileName.setText(message.getContent());
        if(Session.getInstance().getUser().equals(message.getSender())) {
            displayOnRight(message);
        }
    }
    public void displayOnRight(Message message) {
        AnchorPane.clearConstraints(chatVBox);
        AnchorPane.setRightAnchor(chatVBox, 10.0);
        AnchorPane.setTopAnchor(chatVBox, 10.0);
        messageHBox.getChildren().remove(ImageStackPane);
        recieverName.setStyle("-fx-text-fill:#000000");
        messageVbox.setStyle("-fx-border-color: #ffff; -fx-background-radius: 2em; -fx-background-color: #DCDCDC;");
    }
}
