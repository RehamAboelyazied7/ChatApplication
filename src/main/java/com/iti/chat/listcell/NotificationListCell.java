package com.iti.chat.listcell;

import com.iti.chat.controller.HomeController;
import com.iti.chat.model.Notification;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;


public class NotificationListCell extends ListCell<Notification> {

    private HomeController homeController;
    private Image image;
    public NotificationListCell(HomeController homeController) {
        super();
        this.homeController = homeController;
    }

    public NotificationListCell() {

    }
    public void setImage(Image image){
        this.image=image;
    }
    @Override
    protected void updateItem(Notification notification, boolean empty) {
        super.updateItem(notification, empty);

        if (notification == null || empty) {
            setGraphic(null);
        } else {
            HBox hBox = new HBox(2);
            hBox.setAlignment(Pos.TOP_LEFT);
            //Circle CircleImageView = new Circle(27.0, Color.TRANSPARENT);
            // CircleImageView.setRadius(27.0);
            //CircleImageView.setFill(Color.TRANSPARENT);
            //  CircleImageView.setEffect(new Lighting());
            //  CircleImageView.setStroke(Color.BLUEVIOLET);
            //CircleImageView.setStrokeLineCap(StrokeLineCap.ROUND);
            //CircleImageView.setStrokeWidth(4.0);
            //Image image = new Image(getClass().getResource("/fxml/sender.png").toExternalForm());
            //CircleImageView.setFill(new ImagePattern(image));
            Label notificationTitle = new Label(notification.toString());
            notificationTitle.setAlignment(Pos.CENTER);
            notificationTitle.setWrapText(true);
            hBox.setStyle("-fx-font-size: 10;-fx-alignment:  center");
            hBox.getChildren().addAll(notificationTitle);
            setGraphic(hBox);

        }


    }


}
