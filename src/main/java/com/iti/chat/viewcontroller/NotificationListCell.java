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

    private HomeController homeController;
    public NotificationListCell(HomeController homeController) {
        super();
        this.homeController = homeController;
    }

    public NotificationListCell() {

    }

    @Override
    protected void updateItem(Notification notification, boolean empty) {
        super.updateItem(notification, empty);
        if (notification == null || empty) {
            setGraphic(null);
        } else {
            HBox hBox = new HBox(2);
            hBox.setAlignment(Pos.TOP_LEFT);
            Circle CircleImageView = new Circle(27.0, Color.TRANSPARENT);
            // CircleImageView.setRadius(27.0);
            CircleImageView.setFill(Color.TRANSPARENT);
            //  CircleImageView.setEffect(new Lighting());
            //  CircleImageView.setStroke(Color.BLUEVIOLET);
            CircleImageView.setStrokeLineCap(StrokeLineCap.ROUND);
            CircleImageView.setStrokeWidth(4.0);
            Image image = new Image(getClass().getResource("/view/sender.png").toExternalForm());
            CircleImageView.setFill(new ImagePattern(image));
            Label notificationTitle = new Label("");
            notificationTitle.setAlignment(Pos.CENTER);
            notificationTitle.setWrapText(true);
            hBox.setStyle("-fx-font-size: 10;-fx-alignment:  center");
            hBox.getChildren().addAll(CircleImageView, new Label(notification.toString()));
            setGraphic(hBox);

        }

    }


}
