package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.delegate.UserPasswordDelegate;
import com.iti.chat.model.User;
import com.iti.chat.util.Hashing;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import com.iti.chat.validator.RegisterValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {
    @FXML
    PasswordField passwordField;

    @FXML
    PasswordField passwordFieldConfirm;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label warningLabel;

    private UserInfoDelegate delegate;
    private UserPasswordDelegate passwordDelegate;
    private Stage stage;
    private RegisterValidation validation;

    private boolean validatePassword(String password, String passwordConfirm) {
        validation = new RegisterValidation();
        boolean valid = true;
        if (validation.checkPass(password) == RegisterValidation.INVALID || validation.checkPass(password) == RegisterValidation.EMPTY) {
            warningLabel.setText("password should contain capital letter and numbers and special character");
            valid = false;
        }
        if (valid && !password.equals(passwordConfirm)) {
            warningLabel.setText("password don't match");
            valid = false;
        }
        return valid;
    }


    public void changePassword() {
        String password = passwordField.getText();
        String passwordConfirm = passwordFieldConfirm.getText();
        boolean valid = validatePassword(password, passwordConfirm);
        if (valid) {
            User currentUser = Session.getInstance().getUser();
            currentUser.setPassword(Hashing.getSecurePassword(password));
            if (currentUser.getIsAddedFromServer() == 1) {
                currentUser.setIsAddedFromServer(0);
                try {
                    passwordDelegate.updateUserPassword(currentUser);
                    SceneTransition.goToHomeScene(stage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    delegate.updateUserPassword(currentUser);
                    warningLabel.setText("password Updated");
                    //stage.close();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cancel(ActionEvent actionEvent) {
        User currentUser = Session.getInstance().getUser();
        if (currentUser.getIsAddedFromServer() == 1){
            try {
                passwordDelegate.logout(currentUser);
                SceneTransition.goToLoginScreen(stage);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }

        }else{
            stage.close();
        }

    }

    public void setDelegate(UserInfoDelegate delegate) {
        this.delegate = delegate;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public UserPasswordDelegate getPasswordDelegate() {
        return passwordDelegate;
    }

    public void setPasswordDelegate(UserPasswordDelegate passwordDelegate) {
        this.passwordDelegate = passwordDelegate;
    }
}
