package com.iti.chat.controller;

import com.iti.chat.delegate.LoginDelegate;
import com.iti.chat.model.User;

import static com.iti.chat.util.Encryption.encrypt;
import static com.iti.chat.util.Encryption.decrypt;
import static com.iti.chat.util.Hashing.getSecurePassword;

import com.iti.chat.util.JFXDialogFactory;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.Initializable;

public class LoginController implements Initializable {

    @FXML
    StackPane root;

    @FXML
    Button logInButton;

    @FXML
    PasswordField passwordField;

    @FXML
    TextField phoneTextField;

    @FXML
    CheckBox rememberMe;

    @FXML
    private Label warningLabel;

    private Stage stage;

    private LoginDelegate delegate;

    private boolean isRemembered = false;

    private boolean isLogged = true;

    public void setDelegate(LoginDelegate delegate) {

        this.delegate = delegate;
    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    @FXML
    public void login(ActionEvent ae) throws FileNotFoundException {
        try {
            //User user = delegate.login(phoneTextField.getText(), passwordField.getText());
            User user = delegate.login(phoneTextField.getText(), getSecurePassword(passwordField.getText()));
            if (user != null) {
                Session.getInstance().setUser(user);
                //System.out.printf("logged in as " + user);
                PrintWriter writer;
                try {
                    writer = new PrintWriter("rememberMeUserInfo.txt", "UTF-8");
                } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (isRemembered) {
                    try {
                        writer = new PrintWriter("rememberMeUserInfo.txt", "UTF-8");
                        writer.println(encrypt(phoneTextField.getText()));
                        writer.println(encrypt(passwordField.getText()));
                        writer.close();
                    } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    writer = new PrintWriter("rememberMeUserInfo.txt");
                    writer.print("");
                    writer.close();

                }

                try (PrintWriter writer1 = new PrintWriter("userAuthenticationInfo.txt")) {
                    writer1.print("");
                    writer1.println(encrypt(phoneTextField.getText()));
                    writer1.println(encrypt(passwordField.getText()));
                    writer1.close();
                }
                if (user.getIsAddedFromServer() == 0){
                    SceneTransition.goToHomeScene(stage);
                    System.out.println(user.getIsAddedFromServer());
                }
                else if(user.getIsAddedFromServer() == 1)
                {
                    SceneTransition.goToChangePasswordScene(stage);
                    System.out.println("else " + user.getIsAddedFromServer());
                }

            } else {
                //System.out.println("invalid phone or password");
                warningLabel.setText("invalid phone or password");
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

    @FXML
    public void rememberMe(ActionEvent ac) {
        if (rememberMe.isSelected()) {
            isRemembered = true;
        } else {
            isRemembered = true;
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        warningLabel.setText("");
        File file = new File("rememberMeUserInfo.txt");
        try {
            if (!(file.length() == 0)) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                phoneTextField.setText(decrypt(reader.readLine()));
                passwordField.setText(decrypt(reader.readLine()));
                reader.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
