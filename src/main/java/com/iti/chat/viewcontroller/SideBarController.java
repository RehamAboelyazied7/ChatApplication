package com.iti.chat.viewcontroller;

import com.iti.chat.util.image.ImageTint;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {

    @FXML
    private VBox sideBarVBox;

    @FXML
    private ImageView profileImageView;

    @FXML
    private ImageView magnifierImageView;

    @FXML
    private ImageView contactsImageView;

    @FXML
    private ImageView signOutImageView;

    @FXML
    private ImageView userimage ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


//        ImageTint.setWhiteTint(profileImageView);
//
//        profileImageView.setOnMouseClicked(e -> {
//
//                ImageTint.setWhiteTint(profileImageView);
//
//        });
//
//        magnifierImageView.setOnMouseClicked(e -> {
//
//            ImageTint.setWhiteTint(magnifierImageView);
//            ImageTint.removeTint(contactsImageView);
//
//        });
//
//        contactsImageView.setOnMouseClicked(e -> {
//
//            ImageTint.setWhiteTint(contactsImageView);
//            ImageTint.removeTint(magnifierImageView);
//
//        });

    }

    public VBox getSideBarVBox() {
        return sideBarVBox;
    }

    public void setSideBarVBox(VBox sideBarVBox) {
        this.sideBarVBox = sideBarVBox;
    }

    public ImageView getProfileImageView() {
        return profileImageView;
    }

    public void setProfileImageView(ImageView profileImageView) {
        this.profileImageView = profileImageView;
    }

    public ImageView getMagnifierImageView() {
        return magnifierImageView;
    }

    public void setMagnifierImageView(ImageView magnifierImageView) {
        this.magnifierImageView = magnifierImageView;
    }

    public ImageView getContactsImageView() {
        return contactsImageView;
    }

    public void setContactsImageView(ImageView contactsImageView) {
        this.contactsImageView = contactsImageView;
    }

    public ImageView getSignOutImageView() {
        return signOutImageView;
    }

    public void setSignOutImageView(ImageView signOutImageView) {
        this.signOutImageView = signOutImageView;
    }

    public ImageView getUserimage() {
        return userimage;
    }
}
