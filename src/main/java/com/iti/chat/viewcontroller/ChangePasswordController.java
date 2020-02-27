package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.model.User;
import com.iti.chat.util.Hashing;
import com.iti.chat.util.Session;
import com.iti.chat.validator.RegisterValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.Buffer;
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
            try {
                delegate.updateUserPassword(currentUser);
                warningLabel.setText("password Updated");
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }

    public void setDelegate(UserInfoDelegate delegate) {
        this.delegate = delegate;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
