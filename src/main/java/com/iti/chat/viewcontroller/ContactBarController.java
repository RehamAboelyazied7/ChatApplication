package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.util.Animator;
//import com.iti.chat.util.XMLUtil;
import com.iti.chat.util.ColorUtils;
import com.iti.chat.util.Session;
import com.iti.chat.util.XMLUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class ContactBarController implements Initializable {


    @FXML
    private HBox motherHBox;

    @FXML
    private Circle userImageCircle;

    @FXML
    private Circle statusCircle;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView saveImageView;

    private ChatRoomController chatRoomController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Animator.setIconAnimation(saveImageView);

        getSaveImageView().setOnMouseClicked(mouseEvent -> {

            FileChooser fileChooser = new FileChooser();

            File file = fileChooser.showSaveDialog(saveImageView.getScene().getWindow());
            file = new File(file.getAbsolutePath() + ".html");

            if (file != null) {

                ChatRoom chatRoom = chatRoomController.getCurrentChatRoom();
                for (Message message : chatRoom.getMessages()) {

                    if (message.getSender().equals(Session.getInstance().getUser())) {

                        message.setInRightDirection(true);
                        if (ColorUtils.areSimilarColors(message.getStyle().getColor(), ChatPageController.RIGHT_MESSAGE_BUBBLE_COLOR)) {

                            message.setBubbleColor(ColorUtils.invertedColor(ChatPageController.RIGHT_MESSAGE_BUBBLE_COLOR));

                        } else {

                            message.setBubbleColor(ChatPageController.RIGHT_MESSAGE_BUBBLE_COLOR);

                        }

                    } else {

                        message.setInRightDirection(false);
                        if (ColorUtils.areSimilarColors(message.getStyle().getColor(), ChatPageController.LEFT_MESSAGE_BUBBLE_COLOR)) {

                            message.setBubbleColor(ColorUtils.invertedColor(ChatPageController.LEFT_MESSAGE_BUBBLE_COLOR));

                        } else {

                            message.setBubbleColor(ChatPageController.LEFT_MESSAGE_BUBBLE_COLOR);

                        }

                    }

                }

                XMLUtil.saveRoomToXML(chatRoom, file);

            }
        });

    }

    public Circle getUserImageCircle() {
        return userImageCircle;
    }

    public void setUserImageCircle(Circle userImageCircle) {
        this.userImageCircle = userImageCircle;
    }

    public Circle getStatusCircle() {
        return statusCircle;
    }

    public void setStatusCircle(Circle statusCircle) {
        this.statusCircle = statusCircle;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public ImageView getSaveImageView() {
        return saveImageView;
    }

    public void setSaveImageView(ImageView saveImageView) {
        this.saveImageView = saveImageView;
    }

    public HBox getMotherHBox() {
        return motherHBox;
    }

    public void setMotherHBox(HBox motherHBox) {
        this.motherHBox = motherHBox;
    }

    public ChatRoomController getChatRoomController() {
        return chatRoomController;
    }

    public void setChatRoomController(ChatRoomController chatRoomController) {
        this.chatRoomController = chatRoomController;
    }
}
