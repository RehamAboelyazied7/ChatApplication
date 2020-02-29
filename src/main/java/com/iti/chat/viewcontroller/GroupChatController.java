package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;
import com.iti.chat.util.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GroupChatController implements Initializable {


    @FXML
    private Circle groupImage;

    @FXML
    private JFXTextField groupName;

    @FXML
    private HBox membersBox;

    @FXML
    private Label nameLabel;

    @FXML
    private JFXButton submitBtn;

    ListView<ChatRoom> chatRoomListView;

    private HomeController homeController;

    private Parent parent;

    List<User> groupMembersList;
    List<GroupMemberBox> groupMembersBoxList;

    public Circle getGroupImage() {
        return groupImage;
    }

    public String getGroupName() {
        return groupName.getText();
    }

    public HBox getMembersBox() {
        return membersBox;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupImage.setFill(new ImagePattern(new Image(getClass().getResource("/view/dialogue.png").toExternalForm())));
        groupMembersList = new ArrayList();
        groupMembersList.add(Session.getInstance().getUser());
        nameLabel.setText(Session.getInstance().getUser().getFirstName());
        groupMembersBoxList = new ArrayList<>();
        membersBox.getChildren().clear();
        groupName.setVisible(false);
        /*groupImage.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Image image = selectImage();
            if (image != null) {
                groupImage.setFill(new ImagePattern(image));
            }
        });*/
    }


    @FXML
    public void submitGroup() {
        ChatRoom room = new ChatRoom();

        ChatRoomController chatRoomController = SceneTransition.loadChatRoom(homeController.getRightVBox(), room, homeController);
        ChatRoom chatRoom = chatRoomController.createOrGetChatRoom(groupMembersList);
        chatRoomController.loadChatRoom(chatRoom);
        //chatRoom.setName(groupName.getText());
        homeController.getListViewBox().getChildren().clear();
        homeController.getListViewBox().getChildren().add(chatRoomListView);
        homeController.updateChatRooms(this);
    }

    public void contactListAction(Event mouseEvent, User user) {
        if (groupMembersList.contains(user)) {
            mouseEvent.consume();
        } else {
            groupMembersList.add(user);
            GroupMemberBox member = new GroupMemberBox(user);
            membersBox.getChildren().add(member);
            member.getRemoveBtn().setOnAction((actionEvent) -> {
                membersBox.getChildren().remove(member);
                groupMembersList.remove(member.getUser());
            });
        }
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
        updateHomeListView();
    }

    private void updateHomeListView() {
        homeController.getEditableBox().getChildren().clear();
        JFXButton createGroupBtn = new JFXButton("Create Group Chat");
        createGroupBtn.setWrapText(true);
        createGroupBtn.setStyle("-fx-font:bold 15px \"Calibri\";-fx-background-color:#4c84ff; -fx-background-radius:8,7,6; -fx-text-alignment:center; -fx-text-fill:#ffffff;");
        homeController.getEditableBox().getChildren().add(createGroupBtn);
        createGroupBtn.setOnAction((event) -> {
            Animator.setIconAnimation(homeController.getSideBarController().getProfileImageView());
            membersBox.getChildren().clear();
            homeController.getUserListView().setCellFactory(listView -> new GroupContactList(this));
            homeController.getUserListView().setItems(FXCollections.observableList(homeController.getClient().getUser().getFriends()));
            homeController.showUserListView();
            loadParent();
        });
    }

    private void loadParent() {
        homeController.getRightVBox().getChildren().clear();
        homeController.getRightVBox().getChildren().add(parent);
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public void setChatRoomListView(ListView<ChatRoom> chatRoomListView) {
        this.chatRoomListView = chatRoomListView;

    }


    /*private Image selectImage() {
        Image image = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        File selectedImage = fileChooser.showOpenDialog(homeController.getStage());
        if (selectedImage != null) {
            image = new Image(selectedImage.toURI().toString());
        }
        return image;

    }*/


}
