package com.iti.chat.controller;

import com.iti.chat.util.image.ImageTint;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {

    @FXML
    ImageView friendsImageView;

    @FXML
    ImageView searchImageView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        friendsImageView.setOnMouseClicked(e -> {
            ImageTint.setWhiteTint(friendsImageView);
            ImageTint.removeTint(searchImageView);
        });

        searchImageView.setOnMouseClicked(e -> {
            ImageTint.setWhiteTint(searchImageView);
            ImageTint.removeTint(friendsImageView);
        });
    }
}
