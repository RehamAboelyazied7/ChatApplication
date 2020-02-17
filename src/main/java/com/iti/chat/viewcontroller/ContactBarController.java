package com.iti.chat.viewcontroller;

import com.iti.chat.util.Animator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactBarController implements Initializable {

    @FXML
    private HBox motherHBox;

    @FXML
    private Circle userImageCircle;

    @FXML
    private Circle statusCircle;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView saveImageView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Animator.setIconAnimation(saveImageView);

    }

    public Circle getUserImageCircle() {
        return userImageCircle;
    }

    public void setUserImageCircle(Circle userImageCircle) {
        this.userImageCircle = userImageCircle;
    }

    public Circle getStatusCircle() {
        return statusCircle;
    }

    public void setStatusCircle(Circle statusCircle) {
        this.statusCircle = statusCircle;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public ImageView getSaveImageView() {
        return saveImageView;
    }

    public void setSaveImageView(ImageView saveImageView) {
        this.saveImageView = saveImageView;
    }

    public HBox getMotherHBox() {
        return motherHBox;
    }

    public void setMotherHBox(HBox motherHBox) {
        this.motherHBox = motherHBox;
    }


}
