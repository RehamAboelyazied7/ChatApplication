package com.iti.chat.viewcontroller;

import com.iti.chat.model.Message;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;


public class MessageListCell extends ListCell<Message> {

    public MessageListCell() {
        super();
    }
    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) { // <== test for null item and empty parameter
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ChatPage.fxml"));
            try {
                Node parent = loader.load();
                ChatPageController controller = loader.getController();
                controller.displayMessage(item);
                setGraphic(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            setGraphic(null);
        }
    }
}
