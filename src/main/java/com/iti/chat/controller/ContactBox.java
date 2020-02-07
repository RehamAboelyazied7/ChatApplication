package com.iti.chat.controller;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class ContactBox extends HBox {
    Label contactName;
    //Unknown Field
    Label contactInfo;
    Image userImg;
    VBox dataBox;

    public ContactBox() {
        Circle circleImage = new Circle(75, 75, 20);
        //userImg = new Image(imagePath);
        //circleImage.setFill(new ImagePattern(userImg));
        circleImage.setStroke(Color.valueOf("#4c84ff"));
        contactName = new Label();
        contactName.setId("contactNameLabel");
        contactInfo = new Label();
        contactInfo.setId("contactInfoLabel");
        dataBox = new VBox(contactName, contactInfo);
        //this.getStylesheets().add("/view/ContactCellStyle.css");
        this.getStylesheets().add(getClass().getResource("/view/ContactCellStyle.css").toExternalForm());
        this.getChildren().addAll(circleImage, dataBox);

    }

    String getName() {
        return contactName.getText();
    }

    void setName(String name) {
        contactName.setText(name);
    }

    String getInfo() {
        return contactInfo.getText();
    }

    void setInfo(String info) {
        contactInfo.setText(info);
    }

    Image getImage() {
        return userImg;
    }
}
