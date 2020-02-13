package com.iti.chat.util;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class JFXDialogFactory {
    public static void showConnectionError(StackPane container) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text("Connection Error"));
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
