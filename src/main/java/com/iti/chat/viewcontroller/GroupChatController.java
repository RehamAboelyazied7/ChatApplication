package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;
import com.iti.chat.util.Animator;
import com.iti.chat.util.GroupMemberBox;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

    private ListView friendsListView;

    private HomeController homeController;

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
    }

    private ChatRoom room = new ChatRoom();

    @FXML
    public void submitGroup() {

        System.out.println(groupMembersList);
        ChatRoomController chatRoomController = SceneTransition.loadChatRoom(homeController.getRightVBox(), room, homeController);
        ChatRoom chatRoom = chatRoomController.createOrGetChatRoom(groupMembersList);
        chatRoomController.loadChatRoom(chatRoom);
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

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {

        this.homeController = homeController;
        homeController.getEditableBox().getChildren().clear();
        JFXButton createGroupBtn = new JFXButton("create Group chat");
        homeController.getEditableBox().getChildren().add(createGroupBtn);
        createGroupBtn.setOnAction((event) -> {
            homeController.getListView().setCellFactory(listView -> new ContactListCell(this));
            homeController.getListViewBox().getChildren().clear();
            homeController.getListViewBox().getChildren().add(homeController.getListView());
        });


    }
}
