package com.iti.chat.viewcontroller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageTextAreaController implements Initializable {
    @FXML
    ImageView sendImageView;

    @FXML
    JFXTextArea messageTextArea;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String imagePath = getClass().getResource("/view/send-message.png" ).toExternalForm();
        Image image = new Image(imagePath, 50, 50, true, true);
        sendImageView.setImage(image);
    }

    public String getMessage() {

        return messageTextArea.getText();
    }

    public void clearText() {
        messageTextArea.clear();
    }
}
