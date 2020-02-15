package com.iti.chat.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ChatRoomController {

    @FXML
    private VBox messagesVBox;

    @FXML
    private ContactBarController contactBarController;

    @FXML
    private RichTextAreaController richTextAreaController;

    @FXML
    private VBox motherVBox;

    @FXML
    private ScrollPane scrollPane;

    public ContactBarController getContactBarController() {
        return contactBarController;
    }

    public void setContactBarController(ContactBarController contactBarController) {
        this.contactBarController = contactBarController;
    }

    public RichTextAreaController getRichTextAreaController() {
        return richTextAreaController;
    }

    public void setRichTextAreaController(RichTextAreaController richTextAreaController) {
        this.richTextAreaController = richTextAreaController;
    }

    public VBox getMotherVBox() {
        return motherVBox;
    }

    public void setMotherVBox(VBox motherVBox) {
        this.motherVBox = motherVBox;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public VBox getMessagesVBox() {
        return messagesVBox;
    }

    public void setMessagesVBox(VBox messagesVBox) {
        this.messagesVBox = messagesVBox;
    }
}
