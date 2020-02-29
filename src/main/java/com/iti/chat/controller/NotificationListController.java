package com.iti.chat.controller;

import com.iti.chat.listcell.NotificationListCell;
import com.iti.chat.model.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class NotificationListController  {


    public static   List<Notification>notifications;
    public NotificationListController(){
         notifications=new ArrayList<>();
    }
    ListView<Notification> notificationListView = new ListView<Notification>();
   // ObservableList<Notification> notificationObservableList;
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Notification notification) {
        notifications.add(notification);
    }

    public ListView<Notification> addList()//List<Notification> notificationList){

    {
        System.out.println("size"+notifications.size());
        VBox notificationVbox=new VBox(5);
        notificationVbox.setPadding(new Insets(10));
        notificationVbox.setAlignment(Pos.CENTER);
        ObservableList<Notification> notificationObservableList=FXCollections.observableList(notifications);
        for (int i=0;i<notifications.size();i++){
            System.out.println("size of list "+notifications.size());
            notificationObservableList.add(notifications.get(i));
        }


        notificationListView.setCellFactory(new Callback<ListView<Notification>, ListCell<Notification>>() {
            @Override
            public ListCell<Notification> call(ListView<Notification> notificationListView) {
                return new NotificationListCell();
            }
        });


        //notificationVbox.setStyle("-fx-background: #4c84ff");

        //notificationListView.setItems(notificationObservableList);
        notificationVbox.getChildren().addAll(notificationListView);
        return notificationListView;
    }
}
