package com.iti.chat.viewcontroller;

import com.iti.chat.model.*;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.util.Session;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeController implements Initializable {

    @FXML
    private GridPane motherGridPane;

    @FXML
    ListView<User> listView;

    ClientServiceProvider model;
    ChatRoom room;
    Stage stage;
    FileTransferProgressController fileTransferProgressController;

    @FXML
    private ChatRoomController chatRoomController;

    @FXML
    private  SideBarController sideBarController;

    public void setModel(ClientServiceProvider model) {
        this.model = model;
        room = new ChatRoom();
        room.addUser(Session.getInstance().getUser());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.widthProperty().addListener((observableValue, number, t1) -> {
            chatRoomController.getMessagesVBox().requestLayout();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setCellFactory(listView -> new ContactListCell());
        for (int i = 0; i < 3; i++) {
            listView.getItems().add(Session.getInstance().getUser());
        }
        chatRoomController.getRichTextAreaController().getSendButton().setOnAction(ae -> {
            try {
                model.sendMessage(chatRoomController.getRichTextAreaController().getMessage(), room);
                chatRoomController.getRichTextAreaController().clearText();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        });
        //richTextAreaController.sendButton.setOnAction(this::uploadFile);
        chatRoomController.getScrollPane().vvalueProperty().bind(chatRoomController.getMessagesVBox().heightProperty());
        chatRoomController.getMessagesVBox().maxWidthProperty().bind(chatRoomController.getScrollPane().widthProperty());
//        sideBarAnchorPane.minHeightProperty().bind(motherGridPane.heightProperty());
    }

    public void receiveMessage(Message message) {
        Platform.runLater(() -> {
            Pane pane = createMessageNode(message);
            chatRoomController.getMessagesVBox().getChildren().add(pane);
            pane.maxWidthProperty().bind(chatRoomController.getMessagesVBox().widthProperty());
        });
    }

    public void uploadFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null) {
            fileTransferProgressController = createFileTransfer(selectedFile.length());
            StackPane container = new StackPane();
            chatRoomController.getMessagesVBox().getChildren().add(container);
            fileTransferProgressController.willStartTransfer(container);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
                try {
                    Message message = new Message(selectedFile.getName(), Session.getInstance().getUser(), MessageType.ATTACHMENT_MESSAGE);
                    model.sendFile(message, selectedFile, room);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void didSendNBytes(long n) {
        fileTransferProgressController.setCurrentBytes(n);
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

    public FileTransferProgressController createFileTransfer(long bytes) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/file_transfer_progress.fxml"));
        try {
            loader.load();
            FileTransferProgressController controller = loader.getController();
            controller.setTotalBytes(bytes);
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GridPane getMotherGridPane() {
        return motherGridPane;
    }

    public void setMotherGridPane(GridPane motherGridPane) {
        this.motherGridPane = motherGridPane;
    }

    public ChatRoomController getChatRoomController() {
        return chatRoomController;
    }

    public void setChatRoomController(ChatRoomController chatRoomController) {
        this.chatRoomController = chatRoomController;
    }

    public SideBarController getSideBarController() {
        return sideBarController;
    }

    public void setSideBarController(SideBarController sideBarController) {
        this.sideBarController = sideBarController;
    }
}
