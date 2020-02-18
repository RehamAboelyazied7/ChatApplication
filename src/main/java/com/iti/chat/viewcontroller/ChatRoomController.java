package com.iti.chat.viewcontroller;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.iti.chat.delegate.ChatRoomDelegate;
import com.iti.chat.model.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ChatRoomController implements Initializable{

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        scrollPane.vvalueProperty().bind(messagesVBox.heightProperty());
        messagesVBox.maxWidthProperty().bind(scrollPane.widthProperty());

    }

    public void receiveMessage(Message message) {
    }

    public void receiveFile(RemoteInputStream remoteInputStream) {
    }

    public void setDelegate(ChatRoomDelegate delegate) {
    }
}
