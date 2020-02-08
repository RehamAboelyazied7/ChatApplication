package com.iti.chat.controller;

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
    public VBox chatVBox,messageVbox;
    @FXML
    public HBox messageHBox;
    @FXML
    public StackPane ImageStackPane;
    @FXML
    public Circle CircleImageView;
    @FXML
    public Label recieverName,recieverMessage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CircleImageView.setFill(Color.TRANSPARENT);
        Image image = new Image(getClass().getResource("/view/sender.png").toExternalForm());
        CircleImageView.setFill(new ImagePattern(image));
        //chatVBox.setPrefWidth(300);
        //chatVBox.setPrefHeight(200);
        //chatVBox.setMinHeight(100);
        //messageHBox.setHgrow(messageVbox, Priority.ALWAYS);
        //messageHBox.prefHeightProperty().bind(chatVBox.heightProperty().divide(150));
       //messageVbox.prefWidthProperty().bind(chatVBox.widthProperty().divide(150));
        chatVBox.maxWidthProperty().bind(anchorChatPage.widthProperty().multiply(0.8));
        displayOnRight();
        anchorChatPage.requestLayout();
        //Message message=new Message();
        //message.messageDirection(true,messageHBox,ImageStackPane,messageVbox,recieverMessage);

    }

    public void displayOnRight() {
        AnchorPane.clearConstraints(chatVBox);
        AnchorPane.setRightAnchor(chatVBox, 10.0);
        AnchorPane.setTopAnchor(chatVBox, 10.0);
        messageHBox.getChildren().remove(ImageStackPane);
    }
}
