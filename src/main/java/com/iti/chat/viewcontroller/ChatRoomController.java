package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;
import com.iti.chat.service.ClientServiceProvider;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.iti.chat.delegate.ChatRoomDelegate;
import com.iti.chat.model.Message;
import com.iti.chat.model.MessageType;
import com.iti.chat.util.FileTransfer;
import com.iti.chat.util.Session;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatRoomController implements Initializable {

    @FXML
    private VBox messagesVBox;

    @FXML
    private ContactBarController contactBarController;

    @FXML
    public RichTextAreaController richTextAreaController;

    @FXML
    private VBox motherVBox;

    @FXML
    private ScrollPane scrollPane;

    private HomeController homeController;

    private ClientServiceProvider model;

    Stage stage;

    ChatRoomDelegate delegate;

    File savePath;

    ExecutorService executorService;

    int roomId;

    public RichTextAreaController getRichTextAreaController() {
        return richTextAreaController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        scrollPane.vvalueProperty().bind(messagesVBox.heightProperty());
        messagesVBox.maxWidthProperty().bind(scrollPane.widthProperty());

        scrollPane.vvalueProperty().bind(messagesVBox.heightProperty());
        messagesVBox.maxWidthProperty().bind(scrollPane.widthProperty());
        richTextAreaController.getPaperClipButton().setOnAction(ae -> {
            uploadFile(ae);
        });
        richTextAreaController.getSendButton().setOnAction(as -> {
            try {
                delegate.sendMessage(getRichTextAreaController().getMessage(), roomId);
                getRichTextAreaController().clearText();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        });
        messagesVBox.widthProperty().addListener((observableValue, number, t1) -> {
            messagesVBox.requestLayout();
        });
    }

    private void initThreadPool() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(3);
        }

    }

    public void setDelegate(ChatRoomDelegate delegate) {
        this.delegate = delegate;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void receiveFile(RemoteInputStream remoteInputStream) {
        initThreadPool();
        executorService.submit(() -> {
            try {
                FileTransfer.download(savePath.getAbsolutePath(), remoteInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void receiveImage(RemoteInputStream remoteInputStream) {
        try {
            Image image = FileTransfer.downloadImage(remoteInputStream);
            System.out.println("inside receive file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pane createTextMessageNode(Message message) {
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

    public Pane createAttachmentMessageNode(Message message) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/attachment_message.fxml"));
        try {
            Pane pane = loader.load();
            AttachmentMessageController controller = loader.getController();
            controller.downloadButton.setOnAction(ae -> {
                downloadFile(message.getRemotePath(), message.getContent());
            });
            controller.displayMessage(message);
            return pane;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void downloadFile(String remotePath, String fileName) {
        savePath = null;
        FileChooser chooser = new FileChooser();
        chooser.setInitialFileName(fileName);
        savePath = chooser.showSaveDialog(stage);
        if (savePath != null) {
            try {
                delegate.requestFileDownload(remotePath);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void receiveMessage(Message message) {
        if(message.getChatRoom().getId() == roomId) {
            printMessageOnChatRoom(message);
        }
    }

    public void printMessageOnChatRoom(Message message) {

        Platform.runLater(() -> {
            Pane pane;
            if (message.getMessageType() == MessageType.TEXT_MESSAGE) {
                pane = createTextMessageNode(message);
            } else {
                pane = createAttachmentMessageNode(message);
            }
            messagesVBox.getChildren().add(pane);
            pane.maxWidthProperty().bind(getMessagesVBox().widthProperty());
        });

    }

    public void uploadFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
                try {
                    Message message = new Message(selectedFile.getName(), Session.getInstance().getUser(), MessageType.ATTACHMENT_MESSAGE);
                    delegate.sendFile(message, selectedFile, roomId);
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


    public ChatRoom createOrGetChatRoom(List<User> users) {
        try {
            return delegate.createNewChatRoom(users);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void refresh() {
        messagesVBox.getChildren().clear();
        loadChatRoom(getCurrentChatRoom());
    }

    public void loadChatRoom(ChatRoom chatRoom) {
        roomId = chatRoom.getId();
        messagesVBox.getChildren().clear();

        for (int i = 0; i < chatRoom.getMessages().size(); i++) {
            Message msg = chatRoom.getMessages().get(i);
            printMessageOnChatRoom(msg);

        }
    }

    public ChatRoom getCurrentChatRoom() {
        try {
            return delegate.getChatRoom(roomId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }



    public VBox getMessagesVBox() {
        return messagesVBox;
    }

    public void setMessagesVBox(VBox messagesVBox) {
        this.messagesVBox = messagesVBox;
    }

    public ContactBarController getContactBarController() {
        return contactBarController;
    }

    public void setContactBarController(ContactBarController contactBarController) {
        this.contactBarController = contactBarController;
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

    public ClientServiceProvider getModel() {
        return model;
    }

    public void setModel(ClientServiceProvider model) {
        this.model = model;
    }

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}