package com.iti.chat.viewcontroller;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.iti.chat.delegate.FriendRequestDelegate;
import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.model.*;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.service.SessionService;
import com.iti.chat.util.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeController implements Initializable {

    @FXML
    private VBox rightVBox;

    @FXML
    ListView<User> listView;
    //    ListView<Notification> listView;

    @FXML
    private GridPane motherGridPane;
    // public  ServerServices serverServices;

    @FXML
    private VBox editableBox;

    @FXML
    private Label editableBoxLabel;

    @FXML
    private VBox listViewBox;

    private ListView<ChatRoom> chatRoomListView;

    private List<ImageView> iconsImageView;

    private ClientServiceProvider client;
    ObservableList<Notification> list;
    ListView<Notification> notificationListView = new ListView<>();
    NotificationListController notificationListController = new NotificationListController();

    private ChatRoom room;
    private Stage stage;
    private FileTransferProgressController fileTransferProgressController;

    public int check = 0;
    SessionService sessionService;
    public int changeList = 0;

    @FXML
    private ChatRoomController chatRoomController;

    @FXML
    private SideBarController sideBarController;

    @FXML
    private UserProfileController userProfileController;
    public int click = 0;

    private ObservableList<ChatRoom> chatRooms = null;

    public void setClient(ClientServiceProvider client) {
        this.client = client;
        didSetClient();
    }

    private void didSetClient() {
        UserInfoDelegate infoDelegate = new UserInfoDelegate(client, userProfileController);
        userProfileController.setDelegate(infoDelegate);
        ObservableList<User> userObservableList = FXCollections.observableList(client.getUser().getFriends());
        listView.setItems(userObservableList);
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

    private void setAnimations(ImageView suspendIcon) {
        for (int i = 0; i < iconsImageView.size(); i++) {
            if (iconsImageView.get(i) == suspendIcon) {
                Animator.suspendIconAnimation(iconsImageView.get(i));
            } else {
                Animator.setIconAnimation(iconsImageView.get(i));
            }
        }
    }

    private void setProfileImageHandler() {
        sideBarController.getProfileImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            try {
                setAnimations(sideBarController.getProfileImageView());
                notificationListView.setVisible(false);
                listView.setVisible(true);
                changeList = 1;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SceneTransition.class.getResource("/view/UserProfile.fxml"));
                Parent parent = loader.load();
                userProfileController = loader.getController();
                UserInfoDelegate infoDelegate = new UserInfoDelegate(client, userProfileController);
                userProfileController.setDelegate(infoDelegate);
                rightVBox.getChildren().clear();
                rightVBox.getChildren().add(parent);
                notificationListView.setVisible(false);
                listView.setVisible(true);
                changeList = 1;


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setContactsImageHandler() {
        sideBarController.getContactsImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            setAnimations(sideBarController.getContactsImageView());
            notificationListView.setVisible(false);
            listView.setVisible(true);
            changeList = 1;
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SceneTransition.class.getResource("/view/groupChat.fxml"));
                Parent parent = loader.load();
                GroupChatController groupChatController = loader.getController();
                updateChatRooms(groupChatController);
                groupChatController.setHomeController(this);
                groupChatController.setParent(parent);
                groupChatController.setChatRoomListView(chatRoomListView);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setChatRoomHandler() {
        sideBarController.getChatImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            setAnimations(sideBarController.getChatImageView());
            listView.setCellFactory(listView -> new ContactListCell(this));
            listViewBox.getChildren().clear();
            listViewBox.getChildren().add(listView);
            editableBox.getChildren().clear();
            editableBox.getChildren().add(new Label("list Of friends"));

        });
    }

    private void setMagnifierImageHandler() {


        sideBarController.getMagnifierImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            setAnimations(sideBarController.getMagnifierImageView());

//            ObservableList<User> friendRequest = null;

//            try {
//                List<User> listUser = client.pendingFriendRequests();
//                friendRequest = FXCollections.observableList(listUser);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            } catch (NotBoundException e) {
//                e.printStackTrace();
//            }

            notificationListView.setVisible(false);
            listViewBox.getChildren().clear();
            listView.setVisible(true);

//            listView.setItems(friendRequest);
//            listView.setCellFactory((item)-> new RequestContactCell(this));

            editableBox.getChildren().clear();
            editableBox.getChildren().add(new Label("Add Friends"));
            ContactsSearchBox contactsSearchBox = new ContactsSearchBox();
            editableBox.getChildren().add(contactsSearchBox);
            contactsSearchBox.setHomeController(this);
            FriendRequestDelegate friendRequestDelegate = new FriendRequestDelegate(contactsSearchBox, client);
            contactsSearchBox.setFriendRequestDelegate(friendRequestDelegate);
            client.setFriendRequestDelegate(friendRequestDelegate);
            //listViewBox.getChildren().add();
            changeList = 1;

        });
    }

    private void setNotificationsHandler() {
        sideBarController.getNotificationImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            listView.setVisible(false);
            if (changeList == 1 && click == 1) {
                // notificationListController.getNotifications().size()!=0 ) {
                notificationListView.setVisible(true);
            } else {
                motherGridPane.add(notificationListView, 1, 0);
                changeList = 1;
                click = 1;
            }
            notificationView();

            // ++check;
        });
    }

    private void setSignOutHandler() {
        sideBarController.getSignOutImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            try {

                client.sessionService.logout(client.getUser());
               // client.setUser(null);
                System.out.println("LogOut");
                SceneTransition.goToLoginScreen(stage);
                //serverServices.
               // SceneTransition.check=true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Session.signOutInstance();
            notificationListView.setVisible(false);
            listView.setVisible(true);

        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void acceptFriendRequest(User user) {
        try {
            client.acceptFriendRequest(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setPlaceholder(new Label("No Content In List"));
        listView.setMinWidth(200);
        listView.setCellFactory(listView -> new ContactListCell(this));
        iconsImageView = Arrays.asList(sideBarController.getChatImageView(),
                sideBarController.getProfileImageView(),
                sideBarController.getContactsImageView(),
                sideBarController.getMagnifierImageView(),
                sideBarController.getNotificationImageView(),
                sideBarController.getSignOutImageView());
        setAnimations(sideBarController.getProfileImageView());
        setProfileImageHandler();
        setSignOutHandler();
        setMagnifierImageHandler();
        setNotificationsHandler();
        setChatRoomHandler();
        setContactsImageHandler();
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


    public void notificationView() {
        setAnimations(sideBarController.getNotificationImageView());
        notificationListView.setPlaceholder(new Label("No Content In List"));
        //NotificationListController notificationListController = new NotificationListController();
        list = FXCollections.observableList(notificationListController.addList().getItems());
        //System.out.println("size"+notificationListController.getNotifications().size());
        notificationListView.setCellFactory(new Callback<ListView<Notification>, ListCell<Notification>>() {
            @Override
            public ListCell<Notification> call(ListView<Notification> notificationListView) {
                return new NotificationListCell();
            }
        });
       /* notificationListView.setItems(list);
//        NotificationListController.notifications.clear();
        check++;
        System.out.println("add items"+notificationListController.addList().getItems().size());

        */
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
            listView.setItems(friends);
            listView.refresh();
            if(chatRoomController != null) {
                chatRoomController.refresh();
            }
        });
    }


    private void friendStatusChangeNotificationBehaviour(Notification notification) {

        listView.getItems().filtered(user -> user.getId() == notification.getSource().getId())
                .get(0).setStatus(notification.source.getStatus());

        listView.refresh();

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

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
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

    public void receiveAnnouncment(Message announcment) {
        System.out.println("recieved announcment" + announcment.getContent());
        PushNotification pushNotification = new PushNotification();
        pushNotification.createNotify(announcment, 6);
    }

    public VBox getEditableBox() {
        return editableBox;
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

    public ListView<User> getListView() {
        return listView;
    }

    public VBox getListViewBox() {
        return listViewBox;
    }

    public ListView<ChatRoom> getChatRoomListView() {
        return chatRoomListView;
    }

    public void updateChatRooms(GroupChatController groupChatController) {
        try {
            chatRooms = FXCollections.observableList(client.getGroupChatRooms(client.getUser()));
            chatRoomListView = new ListView<ChatRoom>(chatRooms);
            chatRoomListView.setPlaceholder(new Label("no groups in list"));
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