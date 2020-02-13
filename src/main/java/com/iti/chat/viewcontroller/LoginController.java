package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.LoginDelegate;
import com.iti.chat.model.User;
import com.iti.chat.util.JFXDialogFactory;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    StackPane root;

    @FXML
    Button logInButton;

    @FXML
    PasswordField passwordField;

    @FXML
    TextField phoneTextField;


    private Stage stage;

    private LoginDelegate delegate;


    public void setDelegate(LoginDelegate delegate) {

        this.delegate = delegate;
    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    @FXML
    public void login(ActionEvent ae) {
        try {
            User user = delegate.login(phoneTextField.getText(), passwordField.getText());
            if(user != null) {
                Session.getInstance().setUser(user);
                SceneTransition.goToUserProfilerScene(stage);
            }
            else {
                System.out.println("invalid phone or password");
            }

        } catch (SQLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
            JFXDialogFactory.showConnectionError(root);
        }
    }

    @FXML
    public void register(ActionEvent ac) {
        SceneTransition.goToRegisterScene(stage);
    }



}
