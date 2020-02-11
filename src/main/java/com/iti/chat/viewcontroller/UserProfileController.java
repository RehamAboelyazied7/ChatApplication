package com.iti.chat.viewcontroller;


import com.iti.chat.validator.RegisterValidation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    @FXML
    Button editBtn;

    @FXML
    Button saveBtn;

    @FXML
    Button cancelBtn;

    @FXML
    TextField nameField;

    @FXML
    TextArea bioField;

    @FXML
    TextField emailField;

    @FXML
    TextField phoneField;

    @FXML
    TextField countryField;

    @FXML
    TextField genderField;

    @FXML
    Label nameWarning;

    @FXML
    Label emailWarning;

    @FXML
    Label phoneWarning;

    RegisterValidation validation;
    String name;
    String bio;
    String email;
    String phone;
    String country;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        validation = new RegisterValidation();
        setEditableFields();
        collectData();
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
        boolean validData = true;
        if (validation.checkName(name) == -1 || validation.checkName(name) == 0) {
            nameWarning.setText("Invalid");
            validData = false;

        }
        if (validation.checkEmail(email) == -1) {
            emailWarning.setText("Invalid");
            validData = false;
        }
        if (validation.checkPhone(phone) == -1) {
            phoneWarning.setText("Invalid");
            validData = false;
        }
        if (validData) {
            // check phone no with database
        }
        // update database
        //update view


    }

    @FXML
    public void cancel() {
        nameField.setText(name);
        bioField.setText(bio);
        emailField.setText(email);
        phoneField.setText(phone);
        countryField.setText(country);

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
}
