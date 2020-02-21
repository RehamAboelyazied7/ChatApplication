package com.iti.chat.util;

import com.iti.chat.model.User;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class GroupMemberBox extends VBox {
    private Circle memberCircle;
    private Label memberLabelName;


    private User user;

    private JFXButton removeBtn;

    public GroupMemberBox(User user) {
        super();
        this.user = user;
        memberCircle = new Circle();
        memberLabelName = new Label(user.getFirstName());
        removeBtn = new JFXButton("Remove");
        memberCircle.setFill(new ImagePattern(new Image(getClass().getResource("/view/icons/Male.png").toExternalForm())));
        memberCircle.setRadius(32);
        this.setPrefHeight(90);
        this.setPrefWidth(90);
        this.setSpacing(6);
        this.setPadding(new Insets(6, 0, 0, 0));
        this.setAlignment(Pos.CENTER);
        //this.setId("member" + memberIndex);
        /*removeBtn.setOnAction((actionEvent) -> {
            removeBtnAction();
        });*/
        this.getChildren().addAll(memberCircle, memberLabelName, removeBtn);
    }

    public JFXButton getRemoveBtn() {
        return removeBtn;
    }

    public User getUser() {
        return user;
    }

   /* private void removeBtnAction() {
        HBox box = (HBox) this.getParent();
        box.getChildren().remove(this);
    }*/

}
