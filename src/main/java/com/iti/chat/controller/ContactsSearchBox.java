/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.chat.controller;

import com.iti.chat.delegate.FriendRequestDelegate;
import com.iti.chat.listcell.AddFriendCell;
import com.iti.chat.model.User;
import com.iti.chat.util.Animator;
import com.iti.chat.util.Session;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Shaimaa Saied
 */


public class ContactsSearchBox extends VBox {
    FriendRequestDelegate friendRequestDelegate;
    HomeController homeController;
    private JFXTextField jFXTextField1;
    private JFXTextField jFXTextField2;
    private JFXTextField jFXTextField3;
    private JFXTextField jFXTextField4;
    private GridPane gridPane;
    private JFXButton jfxButton;

    public ContactsSearchBox() {
        jFXTextField1 = new JFXTextField();
        jFXTextField2 = new JFXTextField();
        jFXTextField3 = new JFXTextField();
        jFXTextField4 = new JFXTextField();
        gridPane = new GridPane();
        jfxButton = new JFXButton("Search");

        setSpacing(12);
        VBox.setMargin(this, new Insets(0, 20, 0, 20));
        setId("VBox");
        setPrefHeight(132.0);
        setPrefWidth(259.0);

        gridPane.setPrefHeight(66.0);
        gridPane.setPrefWidth(259.0);
        gridPane.setAlignment(Pos.CENTER);

        jfxButton.setAlignment(Pos.CENTER);
        jfxButton.setStyle("-fx-font:bold 15px \"Calibri\";-fx-background-color:#4c84ff; -fx-background-radius:8,7,6; -fx-text-alignment:center; -fx-text-fill:#ffffff;");

        getChildren().add(jFXTextField1);
        getChildren().add(jFXTextField2);
        getChildren().add(jFXTextField3);
        getChildren().add(jFXTextField4);

        gridPane.getChildren().add(jfxButton);
        getChildren().add(gridPane);
        jfxButton.setOnMouseClicked((arg0) -> {
            Animator.setIconAnimation(homeController.getSideBarController().getProfileImageView());
            boolean invalid = false;
            Set<User> set = new LinkedHashSet<>();
            List<User> userslist = new ArrayList<User>();
            try {
                if (jFXTextField1.getText().matches("[0-9]+") && jFXTextField1.getText().length() <= 11 && jFXTextField1.getText().length() > 0)
                    set.addAll(friendRequestDelegate.searchByPhone(jFXTextField1.getText()));
                else{}
//                    invalid = true;

                if (jFXTextField2.getText().length() <= 11 && jFXTextField2.getText().length() > 0 && invalid == false) {
                    if (jFXTextField2.getText().matches("[0-9]+"))
                        set.addAll(friendRequestDelegate.searchByPhone(jFXTextField2.getText()));
                    else{}
//                        invalid = true;
                }

                if (jFXTextField3.getText().length() <= 11 && jFXTextField3.getText().length() > 0 && invalid == false) {
                    if (jFXTextField3.getText().matches("[0-9]+"))
                        set.addAll(friendRequestDelegate.searchByPhone(jFXTextField3.getText()));
                    else{}
//                        invalid = true;
                }
                if (jFXTextField4.getText().length() <= 11 && jFXTextField4.getText().length() > 0 && invalid == false) {
                    if (jFXTextField4.getText().matches("[0-9]+"))
                        set.addAll(friendRequestDelegate.searchByPhone(jFXTextField4.getText()));
                    else{}
//                        invalid = true;
                }

                List<User> currentUserFriends = Session.getInstance().getUser().getFriends();
                List<User> pendingFriends = friendRequestDelegate.getPendingSentRequestFriends();

                set.removeAll(currentUserFriends);
                System.out.println(currentUserFriends);
                set.removeAll(pendingFriends);
                System.out.println(pendingFriends);
                set.remove(Session.getInstance().getUser());

                for (User user : set)
                    userslist.add(user);

                ObservableList<User> userObservableList = FXCollections.observableList(userslist);
                homeController.getUserListView().setItems(userObservableList);
                if (invalid)
                    homeController.getUserListView().setPlaceholder(new Label("Invalid number/numbers!"));
                else
                    homeController.getUserListView().setPlaceholder(new Label("no users match the numbers"));

                homeController.getUserListView().setCellFactory(listView -> new AddFriendCell(this, homeController));

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

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public FriendRequestDelegate getFriendRequestDelegate() {
        return friendRequestDelegate;
    }

    public void setFriendRequestDelegate(FriendRequestDelegate friendRequestDelegate) {
        this.friendRequestDelegate = friendRequestDelegate;
    }
}


