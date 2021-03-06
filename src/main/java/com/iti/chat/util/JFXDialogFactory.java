package com.iti.chat.util;

import com.iti.chat.validator.RegisterValidation;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import java.util.concurrent.atomic.AtomicReference;

import static com.iti.chat.util.Hashing.getSecurePassword;


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

