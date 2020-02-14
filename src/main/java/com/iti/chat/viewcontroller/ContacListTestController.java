package com.iti.chat.viewcontroller;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ContacListTestController extends ListView<String> implements Initializable {
    @FXML
    private JFXListView<String> contactList;

    public Image userImage;
    ObservableList<String> listCell = FXCollections.observableArrayList("test", "reham", "ay7aga");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userImage = new Image("file:/C:/Users/Reham%20Aboelyazied/Desktop/Images/Ball.png");
        System.out.println("image " + userImage);
        contactList.setItems(listCell);
        contactList.setCellFactory(param -> new ListCell<String>() {
            Circle userImageCircle = new Circle(25);

            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    System.out.println("here");
                    setText(null);
                    setGraphic(null);
                } else {
                    System.out.println(userImage);
                    setText(name);
                    userImageCircle.setFill(new ImagePattern(userImage));
                    userImageCircle.setStroke(Color.BLUE);
                    setGraphic(userImageCircle);
                }
            }
        });

    }


}
