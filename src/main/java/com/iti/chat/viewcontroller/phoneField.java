/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.FriendRequestDelegate;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * @author Shaimaa Saied
 */


class phoneField extends AnchorPane {

    private JFXTextField jFXTextField;
    private HBox hBox;
    private ImageView imageView;

    public phoneField() {

        jFXTextField = new JFXTextField();
        hBox = new HBox();
        imageView = new ImageView();

        setId("AnchorPane");
        setPrefHeight(66.0);
        setPrefWidth(259.0);

        jFXTextField.setLayoutX(14.0);
        jFXTextField.setLayoutY(18.0);

        hBox.setLayoutX(183.0);
        hBox.setPrefHeight(66.0);
        hBox.setPrefWidth(30.0);

        imageView.setFitHeight(31.0);
        imageView.setFitWidth(30.0);
        imageView.setLayoutX(213.0);
        imageView.setLayoutY(23.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("../../../../../Downloads/plus%20(1).png").toExternalForm()));

        getChildren().add(jFXTextField);
        getChildren().add(hBox);
        getChildren().add(imageView);

    }
}


