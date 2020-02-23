package com.iti.chat.viewcontroller;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.model.*;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.service.ServerServices;
import com.iti.chat.service.SessionService;
import com.iti.chat.util.Animator;
import com.iti.chat.util.FileTransfer;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    ListView<ChatRoom> chatRoomListView;

//    @FXML
//    ListView<User> listView;

    private ClientServiceProvider client;
    ObservableList<Notification> list ;
    ListView<Notification> notificationListView=new ListView<>();
    NotificationListController notificationListController=new NotificationListController();

    private ChatRoom room;
    private Stage stage;
    private FileTransferProgressController fileTransferProgressController;
    
    public  int check=0;
    SessionService sessionService;
    public int changeList=0;

    @FXML
    private ChatRoomController chatRoomController;

    @FXML
    private SideBarController sideBarController;

    @FXML
    private UserProfileController userProfileController;
    public int click = 0;

    public void setClient(ClientServiceProvider client) {
        this.client = client;
        didSetClient();
    }

    private void didSetClient() {
        try {
            if(client.getUser().getRemoteImagePath() != null) {
                client.requestImageDownload(client.getUser().getRemoteImagePath());
            }
            UserInfoDelegate infoDelegate = new UserInfoDelegate(client, userProfileController);
            userProfileController.setDelegate(infoDelegate);
            ObservableList<User> userObservableList = FXCollections.observableList(client.getUser().getFriends());
            listView.setItems(userObservableList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void setAnimations() {
        Animator.setIconAnimation(sideBarController.getMagnifierImageView());
        Animator.setIconAnimation(sideBarController.getSignOutImageView());
        Animator.setIconAnimation(sideBarController.getNotificationImageView());

        //clicked by default
        Animator.suspendIconAnimation(sideBarController.getProfileImageView());
        //Animator.suspendIconAnimation(sideBarController.getContactsImageView());
        Animator.setIconAnimation(sideBarController.getContactsImageView());
    }

    private void setProfileImageHandler() {
        sideBarController.getProfileImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            try {

                Animator.suspendIconAnimation(sideBarController.getProfileImageView());
                notificationListView.setVisible(false);
                listView.setVisible(true);
                changeList=1;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SceneTransition.class.getResource("/view/UserProfile.fxml"));
                Parent parent = loader.load();
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

            Animator.suspendIconAnimation(sideBarController.getContactsImageView());
            Animator.setIconAnimation(sideBarController.getMagnifierImageView());
            Animator.setIconAnimation(sideBarController.getProfileImageView());
            Animator.setIconAnimation(sideBarController.getNotificationImageView());
            Animator.setIconAnimation(sideBarController.getChatImageView());
            notificationListView.setVisible(false);
            listView.setVisible(true);
            changeList = 1;
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SceneTransition.class.getResource("/view/groupChat.fxml"));
                Parent parent = loader.load();
                GroupChatController groupChatController = loader.getController();
                chatRoomListView.setCellFactory(listView -> new ChatRoomCell(groupChatController));
                listViewBox.getChildren().clear();
                listViewBox.getChildren().add(chatRoomListView);
                groupChatController.setHomeController(this);
                rightVBox.getChildren().clear();
                rightVBox.getChildren().add(parent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setChatRoomHandler() {
        sideBarController.getChatImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            Animator.suspendIconAnimation(sideBarController.getChatImageView());
            Animator.setIconAnimation(sideBarController.getContactsImageView());
            Animator.setIconAnimation(sideBarController.getProfileImageView());
            Animator.setIconAnimation(sideBarController.getMagnifierImageView());
            Animator.setIconAnimation(sideBarController.getNotificationImageView());
            listView.setCellFactory(listView -> new ContactListCell(this));
            listViewBox.getChildren().clear();
            listViewBox.getChildren().add(listView);
            editableBox.getChildren().clear();
            editableBox.getChildren().add(new Label("list Of friends"));

        });
    }

    private void setMagnifierImageHandler() {


        sideBarController.getMagnifierImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            Animator.suspendIconAnimation(sideBarController.getMagnifierImageView());
            Animator.setIconAnimation(sideBarController.getContactsImageView());
            notificationListView.setVisible(false);
            listView.setVisible(true);
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
                System.out.println("LogOut");
                SceneTransition.goToLoginScreen(stage);
                //serverServices.
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
        chatRoomListView = new ListView<>();
        listView.setPlaceholder(new Label("No Content In List"));
        listView.setMinWidth(200);
        listView.setCellFactory(listView -> new ContactListCell(this));
        setAnimations();
        setProfileImageHandler();
        setSignOutHandler();
        setMagnifierImageHandler();
        setNotificationsHandler();
        setChatRoomHandler();
        setContactsImageHandler();
    }

    public void receiveImage(RemoteInputStream remoteInputStream) throws IOException {
        Image image = FileTransfer.downloadImage(remoteInputStream);
        sideBarController.getUserimage().setImage(image);

    }

    public void notificationView() {
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

    public void uploadFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            fileTransferProgressController = createFileTransfer(selectedFile.length());
            StackPane container = new StackPane();
            chatRoomController.getMessagesVBox().getChildren().add(container);
            fileTransferProgressController.willStartTransfer(container);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
                try {
                    Message message = new Message(selectedFile.getName(), Session.getInstance().getUser(), MessageType.ATTACHMENT_MESSAGE);
                    client.sendFile(message, selectedFile, room);
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
        PushNotification pushNotification = new PushNotification();
        // notificationView();
        pushNotification.initializeNotify(notification);
        System.out.println("recieve Notification");
        if (notification.notificationType == NotificationType.STATUS_UPDATE) {

            friendStatusChangeNotificationBehaviour(notification);

        }

    }

    public void refresh() {
        User currentUser =  Session.getInstance().getUser();
        System.out.println("inside refresh");
        System.out.println(currentUser.getFriends());
        Platform.runLater(() -> {
            listView.refresh();
            listView.setItems(FXCollections.observableList(currentUser.getFriends()));
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
          PushNotification pushNotification=new PushNotification();
          pushNotification.createNotify(announcment,6);
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


}