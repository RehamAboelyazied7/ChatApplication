package com.iti.chat.viewcontroller;

import com.iti.chat.model.Notification;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Notifications;

public class NotificationController {
    public void displayNotification(Notification notification) {//List<Notification> notification) {


        Platform.runLater(new Runnable() {
                              @Override
                              public void run() {


                                  ImageView imageView = new ImageView(new Image(this.getClass().getResource("/view/sender.png").toExternalForm()));
                                  imageView.setFitWidth(50);
                                  imageView.setFitHeight(50);
                                 // if()
                                  Notifications.create().graphic(imageView).title("add").text("shimaa send freind request").darkStyle().
                                          show();
                              }
                          }
        );
    }
}
