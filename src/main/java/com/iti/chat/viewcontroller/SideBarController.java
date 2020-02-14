package com.iti.chat.viewcontroller;

import com.iti.chat.util.image.ImageTint;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {

    @FXML
    ImageView profileImageView;

    @FXML
    ImageView magnifierImageView;

    @FXML
    ImageView contactsImageView;

    @FXML
    ImageView signOutImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ImageTint.setWhiteTint(profileImageView);

        profileImageView.setOnMouseClicked(e -> {

                ImageTint.setWhiteTint(profileImageView);

        });

        magnifierImageView.setOnMouseClicked(e -> {

            ImageTint.setWhiteTint(magnifierImageView);
            ImageTint.removeTint(contactsImageView);

        });

        contactsImageView.setOnMouseClicked(e -> {

            ImageTint.setWhiteTint(contactsImageView);
            ImageTint.removeTint(magnifierImageView);

        });

    }
}
