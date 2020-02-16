package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.ChatRoomDelegate;
import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Notification;
import com.iti.chat.model.User;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.util.Animator;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.application.Platform;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private VBox rightVBox;

    @FXML
    //ListView<User> listView;
    ListView<Notification> listView;
    private GridPane motherGridPane;

//    @FXML
//    ListView<User> listView;

    ClientServiceProvider model;
    ChatRoom room;
    Stage stage;
    FileTransferProgressController fileTransferProgressController;

    @FXML
    private ChatRoomController chatRoomController;

    @FXML
    private  SideBarController sideBarController;

    @FXML
    UserProfileController userProfileController;

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

//        listView.setCellFactory(listView -> new ContactListCell());
//        for (int i = 0; i < 3; i++) {
//            listView.getItems().add(Session.getInstance().getUser());
//        }
        listView.setCellFactory(listView -> new NotificationListCell());
        ObservableList<Notification> notificationObservableList= FXCollections.observableArrayList();
        notificationObservableList.add(new Notification(new User("shimaa","monuir","028282882","shhshs",1,"sjsj"),new User("esraa","ali","9373773","333",1,"d"),1));
        notificationObservableList.add(new Notification(new User("esraa","mohamed","028282882","shhshs",1,"sjsj"),new User("esraa","ali","9373773","333",1,"d"),3));
        listView.getItems().addAll(notificationObservableList);

        try {
            model = new ClientServiceProvider();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        listView.setPlaceholder(new Label("No Content In List"));
        listView.setMinWidth(200);



        model.setUser(Session.getInstance().getUser());

        sideBarController.getProfileImageView().setOpacity(0.4);
        sideBarController.getContactsImageView().setOpacity(0.4);

        Animator.setIconAnimation(sideBarController.getMagnifierImageView());
        Animator.setIconAnimation(sideBarController.getSignOutImageView());

//        listView.setCellFactory(listView -> new ContactListCell());
//
//        ObservableList<User> userObservableList = FXCollections.observableList(model.getUser().getFriends());
//        listView.setItems(userObservableList);

//        listView.setCellFactory(listView -> new RequestContactCell(this));
//        for (int i = 0; i < 3; i++) {
//            listView.getItems().add(Session.getInstance().getUser());
//        }




        //richTextAreaController.sendButton.setOnAction(this::uploadFile);


        //sideBarVBox.minHeightProperty().bind(motherGridPane.heightProperty());
        //chatRoomController.getMessagesVBox().minHeightProperty().bind(motherGridPane.heightProperty());
        //sideBarController.getSignOutImageView().setOnMouseClicked(ae -> SceneTransition.loadProfileScene(rightVBox));
        SceneTransition.loadChatRoom(rightVBox);
    }



    public void didSendNBytes(long n) {
        fileTransferProgressController.setCurrentBytes(n);
    }

    public void receiveNotification(Notification notification) {

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
}
