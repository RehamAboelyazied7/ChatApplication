package com.iti.chat.viewcontroller;


import com.iti.chat.delegate.LoginDelegate;
import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.model.User;
import com.iti.chat.util.Session;
import com.iti.chat.validator.RegisterValidation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    @FXML
    private Circle userImage;

    @FXML
    private Button editBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea bioField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField genderField;

    @FXML
    private Label nameWarning;

    @FXML
    private Label emailWarning;

    @FXML
    private Label phoneWarning;

    private Stage stage;
    private UserInfoDelegate delegate;

    private RegisterValidation validation;
    private String name;
    private String bio;
    private String email;
    private String phone;
    private String country;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        validation = new RegisterValidation();
        setEditableFields();
        collectData();

        userImage.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Image image = selectImage();
            if (image != null) {
                userImage.setFill(new ImagePattern(image));
            }
        });
    }

    public void setDelegate(UserInfoDelegate delegate) {

        this.delegate = delegate;
    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }


    @FXML
    public void edit() {
        setEditableFields();
    }

    @FXML
    public void save() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String country = countryField.getText();
        String firstName = new String();
        String lastName = new String();
        boolean validData = true;
        if (validation.checkName(name) == -1 || validation.checkName(name) == 0) {
            nameWarning.setText("Invalid");
            validData = false;

        }

        /*if (validation.checkEmail(email) == -1) {
            emailWarning.setText("Invalid");
            System.out.println("email");
            validData = false;
        }*/

        if (validation.checkPhone(phone) == -1) {
            phoneWarning.setText("Invalid");
            validData = false;
        }
        if (validData) {
            String[] tempName = name.split("\\s");
            firstName = tempName[0];
            System.out.println(firstName);
            for (int i = 1; i < tempName.length; i++) {
                System.out.println(tempName[i]);
                lastName += tempName[i];
            }

           // User currentUser = Session.getInstance().getUser();
            User currentUser = new User();
            currentUser.setId(1);
            currentUser.setFirstName(firstName);
            currentUser.setEmail(email);
            currentUser.setPhone(phone);
            currentUser.setCountry(country);
            try {
                delegate.updateUserInfo(currentUser);
                collectData();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            collectData();
            setEditableFields();
            clearWarnings();
        }
    }
    //Session.getInstance().getUser();
    // update database
    //update view


    @FXML
    public void cancel() {
        nameField.setText(name);
        bioField.setText(bio);
        emailField.setText(email);
        phoneField.setText(phone);
        countryField.setText(country);
        setEditableFields();
        clearWarnings();

    }

    private void setEditableFields() {
        saveBtn.setVisible(!saveBtn.isVisible());
        cancelBtn.setVisible(!cancelBtn.isVisible());
        bioField.setEditable(!bioField.isEditable());
        nameField.setEditable(!nameField.isEditable());
        emailField.setEditable(!emailField.isEditable());
        phoneField.setEditable(!phoneField.isEditable());
        countryField.setEditable(!countryField.isEditable());
        genderField.setEditable(!genderField.isEditable());

    }

    private void collectData() {

        name = nameField.getText();
        bio = bioField.getText();
        email = emailField.getText();
        phone = phoneField.getText();
        country = countryField.getText();
    }

    private void clearWarnings() {
        nameWarning.setText("");
        emailWarning.setText("");
        phoneWarning.setText("");
    }

    private Image selectImage() {
        Image image = null;
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            image = new Image(file.toURI().toString());
        }
        return image;

    }
}
