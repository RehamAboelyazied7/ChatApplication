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
    private JFXTextField jFXTextField1;
    private JFXTextField jFXTextField2;
    private JFXTextField jFXTextField3;
    private JFXTextField jFXTextField4;
    private GridPane gridPane;
    private JFXButton jfxButton;

    public void setFriendRequestDelegate(FriendRequestDelegate friendRequestDelegate) {
        this.friendRequestDelegate = friendRequestDelegate;
    }

    public ContactsSearchBox() {
        jFXTextField1 = new JFXTextField();
        jFXTextField2 = new JFXTextField();
        jFXTextField3 = new JFXTextField();
        jFXTextField4 = new JFXTextField();
        gridPane = new GridPane();
        jfxButton = new JFXButton("Search");

        setId("VBox");
        setPrefHeight(132.0);
        setPrefWidth(259.0);

        gridPane.setPrefHeight(66.0);
        gridPane.setPrefWidth(259.0);
        gridPane.setAlignment(Pos.CENTER);

        jfxButton.setAlignment(Pos.CENTER);

        getChildren().add(jFXTextField1);
        getChildren().add(jFXTextField2);
        getChildren().add(jFXTextField3);
        getChildren().add(jFXTextField4);

        gridPane.getChildren().add(jfxButton);
        getChildren().add(gridPane);
        jfxButton.setOnMouseClicked((arg0) -> {
            List<User> userslist = null;
            try {
                userslist = friendRequestDelegate.searchByPhone(jFXTextField1.getText());
                userslist.addAll(friendRequestDelegate.searchByPhone(jFXTextField2.getText()));
                userslist.addAll(friendRequestDelegate.searchByPhone(jFXTextField3.getText()));
                userslist.addAll(friendRequestDelegate.searchByPhone(jFXTextField4.getText()));

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


