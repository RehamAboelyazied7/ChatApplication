package com.iti.chat.viewcontroller;

import com.iti.chat.model.Notification;
import com.iti.chat.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;


import java.util.List;

public class NotificationListController  {

    public VBox addList()//List<Notification> notificationList){

    {
        VBox notificationVbox=new VBox(5);
        notificationVbox.setPadding(new Insets(10));
        notificationVbox.setAlignment(Pos.CENTER);
        ObservableList<Notification> notificationObservableList=FXCollections.observableArrayList();
        notificationObservableList.add(new Notification(new User("shimaa","monuir","028282882","shhshs",1,"sjsj"),new User("esraa","ali","9373773","333",1,"d"),1));
        notificationObservableList.add(new Notification(new User("esraa","mohamed","028282882","shhshs",1,"sjsj"),new User("esraa","ali","9373773","333",1,"d"),3));
     /*   for(Notification notifyItem  : notificationList) {

             notificationObservableList.add(notifyItem);

        }
        */
        ListView<Notification> notificationListView = new ListView<Notification>();
        notificationListView.setCellFactory(new Callback<ListView<Notification>, ListCell<Notification>>() {
            @Override
            public ListCell<Notification> call(ListView<Notification> notificationListView) {
                return new NotificationListCell();
            }
        });
        notificationVbox.setStyle("-fx-background: #4c84ff");
       // notificationListView.setStyle("-fx-background: #4c84ff");
        notificationListView.setItems(notificationObservableList);
        notificationVbox.getChildren().addAll(notificationListView);
        return notificationVbox;
    }
}


