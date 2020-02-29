package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;
import com.iti.chat.util.Animator;
import com.iti.chat.util.GroupMemberBox;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
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

    public JFXTextField getGroupName() {
        return groupName;
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
    }


    @FXML
    public void submitGroup() {
        ChatRoom room = new ChatRoom();
        ChatRoomController chatRoomController = SceneTransition.loadChatRoom(homeController.getRightVBox(), room, homeController);
        ChatRoom chatRoom = chatRoomController.createOrGetChatRoom(groupMembersList);
        chatRoomController.loadChatRoom(chatRoom);
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
            homeController.getListViewBox().getChildren().clear();
            homeController.getListViewBox().getChildren().add(homeController.getUserListView());
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
}
