package com.iti.chat.util;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSpinner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;
import java.util.stream.Stream;

public class JFXDialogFactory {
    public static void showConnectionError(StackPane container) {
        JFXDialogLayout layout = new JFXDialogLayout();
        Text connectionError = new Text("Connection Error");
        String imagePath = JFXDialogFactory.class.getResource("/view/crc_error.jpg").toExternalForm();
        Image redSign = new Image(imagePath, 30, 30, true, true);
        ImageView imageView = new ImageView(redSign);
        imageView.setStyle("-fx-background-color: transparent;");
        HBox heading = new HBox(imageView, connectionError);
        heading.setSpacing(10);
        heading.setAlignment(Pos.CENTER);
        layout.setHeading(heading);
        layout.setBody(new Text("Can't connect to server, please check your internet connection"));
        JFXDialog dialog = new JFXDialog();
        dialog.setDialogContainer(container);
        layout.setStyle("-fx-background-color: #DCDCDC;");
        dialog.setContent(layout);
        Button ok = new Button("Ok");
        ok.setOnAction(e -> {
            dialog.close();
        });
        layout.setActions(ok);
        dialog.show();
    }
}
