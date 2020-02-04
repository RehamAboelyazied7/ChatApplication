package com.iti.chat.controller;

import com.iti.chat.model.User;
import javafx.scene.control.ListCell;

public class ContactListCell extends ListCell<User> {
    private ContactBox contactCell;

    public ContactListCell() {
        super();
        contactCell = new ContactBox();
    }

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) { // <== test for null item and empty parameter
            contactCell.setName(item.getFirstName());
            contactCell.setInfo(item.getStatus());
            //friendImg = new Image("test.png");
            setGraphic(contactCell);
        } else {
            setGraphic(null);
        }
    }
}
