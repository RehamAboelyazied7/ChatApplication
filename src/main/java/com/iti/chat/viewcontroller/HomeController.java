package com.iti.chat.viewcontroller;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.model.*;
import com.iti.chat.service.ClientServiceProvider;
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
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

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
    private GridPane motherGridPane;

//    @FXML
//    ListView<User> listView;

    private ClientServiceProvider model;
    private ChatRoom room;
    private Stage stage;
    private FileTransferProgressController fileTransferProgressController;

    @FXML
    private ChatRoomController chatRoomController;

    @FXML
    private SideBarController sideBarController;

    @FXML
    private UserProfileController userProfileController;



    public void setModel(ClientServiceProvider model) {
        this.model = model;
        UserInfoDelegate infoDelegate = new UserInfoDelegate(model, userProfileController);
        userProfileController.setDelegate(infoDelegate);

        try {
            System.out.println(model.getUser().getRemoteImagePath());
            if(model.getUser().getRemoteImagePath() != null )
            model.requestImageDownload(model.getUser().getRemoteImagePath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

//        ChatRoomDelegate delegate = new ChatRoomDelegate(model, chatRoomController);
//        chatRoomController.setDelegate(delegate);
//        model.setChatRoomDelegate(delegate);
        //room = new ChatRoom();
        //room.addUser(Session.getInstance().getUser());
//        try {
//            model.requestImageDownload(Session.getInstance().getUser().getRemoteImagePath());
//        } catch (IOException | NotBoundException e) {
//            e.printStackTrace();
//        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;

//        stage.widthProperty().addListener((observableValue, number, t1) -> {
//            chatRoomController.getMessagesVBox().requestLayout();
//        });
    }

    public void refresh() {
        System.out.println("inside refresh");
        User currentUser =  Session.getInstance().getUser();
        System.out.println(currentUser.getFriends());
        Platform.runLater(() -> {
            listView.refresh();
            listView.setItems(FXCollections.observableList(currentUser.getFriends()));
        });
    }

    public void acceptFriendRequest(User user) {
        try {
            model.acceptFriendRequest(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void receiveImage(RemoteInputStream remoteInputStream) throws IOException {
        Image image = FileTransfer.downloadImage(remoteInputStream);
        sideBarController.getUserimage().setImage(image);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //not clicked by default
        Animator.setIconAnimation(sideBarController.getMagnifierImageView());
        Animator.setIconAnimation(sideBarController.getSignOutImageView());

        //clicked by default
        Animator.suspendIconAnimation(sideBarController.getProfileImageView());
        Animator.suspendIconAnimation(sideBarController.getContactsImageView());

        sideBarController.getProfileImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            try {

                Animator.suspendIconAnimation(sideBarController.getProfileImageView());
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SceneTransition.class.getResource("/view/UserProfile.fxml"));
                Parent parent = loader.load();
                rightVBox.getChildren().clear();
                rightVBox.getChildren().add(parent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sideBarController.getContactsImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            Animator.suspendIconAnimation(sideBarController.getContactsImageView());
            Animator.setIconAnimation(sideBarController.getMagnifierImageView());
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SceneTransition.class.getResource("/view/groupChat.fxml"));
                Parent parent = loader.load();
                GroupChatController groupChatController = loader.getController();
                groupChatController.setFriendsListView(listView);
                rightVBox.getChildren().clear();
                rightVBox.getChildren().add(parent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sideBarController.getMagnifierImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            Animator.suspendIconAnimation(sideBarController.getMagnifierImageView());
            Animator.setIconAnimation(sideBarController.getContactsImageView());


        });

        sideBarController.getSignOutImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            try {
                SceneTransition.goToLoginScreen(stage);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Session.signOutInstance();

        });


        try {
            model = new ClientServiceProvider();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        listView.setPlaceholder(new Label("No Content In List"));
        listView.setMinWidth(200);

        try {
            model.setUser(Session.getInstance().getUser());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        listView.setCellFactory(listView -> new ContactListCell(this));

        ObservableList<User> userObservableList = FXCollections.observableList(model.getUser().getFriends());
        listView.setItems(userObservableList);

        //model.setUser(Session.getInstance().getUser());

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
        PushNotification pushNotification = new PushNotification();
        pushNotification.initializeNotify(notification);
        System.out.println("recieve Notification");
        if (notification.notificationType == NotificationType.STATUS_UPDATE) {

            friendStatusChangeNotificationBehaviour(notification);

        }

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

    public ClientServiceProvider getModel() {
        return model;
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
//          PushNotification pushNotification=new PushNotification();
//          pushNotification.createNotify(announcment,NotificationType.MESSAGE_RECEIVED);
    }
}

