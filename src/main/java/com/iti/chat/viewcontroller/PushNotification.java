package com.iti.chat.viewcontroller;

import com.iti.chat.model.Notification;
import com.iti.chat.model.NotificationType;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class PushNotification {
   public  int check=1;
   public int notificationNumber=0;
   //HomeController homeController=new HomeController();

    public  void initializeNotify(Notification notification) {
        createNotify(notification.toString(),notification.notificationType);
    }
    public void createNotify(String message,int notificationType){
        ImageView imageView=new ImageView(new Image(getClass().getResource("/view/sender.png").toExternalForm()));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);


        Platform.runLater(() -> {
            Stage owner = new Stage(StageStyle.TRANSPARENT);
            StackPane root = new StackPane();
            root.setStyle("-fx-background-color: TRANSPARENT");
            Scene scene;
            if(notificationNumber>=0){
                System.out.println("larg than 0");
                     scene   = new Scene(root, notificationNumber, 1);
                    }else{
                System.out.println("less than 1");
                scene=new Scene(root,1,1);
                    }

                    scene.setFill(Color.TRANSPARENT);
                    owner.setScene(scene);
                    owner.setWidth(1);
                    owner.setHeight(1);
                    //owner.toBack();

                    if(check==1) {
                        switch (notificationType) {
                            case NotificationType.FRIENDSHIP_ACCEPTED:
                                owner.show();
                                Notifications.create().graphic(imageView).title("FRIENDSHIP_ACCEPTED").text(message).show();
                                notificationNumber += 1;

                                break;
                            case NotificationType.FRIENDSHIP_REJECTED:
                                owner.show();
                                Notifications.create().graphic(imageView).title("FRIENDSHIP_REJECTED").text(message).show();
                                notificationNumber += 1;

                                break;
                            case NotificationType.FRIENDSHIP_REQUEST_RECEIVED:
                                owner.show();
                                Notifications.create().graphic(imageView).title("FRIENDSHIP_REQUEST_RECEIVED").text(message).show();
                                notificationNumber += 1;
                                break;
                            case NotificationType.MESSAGE_RECEIVED:
                                owner.show();
                                Notifications.create().graphic(imageView).title("MESSAGE_RECEIVED").text(message).show();
                                notificationNumber += 1;
                                break;
                            case NotificationType.STATUS_UPDATE:
                                owner.show();
                        Notifications.create().graphic(imageView).title("FRIEND_STATUS_UPDATE").text(message.toString()).show();
                                break;

                        }

                        check = 0;

                    }
            PauseTransition delay = new PauseTransition(Duration.millis(1000));
            delay.setOnFinished( event -> owner.close() );
            delay.play();



                }  );
                //owner.close();



    }


}
