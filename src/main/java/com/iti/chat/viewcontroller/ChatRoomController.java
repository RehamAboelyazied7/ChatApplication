package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.service.ClientServiceProvider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ChatRoomController implements Initializable {

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

    private ClientServiceProvider model;

    private ChatRoom room;

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

    public ClientServiceProvider getModel() {
        return model;
    }

    public void setModel(ClientServiceProvider model) {
        this.model = model;
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

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        richTextAreaController.getSendButton().setOnAction(ae -> {
            try {
                model.sendMessage(richTextAreaController.getMessage(), room);
                richTextAreaController.clearText();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        });

        scrollPane.vvalueProperty().bind(messagesVBox.heightProperty());
        messagesVBox.maxWidthProperty().bind(scrollPane.widthProperty());

    }
}
