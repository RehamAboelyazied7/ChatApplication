package com.iti.chat.viewcontroller;

import com.iti.chat.model.*;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.util.Animator;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
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

//    @FXML
//    ListView<User> listView;

    private ClientServiceProvider model;
    ObservableList<Notification> list ;
    ListView<Notification> notificationListView=new ListView<>();
    NotificationListController notificationListController=new NotificationListController();
    private ChatRoom room;
    private Stage stage;
    private FileTransferProgressController fileTransferProgressController;
    public  int check=0;
    public int changeList=0;
    @FXML
    private ChatRoomController chatRoomController;

    @FXML
    private SideBarController sideBarController;

    @FXML
    private UserProfileController userProfileController;
    public int click=0;

    public void setModel(ClientServiceProvider model) {
        this.model = model;
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

    public void acceptFriendRequest(User user) {
        try {
            model.acceptFriendRequest(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            model = new ClientServiceProvider();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        listView.setPlaceholder(new Label("No Content In List"));
        listView.setMinWidth(200);

        model.setUser(Session.getInstance().getUser());

        listView.setCellFactory(listView -> new ContactListCell(this));

        ObservableList<User> userObservableList = FXCollections.observableList(model.getUser().getFriends());
        listView.setItems(userObservableList);

        model.setUser(Session.getInstance().getUser());

        //not clicked by default
        Animator.setIconAnimation(sideBarController.getMagnifierImageView());
        Animator.setIconAnimation(sideBarController.getSignOutImageView());
        Animator.setIconAnimation(sideBarController.getNotificationImageView());

        //clicked by default
        Animator.suspendIconAnimation(sideBarController.getProfileImageView());
        //Animator.suspendIconAnimation(sideBarController.getContactsImageView());
        Animator.setIconAnimation(sideBarController.getContactsImageView());

        sideBarController.getProfileImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            try {

                Animator.suspendIconAnimation(sideBarController.getProfileImageView());
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SceneTransition.class.getResource("/view/UserProfile.fxml"));
                Parent parent = loader.load();
                rightVBox.getChildren().clear();
                rightVBox.getChildren().add(parent);
                notificationListView.setVisible(false);
                listView.setVisible(true);
                changeList=1;


            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        /*sideBarController.getContactsImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            Animator.suspendIconAnimation(sideBarController.getContactsImageView());
            Animator.setIconAnimation(sideBarController.getMagnifierImageView());
            notificationListView.setVisible(false);
            listView.setVisible(true);
            changeList=1;

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SceneTransition.class.getResource("/view/groupChat.fxml"));
                Parent parent = loader.load();
                GroupChatController groupChatController = loader.getController();
                //groupChatController.setFriendsListView(listView);
                listView.setCellFactory(listView -> new ContactListCell(groupChatController));
                rightVBox.getChildren().clear();
                rightVBox.getChildren().add(parent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/




        sideBarController.getMagnifierImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            Animator.suspendIconAnimation(sideBarController.getMagnifierImageView());
            Animator.setIconAnimation(sideBarController.getContactsImageView());
            notificationListView.setVisible(false);
            listView.setVisible(true);
            changeList=1;



        });


        sideBarController.getNotificationImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            listView.setVisible(false);
            if(changeList==1 && click==1){
            // notificationListController.getNotifications().size()!=0 ) {
                notificationListView.setVisible(true);
            }else{
                motherGridPane.add(notificationListView, 1, 0);
                changeList=1;
                click=1;
            }
            notificationView();

           // ++check;
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
            notificationListView.setVisible(false);
            listView.setVisible(true);

        });


    }
    public void notificationView(){
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
        // notificationView();
        pushNotification.initializeNotify(notification);
        System.out.println("recieve Notification");
        if (notification.notificationType == NotificationType.STATUS_UPDATE) {

            friendStatusChangeNotificationBehaviour(notification);

        }

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

    public void receiveAnnouncment(String announcment) {
        PushNotification pushNotification=new PushNotification();
        pushNotification.createNotify(announcment,NotificationType.MESSAGE_RECEIVED);
        /*Platform.runLater(new Runnable() {
            @Override
            public void run() {
                notificationView();

            }
        });

         */
    }

}