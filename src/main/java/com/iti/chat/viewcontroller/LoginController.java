package com.iti.chat.viewcontroller;

import com.iti.chat.dao.UserDAO;
import com.iti.chat.model.User;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.StringUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginController {
    @FXML
    AnchorPane root;

    @FXML
    Button logInButton;

    @FXML
    PasswordField passwordField;

    @FXML
    TextField phoneTextField;

    private UserDAO userDAO;

    private Stage stage;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void login(ActionEvent ae) {
        try {
            String phone = StringUtil.addSingleQuotes(phoneTextField.getText());
            String password = StringUtil.addSingleQuotes(passwordField.getText());
            User user = userDAO.login(phone, password);
            if(user == null) {
                System.out.println("Invalid phone or password");
            }
            else {
                SceneTransition.goToHomeScene(stage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
