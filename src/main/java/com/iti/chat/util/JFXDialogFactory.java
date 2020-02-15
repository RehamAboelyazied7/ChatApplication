package com.iti.chat.util;

import com.iti.chat.validator.RegisterValidation;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import com.jfoenix.controls.JFXSpinner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import java.util.concurrent.atomic.AtomicReference;



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

    public static String changeUserPassWord(StackPane container) {
        AtomicReference<String> password = new AtomicReference<>();
        RegisterValidation validator = new RegisterValidation();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        TextField newPassword = new TextField();
        TextField confirmPassword = new TextField();

        Label newPasswordLabel = new Label("new password");
        Label confirmPasswordLabel = new Label("confirm password");
        Label warningLabel = new Label("");

        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(newPasswordLabel, 0, 1);
        gridPane.add(newPassword, 1, 1);
        gridPane.add(confirmPasswordLabel, 0, 2);
        gridPane.add(confirmPassword, 1, 2);
        gridPane.add(warningLabel, 1, 3);

        Dialog dialog = new Dialog<>();
        //Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        //stage.getIcons().add(new Image(JFXDialogFactory.class.getResource("view/icons/rsz_lock1.png").toString()));
        //gridPane.getStylesheets().add("view/profileScreen.css");
        dialog.getDialogPane().setContent(gridPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button okBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.addEventFilter(ActionEvent.ACTION, event -> {
            if (validator.checkPass(newPassword.getText()) == 0 ||
                    validator.checkPass(newPassword.getText()) == -1 ||
                    !newPassword.getText().equals(confirmPassword.getText())) {
                System.out.println("here invalid");
                warningLabel.setText("INVALID PASSWORD");
                password.set("NOTVALIDPASSWORD");
                event.consume();

            } else {
                System.out.println("here done");
                warningLabel.setText("DONE");
                password.set(newPassword.getText());
                dialog.close();
            }
        });
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelBtn.addEventFilter(
                ActionEvent.ACTION, event -> {
                    password.set("NOTVALIDPASSWORD");
                    dialog.close();
                });
        dialog.showAndWait();
        return password.get();
    }


}

