package com.iti.chat.viewcontroller;

import com.iti.chat.model.User;
import com.iti.chat.util.GroupMemberBox;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
    private JFXButton submitBtn;

    private ListView friendsListView;

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
        groupImage.setFill(new ImagePattern(new Image(getClass().getResource("/view/icons/group.png").toExternalForm())));
        groupMembersList = new ArrayList();
        groupMembersBoxList = new ArrayList<>();
    }

    @FXML
    public void submitGroup() {

            System.out.println(groupMembersList);
        }

    public void setFriendsListView(ListView friendsListView) {
        this.friendsListView = friendsListView;
        this.friendsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                User user = null;
                if (friendsListView.getSelectionModel().getSelectedItem() instanceof User)
                    user = (User) friendsListView.getSelectionModel().getSelectedItem();
                if (groupMembersList.contains(user)) {
                    event.consume();
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
            //System.out.println(groupMembersList);
        });
    }

}
