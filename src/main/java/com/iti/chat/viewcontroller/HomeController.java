package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.model.*;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.util.*;

import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.healthmarketscience.rmiio.RemoteInputStream;

public class HomeController implements Initializable {

    @FXML
    private VBox rightVBox;

    @FXML
    private ListView<User> userListView = new ListView<>();
    private ListView<ChatRoom> chatRoomListView = new ListView<>();
    private ListView<Notification> notificationListView = new ListView<>();

    @FXML
    private GridPane motherGridPane;

    @FXML
    private VBox editableBox;

    @FXML
    private Label editableBoxLabel;

    @FXML
    private VBox listViewBox;

    @FXML
    private ChatRoomController chatRoomController;

    @FXML
    private SideBarController sideBarController;

    @FXML
    private UserProfileController userProfileController;

    private ClientServiceProvider client;

    private Stage stage;

    private FileTransferProgressController fileTransferProgressController;

    public void showUserListView(){

        listViewBox.getChildren().clear();
        listViewBox.getChildren().add(userListView);

    }

    public void showChatRoomListView(){

        listViewBox.getChildren().clear();
        listViewBox.getChildren().add(chatRoomListView);

    }

    public void showNotificationListView(){

        listViewBox.getChildren().clear();
        listViewBox.getChildren().add(notificationListView);

    }

    public void setClient(ClientServiceProvider client) {
        this.client = client;
        didSetClient();
    }

    private void didSetClient() {
        UserInfoDelegate infoDelegate = new UserInfoDelegate(client, userProfileController);
        userProfileController.setDelegate(infoDelegate);
        ObservableList<User> userObservableList = FXCollections.observableList(client.getUser().getFriends());
        userListView.setItems(userObservableList);
        loadImage();
        loadFriendsImages();

    }

    private void loadFriendsImages() {
        client.getUser().getFriends().stream().filter(user -> user.getRemoteImagePath() != null)
                .forEach(user -> {
                    Image image = ImageCache.getInstance().getImage(user);
                    if (image == null) {
                        try {
                            client.requestImageDownload(user);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (NotBoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void loadImage() {
        if (client.getUser().getRemoteImagePath() != null) {
            Image image = ImageCache.getInstance().getImage(client.getUser());
            if (image == null) {
                try {
                    client.requestImageDownload(client.getUser());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sideBarController.setHomeController(this);
        userListView.setPlaceholder(new Label("No Content In List"));
        chatRoomListView.setPlaceholder(new Label("no groups in list"));
        notificationListView.setPlaceholder(new Label("No Content In List"));
        userListView.setMinWidth(200);
        userListView.setCellFactory(listView -> new ContactListCell(this));

    }

    public void imageChanged() {
        sideBarController.setImage();
        if (userProfileController != null) {
            userProfileController.setImage();
        }
    }

    public void receiveImage(User user, RemoteInputStream remoteInputStream) throws IOException {
        Platform.runLater(() -> {
            Image image = null;
            try {
                image = FileTransfer.downloadImage(remoteInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageCache.getInstance().setImage(user, image);
            if (client.getUser().equals(user)) {
                sideBarController.setImage();
                if (userProfileController != null) {
                    userProfileController.setImage();
                }
            }
            else {
                reloadViews();
            }
        });

    }


    public void didSendNBytes(long n) {
        fileTransferProgressController.setCurrentBytes(n);
    }

    public void receiveNotification(Notification notification) {
        PushNotification pushNotification = new PushNotification();
        // notificationView();
        pushNotification.initializeNotify(notification);
        System.out.println("recieve Notification" + "source " + notification.getSource() + "reciever" + notification.getReceiver());

        if (notification.notificationType == NotificationType.STATUS_UPDATE) {

            friendStatusChangeNotificationBehaviour(notification);

        }

    }

    public void userInfoDidChange(User user) {
        User currentUser = Session.getInstance().getUser();
        if(!user.equals(client.getUser())) {
            reloadViews();
        }
        if (user.getRemoteImagePath() != null) {
            try {
                client.requestImageDownload(user);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }

        System.out.println("inside refresh");
        System.out.println(currentUser.getFriends());

    }

    private void reloadViews() {
        //User currentUser = Session.getInstance().getUser();
        //listView.setItems(FXCollections.observableList(currentUser.getFriends()));
        Platform.runLater(() -> {
            ObservableList<User> friends = FXCollections.observableList(Session.getInstance().getUser().getFriends());
            //userListView.setItems(friends);
            if(userListView.getItems().containsAll(friends)) {
                userListView.setItems(friends);
            }
            userListView.refresh();
            if(chatRoomController != null) {
                chatRoomController.refresh();
            }
        });
    }


    private void friendStatusChangeNotificationBehaviour(Notification notification) {

        userListView.getItems().filtered(user -> user.getId() == notification.getSource().getId())
                .forEach(user -> {user.setStatus(notification.source.getStatus());});

        userListView.refresh();

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

    public VBox getRightVBox() {
        return rightVBox;
    }

    public void setRightVBox(VBox rightVBox) {
        this.rightVBox = rightVBox;
    }

    public UserProfileController getUserProfileController() {
        return userProfileController;
    }

    public void setUserProfileController(UserProfileController userProfileController) {
        this.userProfileController = userProfileController;
    }

    public ClientServiceProvider getClient() {
        return client;
    }

    public Stage getStage() {
        return stage;
    }

    public FileTransferProgressController getFileTransferProgressController() {
        return fileTransferProgressController;
    }

    public void setFileTransferProgressController(FileTransferProgressController fileTransferProgressController) {
        this.fileTransferProgressController = fileTransferProgressController;
    }

    public void setListViewBox(VBox listViewBox) {
        this.listViewBox = listViewBox;
    }

    public void receiveAnnouncment(Message announcment) {
        System.out.println("recieved announcment" + announcment.getContent());
        PushNotification pushNotification = new PushNotification();
        pushNotification.createNotify(announcment, 6);
    }

    public VBox getEditableBox() {
        return editableBox;
    }

    public ListView<Notification> getNotificationListView() {
        return notificationListView;
    }

    public void setNotificationListView(ListView<Notification> notificationListView) {
        this.notificationListView = notificationListView;
    }

    public void setEditableBox(VBox editableBox) {
        this.editableBox = editableBox;
    }

    public Label getEditableBoxLabel() {
        return editableBoxLabel;
    }

    public void setEditableBoxLabel(Label editableBoxLabel) {
        this.editableBoxLabel = editableBoxLabel;
    }

    public ListView<User> getUserListView() {
        return userListView;
    }

    public VBox getListViewBox() {
        return listViewBox;
    }

    public ListView<ChatRoom> getChatRoomListView() {
        return chatRoomListView;
    }

    public void updateChatRooms(GroupChatController groupChatController) {
        try {
            ObservableList<ChatRoom> chatRooms = FXCollections.observableList(client.getGroupChatRooms(client.getUser()));
            chatRoomListView.setItems(chatRooms);
            chatRoomListView.setCellFactory(listView -> new ChatRoomCell(groupChatController, this));
            listViewBox.getChildren().clear();
            listViewBox.getChildren().add(chatRoomListView);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        }
    }
}