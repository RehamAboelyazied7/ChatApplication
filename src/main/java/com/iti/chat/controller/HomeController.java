package com.iti.chat.controller;

import com.iti.chat.dao.FriendRequestDAO;
import com.iti.chat.dao.UserDAO;
import com.iti.chat.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    ListView<User> listView;

    FriendRequestDAO friendRequestDAO;
    UserDAO userDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<User> userList = FXCollections.observableArrayList();
        ObservableList<ContactBox> data = FXCollections.observableArrayList();
        userList.addAll(new User("Reham", "Mohamed","011" , "Reham@ga" , 1, 1),
                new User("Khaled", "Mohamed","011" , "Reham@ga" , 2, 1));
        listView.setItems(userList);
        listView.setCellFactory(contactBoxListView -> new ContactListCell());

    }

}
