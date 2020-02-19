package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.LoginDelegate;
import com.iti.chat.model.User;
import static com.iti.chat.util.Encryption.encrypt;
import static com.iti.chat.util.Encryption.decrypt;
import com.iti.chat.util.JFXDialogFactory;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

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
    public void login(ActionEvent ae) throws FileNotFoundException {
        try {
            User user = delegate.login(phoneTextField.getText(), passwordField.getText());
            if (user != null) {
                Session.getInstance().setUser(user);
                PrintWriter writer;
//                writer = new PrintWriter("authenticationInfo2.txt", "UTF-8");
//                writer.println(phoneTextField.getText());
//                writer.println(passwordField.getText());
//                writer.close();
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
                    PrintWriter writer1 = new PrintWriter("rememberMeUserInfo.txt");
                    writer1.print("");
                    writer1.close();

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
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        File file = new File("rememberMeUserInfo.txt");

        try {

            if (!(file.length() == 0)) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                phoneTextField.setText(decrypt(reader.readLine()));
                try {
                    passwordField.setText(decrypt(reader.readLine()));
                } catch (IOException ex) {

                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);

                } finally {
                    reader.close();
                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        reader = null;
//        file = new File("authenticationInfo2.txt");;
//
//        if (!(file.length() == 0)) {
//            try {
//                reader = new BufferedReader(new FileReader(file));
//                String phone = reader.readLine();
//                String password = reader.readLine();
//
//                User user = delegate.login(phone, password);
//                if (user != null) {
//                    Session.getInstance().setUser(user);
//
//                    SceneTransition.goToHomeScene(stage);
//
//                }
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SQLException ex) {
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (NotBoundException ex) {
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//            } finally {
//                try {
//                    reader.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
    }
}
