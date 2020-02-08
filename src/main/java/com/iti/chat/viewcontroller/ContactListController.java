package com.iti.chat.viewcontroller;

import com.iti.chat.model.User;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;


public class ContactListController {
    void addListOfContacts(ObservableList<User> contactList) {
        /*ObservableList<User> userList = FXCollections.observableArrayList();
        ObservableList<ContactBox> data = FXCollections.observableArrayList();
        userList.addAll(new User("Reham", "Mohamed","011" , "Reham@ga" , 1),
                new User("Khaled", "Mohamed","011" , "Reham@ga" , 2));*/
        final ListView<User> listView = new ListView<User>(contactList);
        listView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> contactBoxListView) {
                return new ContactListCell();
            }
        });
    }

}
