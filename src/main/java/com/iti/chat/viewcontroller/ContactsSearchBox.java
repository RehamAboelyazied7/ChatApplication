/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.FriendRequestDelegate;
import com.iti.chat.model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;

import javax.xml.validation.Validator;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shaimaa Saied
 */


public class ContactsSearchBox extends VBox {
    FriendRequestDelegate friendRequestDelegate;
    AnchorPane anchorPane;
    private JFXTextField jFXTextField;
    private HBox hBox;
    private ImageView imageView;
    private GridPane gridPane;
    private JFXButton jfxButton;

    public void setFriendRequestDelegate(FriendRequestDelegate friendRequestDelegate) {
        this.friendRequestDelegate = friendRequestDelegate;
    }

    public ContactsSearchBox() {
        anchorPane = new AnchorPane();
        jFXTextField = new JFXTextField();
        hBox = new HBox();
        imageView = new ImageView();
        gridPane = new GridPane();
        jfxButton = new JFXButton("Add");

        setId("VBox");
        setPrefHeight(132.0);
        setPrefWidth(259.0);

        anchorPane.setPrefHeight(66.0);
        anchorPane.setPrefWidth(259.0);

        gridPane.setPrefHeight(66.0);
        gridPane.setPrefWidth(259.0);
        gridPane.setAlignment(Pos.CENTER);

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
        imageView.setImage(new Image(this.getClass().getResource("/view/plus.png").toExternalForm()));

        jfxButton.setAlignment(Pos.CENTER);

        anchorPane.getChildren().add(jFXTextField);
        anchorPane.getChildren().add(hBox);
        anchorPane.getChildren().add(imageView);

        gridPane.getChildren().add(jfxButton);

        getChildren().add(anchorPane);
        getChildren().add(gridPane);
        jfxButton.setOnMouseClicked((arg0) -> {
            List<User> userslist = null;
            try {
                userslist = friendRequestDelegate.searchByPhone(jFXTextField.getText());
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(userslist);
        });


    }
}


