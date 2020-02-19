package com.iti.chat.viewcontroller;

import com.iti.chat.model.Message;
import com.iti.chat.model.Notification;
import com.iti.chat.model.NotificationType;
import com.iti.chat.model.UserStatus;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Notifications;

public class PushNotification {
   public  int check=1;

    public  void createNotify(Notification notification) {
        ImageView imageView=new ImageView(new Image(getClass().getResource("/view/sender.png").toExternalForm()));
        imageView.setFitHeight(50);
        imageView.setFitHeight(50);
        Platform.runLater(() -> {
            if(check==1) {
                switch (notification.notificationType) {
                    case NotificationType.FRIENDSHIP_ACCEPTED:
                        Notifications.create().graphic(imageView).title("FRIENDSHIP_ACCEPTED").text(notification.toString()).show();
                        break;
                    case NotificationType.FRIENDSHIP_REJECTED:
                        Notifications.create().graphic(imageView).title("FRIENDSHIP_REJECTED").text(notification.toString()).show();
                        break;
                    case NotificationType.FRIENDSHIP_REQUEST_RECEIVED:
                        Notifications.create().graphic(imageView).title("FRIENDSHIP_REQUEST_RECEIVED").text(notification.toString()).show();
                        break;
                    case NotificationType.MESSAGE_RECEIVED:
                        Notifications.create().graphic(imageView).title("MESSAGE_RECEIVED").text(notification.toString()).show();
                        break;

                }
                check=0;
            }

        });

    }
}
