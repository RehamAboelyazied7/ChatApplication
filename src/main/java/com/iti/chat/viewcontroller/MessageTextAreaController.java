package com.iti.chat.viewcontroller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageTextAreaController implements Initializable {

    @FXML
    HBox rootHBox;

    Button sendButton;
    @FXML
    JFXTextArea messageTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String imagePath = getClass().getResource("/view/send-message.png").toExternalForm();
        Image image = new Image(imagePath, 50, 50, true, true);
        sendButton = new Button("", new ImageView(image));
        sendButton.setAlignment(Pos.CENTER);
        HBox.setMargin(sendButton, new Insets(0, 20, 0, 0));
        rootHBox.getChildren().add(sendButton);
    }

    public String getMessage() {

        return messageTextArea.getText();
    }

    public void clearText() {
        messageTextArea.clear();
    }
}
