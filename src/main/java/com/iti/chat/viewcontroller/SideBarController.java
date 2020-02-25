package com.iti.chat.viewcontroller;

import com.iti.chat.model.User;
import com.iti.chat.util.ImageCache;
import com.iti.chat.util.Session;
import com.iti.chat.util.image.ImageTint;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

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
    private ImageView notificationImageView;

    @FXML
    private Circle userImage;

    @FXML
    private ImageView chatImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setImage();
    }

    public void setImage() {
        User currentUser = Session.getInstance().getUser();
        Image image = ImageCache.getInstance().getImage(currentUser);
        if(image == null) {
            image = ImageCache.getInstance().getDefaultImage(currentUser);
        }
        userImage.setFill(new ImagePattern(image));
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

    public void setNotificationImageView(ImageView notificationImageView) {
        this.notificationImageView = notificationImageView;
    }

    public ImageView getNotificationImageView() {
        return notificationImageView;
    }

    public ImageView getSignOutImageView() {
        return signOutImageView;
    }

    public void setSignOutImageView(ImageView signOutImageView) {
        this.signOutImageView = signOutImageView;
    }

    public Circle getUserimage() {
        return userImage;
    }

    public ImageView getChatImageView() {
        return chatImageView;
    }

    public void setChatImageView(ImageView chatImageView) {
        this.chatImageView = chatImageView;
    }

}
