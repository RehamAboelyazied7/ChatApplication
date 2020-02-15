package com.iti.chat.viewcontroller;
import com.iti.chat.model.Notification;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;




public class NotificationListCell extends ListCell<Notification> {

    public NotificationListCell() {
        super();
    }

    @Override
    protected void updateItem(Notification notification, boolean b) {
        super.updateItem(notification, b);
        if(notification==null){
            setGraphic(null);
        }else{
            HBox hBox=new HBox(2);
            hBox.setAlignment(Pos.TOP_LEFT);
            Circle CircleImageView=new Circle(27.0, Color.TRANSPARENT);
            // CircleImageView.setRadius(27.0);
            CircleImageView.setFill(Color.TRANSPARENT);
            //  CircleImageView.setEffect(new Lighting());
          //  CircleImageView.setStroke(Color.BLUEVIOLET);
            CircleImageView.setStrokeLineCap(StrokeLineCap.ROUND);
            CircleImageView.setStrokeWidth(4.0);
            Image image = new Image(getClass().getResource("/view/sender-icon.png").toExternalForm());
            CircleImageView.setFill(new ImagePattern(image));
            Label notificationTitle=new Label("");
            notificationTitle.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(CircleImageView,new Label(notification.toString()));
            setGraphic(hBox);

        }

    }


        }
