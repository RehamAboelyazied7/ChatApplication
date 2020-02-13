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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ContacListTestController extends ListView<String> implements Initializable {
    @FXML
    private JFXListView<String> contactList;

    public Image userImage;
    ObservableList<String> listView = FXCollections.observableArrayList("test", "reham", "ay7aga");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("/view/userIcon.png");
        System.out.println(file);
        userImage = new Image(file.toURI().toString(), 100, 100, false, false);
        System.out.println("image " + userImage);
        contactList.setItems(listView);
        contactList.setCellFactory(param -> new ListCell<String>() {
            ImageView imageView = new ImageView();

            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    System.out.println("here");
                    setText(null);
                    setGraphic(null);
                } else {
                    System.out.println(userImage);
                    imageView.setImage(userImage);
                    imageView.setFitHeight(20);
                    imageView.setFitWidth(20);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });

    }


}
