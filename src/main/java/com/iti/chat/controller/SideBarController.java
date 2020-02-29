package com.iti.chat.controller;

import com.iti.chat.delegate.FriendRequestDelegate;
import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.listcell.ContactListCell;
import com.iti.chat.listcell.NotificationListCell;
import com.iti.chat.listcell.RequestContactCell;
import com.iti.chat.model.User;
import com.iti.chat.util.Animator;
import com.iti.chat.util.ImageCache;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SideBarController implements Initializable {

    @FXML
    private VBox sideBarVBox;

    @FXML
    private ImageView profileImageView;

    @FXML
    private ImageView magnifierImageView;

    @FXML
    private ImageView contactsImageView;

    @FXML
    private ImageView signOutImageView;

    @FXML
    private ImageView notificationImageView;

    @FXML
    private ImageView groupChatImageView;

    @FXML
    private Circle userImage;

    private HomeController homeController;

    /**
     * list of all icons that only on of them is not clickable (you can't click two of them)
     */
    private List<ImageView> exclusiveIconsList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Animator.setIconAnimation(signOutImageView);
        Animator.setIconAnimation(magnifierImageView);
        Animator.setIconAnimation(notificationImageView);
        Animator.setIconAnimation(groupChatImageView);

        Animator.suspendIconAnimation(contactsImageView);
        Animator.suspendIconAnimation(profileImageView);

        exclusiveIconsList.add(magnifierImageView);
        exclusiveIconsList.add(contactsImageView);
        exclusiveIconsList.add(notificationImageView);
        exclusiveIconsList.add(groupChatImageView);

        setImage();

        setProfileImageHandler();
        setSignOutHandler();
        setContactsImageHandler();
        setGroupChatRoomHandler();
        setNotificationsHandler();
        setMagnifierImageHandler();

    }

    private void setProfileImageHandler() {
        profileImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            try {

                Animator.suspendIconAnimation(profileImageView);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SceneTransition.class.getResource("/view/UserProfile.fxml"));
                Parent parent = loader.load();
                homeController.setUserProfileController(loader.getController());
                UserInfoDelegate infoDelegate = new UserInfoDelegate(homeController.getClient(), homeController.getUserProfileController());
                homeController.getUserProfileController().setDelegate(infoDelegate);
                homeController.getRightVBox().getChildren().clear();
                homeController.getRightVBox().getChildren().add(parent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setContactsImageHandler() {
        contactsImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            //animate all icons put this
            setAnimations(contactsImageView);

            //put the ListView of type User on the middle list
            homeController.showUserListView();

            //set the header of the friend list
            homeController.getEditableBox().getChildren().clear();
            homeController.getEditableBox().getChildren().add(new Label("List Of friends"));

            //customise the User ListView to host the current logged in user friends
            ObservableList<User> userObservableList = FXCollections.observableList(homeController.getClient().getUser().getFriends());
            homeController.getUserListView().setItems(userObservableList);
            homeController.getUserListView().setCellFactory(userListView -> new ContactListCell(homeController));

        });
    }

    private void setGroupChatRoomHandler() {
        groupChatImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            setAnimations(groupChatImageView);

            homeController.showChatRoomListView();

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SceneTransition.class.getResource("/view/groupChat.fxml"));
                Parent parent = loader.load();
                GroupChatController groupChatController = loader.getController();
                homeController.updateChatRooms(groupChatController);
                groupChatController.setHomeController(homeController);
                groupChatController.setParent(parent);
                groupChatController.setChatRoomListView(homeController.getChatRoomListView());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void setMagnifierImageHandler() {

        magnifierImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            setAnimations(magnifierImageView);
            ObservableList<User> friendRequest = null;

            try {

                friendRequest = FXCollections.observableList(homeController.getClient().getPendingRequests());

            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }

            homeController.showUserListView();

            //set the header of the friend list
            homeController.getEditableBox().getChildren().clear();
            homeController.getEditableBox().getChildren().add(new Label("Friend requests"));

            homeController.getUserListView().setItems(friendRequest);
            homeController.getUserListView().setCellFactory((item) -> new RequestContactCell(homeController));

            ContactsSearchBox contactsSearchBox = new ContactsSearchBox();
            homeController.getEditableBox().getChildren().add(contactsSearchBox);
            contactsSearchBox.setHomeController(homeController);
            FriendRequestDelegate friendRequestDelegate = new FriendRequestDelegate(contactsSearchBox, homeController.getClient());
            contactsSearchBox.setFriendRequestDelegate(friendRequestDelegate);
            homeController.getClient().setFriendRequestDelegate(friendRequestDelegate);

        });
    }

    private void setNotificationsHandler() {
        notificationImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            setAnimations(notificationImageView);
            homeController.showNotificationListView();
            homeController.getEditableBox().getChildren().clear();
            homeController.getEditableBox().getChildren().add(new Label("Notification List"));
            homeController.getNotificationListView().setItems(homeController.getNotificationObservableList());
            System.out.println("Notification" + homeController.getNotificationListView().getItems().size());
            homeController.getNotificationListView().setCellFactory(notificationListView -> new NotificationListCell());
            homeController.getNotificationListView().refresh();
        });
    }

    private void setSignOutHandler() {

        signOutImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            try {

                homeController.getClient().sessionService.logout(homeController.getClient().getUser());
                SceneTransition.goToLoginScreen(homeController.getStage());
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Session.signOutInstance();

        });
    }

    private void setAnimations(ImageView suspendIcon) {
        for (int i = 0; i < exclusiveIconsList.size(); i++) {
            if (exclusiveIconsList.get(i) == suspendIcon) {
                Animator.suspendIconAnimation(exclusiveIconsList.get(i));
            } else {
                Animator.setIconAnimation(exclusiveIconsList.get(i));
            }
        }
    }

    public void setImage() {
        User currentUser = Session.getInstance().getUser();
        Image image = ImageCache.getInstance().getImage(currentUser);
        if (image == null) {
            image = ImageCache.getInstance().getDefaultImage(currentUser);
        }
        userImage.setFill(new ImagePattern(image));
    }

    public VBox getSideBarVBox() {
        return sideBarVBox;
    }

    public void setSideBarVBox(VBox sideBarVBox) {
        this.sideBarVBox = sideBarVBox;
    }

    public ImageView getProfileImageView() {
        return profileImageView;
    }

    public void setProfileImageView(ImageView profileImageView) {
        this.profileImageView = profileImageView;
    }

    public ImageView getMagnifierImageView() {
        return magnifierImageView;
    }

    public void setMagnifierImageView(ImageView magnifierImageView) {
        this.magnifierImageView = magnifierImageView;
    }

    public ImageView getContactsImageView() {
        return contactsImageView;
    }

    public void setContactsImageView(ImageView contactsImageView) {
        this.contactsImageView = contactsImageView;
    }

    public void setNotificationImageView(ImageView notificationImageView) {
        this.notificationImageView = notificationImageView;
    }

    public ImageView getNotificationImageView() {
        return notificationImageView;
    }

    public ImageView getSignOutImageView() {
        return signOutImageView;
    }

    public void setSignOutImageView(ImageView signOutImageView) {
        this.signOutImageView = signOutImageView;
    }

    public Circle getUserimage() {
        return userImage;
    }

    public ImageView getGroupChatImageView() {
        return groupChatImageView;
    }

    public void setGroupChatImageView(ImageView groupChatImageView) {
        this.groupChatImageView = groupChatImageView;
    }

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}