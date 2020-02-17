package com.iti.chat.viewcontroller;

import com.iti.chat.model.User;
import com.iti.chat.util.SceneTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class RequestContactCell extends ListCell<User> {
    public RequestContactCell(HomeController homeController) {
        super();

    }

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            Parent parent = null;
            //load fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/RequestContactCell.fxml"));
            try {
                parent = loader.load();
                RequestContactCellController requestContactCellController = loader.getController();
                requestContactCellController.setUserData(item);

                setGraphic(parent);
                setPrefHeight(60);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            setGraphic(null);
        }
    }
}
