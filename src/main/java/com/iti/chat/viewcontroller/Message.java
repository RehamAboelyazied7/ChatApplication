package com.iti.chat.viewcontroller;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Message {
    public void messageDirection(boolean userMessage,HBox messageHBox, StackPane ImageStackPane, VBox messageVbox, Label recieverMessage) {
        if (userMessage == true) //right
        {

            ImageStackPane.setVisible(false);
            messageHBox.getChildren().remove(ImageStackPane);

            messageHBox.paddingProperty().setValue(new Insets(10,10,10,10));
            messageHBox.nodeOrientationProperty().setValue(NodeOrientation.RIGHT_TO_LEFT);
            recieverMessage.nodeOrientationProperty().setValue(NodeOrientation.LEFT_TO_RIGHT);
            messageVbox.setStyle("-fx-border-color: #ffff;-fx-background-radius:2em;-fx-background-color: #DCDCDC");


        } else {
            ImageStackPane.setVisible(true);

            messageHBox.nodeOrientationProperty().setValue(NodeOrientation.LEFT_TO_RIGHT);
            recieverMessage.nodeOrientationProperty().setValue(NodeOrientation.RIGHT_TO_LEFT);
            //recieverName.nodeOrientationProperty().setValue(NodeOrientation.RIGHT_TO_LEFT);
        }
    }
}
