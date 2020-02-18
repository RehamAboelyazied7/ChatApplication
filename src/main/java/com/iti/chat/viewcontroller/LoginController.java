package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.LoginDelegate;
import com.iti.chat.model.User;
import com.iti.chat.util.JFXDialogFactory;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.CheckBox;

public class LoginController {

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

    private Stage stage;

    private LoginDelegate delegate;

    private boolean isRemembered = false;

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
            if (user != null) {
                Session.getInstance().setUser(user);
                PrintWriter writer;
                try {
                    writer = new PrintWriter("authenticationInfo.txt", "UTF-8");
                } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (isRemembered) {
                    try {
                        writer = new PrintWriter("authenticationInfo.txt", "UTF-8");
                        writer.println(phoneTextField.getText());
                        writer.println(passwordField.getText());
                        writer.close();
                    } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    try {
                        try ( FileWriter fileWriter = new FileWriter("authenticationInfo.txt", false);  PrintWriter printWriter = new PrintWriter(fileWriter, false)) {
                            printWriter.flush();
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                SceneTransition.goToHomeScene(stage);
            } else {
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

    @FXML
    public void rememberMe(ActionEvent ac) {
        if (rememberMe.isSelected()) {
            isRemembered = true;
        } else {
            isRemembered = true;
        }
    }

//    public void login(String phoneNumber, String password) {
//        try {
//            User user = delegate.login(phoneNumber, password);
//            Session.getInstance().setUser(user);
//            SceneTransition.goToHomeScene(stage);
//        } catch (RemoteException | SQLException | NotBoundException ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
