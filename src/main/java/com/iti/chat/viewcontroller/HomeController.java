package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.model.Notification;
import com.iti.chat.model.User;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.util.Session;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    ScrollPane scrollPane;

    @FXML
    AnchorPane rootAnchorPane;

    @FXML
    ListView<User> listView;

    @FXML
    VBox messagesVBox;
    @FXML
    MessageTextAreaController messageTextAreaController;
    ClientServiceProvider model;
    ChatRoom room;
    Stage stage;


    public void setModel(ClientServiceProvider model) {
        this.model = model;
        room = new ChatRoom();
        room.addUser(Session.getInstance().getUser());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.widthProperty().addListener((observableValue, number, t1) -> {
            scrollPane.requestLayout();
            messagesVBox.requestLayout();
            rootAnchorPane.requestLayout();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setCellFactory(listView -> new ContactListCell());
        for (int i = 0; i < 3; i++) {
            listView.getItems().add(Session.getInstance().getUser());
        }
        messageTextAreaController.sendButton.setOnAction(this::sendMessage);
        scrollPane.vvalueProperty().bind(messagesVBox.heightProperty());
        messagesVBox.maxWidthProperty().bind(scrollPane.widthProperty());
    }

    public void receiveMessage(Message message) {
        Platform.runLater(() -> {
            Pane pane = createMessageNode(message);
            messagesVBox.getChildren().add(pane);
            pane.maxWidthProperty().bind(messagesVBox.widthProperty());
        });
    }


    public void sendMessage(ActionEvent e) {
        Message message = new Message(messageTextAreaController.getMessage(), Session.getInstance().getUser());
        try {
            model.sendMessage(message, room);
            messageTextAreaController.clearText();
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
    }

    public void receiveNotification(Notification notification) {

    }

    public Pane createMessageNode(Message message) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ChatPage.fxml"));
        try {
            Pane pane = loader.load();
            ChatPageController controller = loader.getController();
            controller.displayMessage(message);
            return pane;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
