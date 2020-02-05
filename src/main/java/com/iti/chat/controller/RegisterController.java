package com.iti.chat.controller;

import com.iti.chat.dao.UserDAO;
import com.iti.chat.dbservice.UserDAOImpl;
import com.iti.chat.validator.RegisterValidation;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.PopupControl;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadioButton;

    //error labels
    @FXML
    private Text firstNameError;

    @FXML
    private Text lastNameError;

    @FXML
    private Text phoneNumberError;

    @FXML
    private Text passwordError;

    @FXML
    private Text genderError;

    @FXML
    private Text confirmPasswordError;

    //error tooltips to explain what is wrong with data validation
    Tooltip passwordTooltip = new Tooltip("Password must contains a small letter" +
            ", a capital letter, a number and a special character and must contains at least 8 characters.");
    Tooltip nameTooltip = new Tooltip("Please enter only characters");
    Tooltip phoneTooltip = new Tooltip("Please enter a valid number");

    //popup boxes
    PopupControl passwordPopupControl = new PopupControl();

    //Validation variables 0 -> empty , 1 -> valid -1 -> invalid
    private int firstNameValidation;
    private int lastNameValidation;
    private int phoneValidation;
    private int passwordValidation;
    private boolean passwordMatchValidation;

    SimpleBooleanProperty isMale = new SimpleBooleanProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        firstNameTextField.setTooltip(nameTooltip);
        lastNameTextField.setTooltip(nameTooltip);
        passwordTextField.setTooltip(passwordTooltip);
        phoneTextField.setTooltip(phoneTooltip);

    }

    @FXML
    public void submitButtonHandler(ActionEvent event) {

        if (validateInputValues()) {

            UserDAO userDAO = new UserDAOImpl();
            try {

                userDAO.register(firstNameTextField.getText(), lastNameTextField.getText(), phoneTextField.getText(),
                        passwordTextField.getText(), "");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean validateInputValues() {

        RegisterValidation validator = new RegisterValidation();
        boolean isValid = true;
        boolean isPasswordValid = true;

        firstNameValidation = validator.checkName(firstNameTextField.getText());
        lastNameValidation = validator.checkName(lastNameTextField.getText());
        phoneValidation = validator.checkPhone(phoneTextField.getText());
        passwordValidation = validator.checkPass(passwordTextField.getText());
        passwordMatchValidation = passwordTextField.getText().equals(confirmPasswordTextField.getText());

        //validation conditions
        switch (firstNameValidation) {

            case 0:
                firstNameError.setFont(new Font(15));
                firstNameError.setText("Enter your name");
                firstNameError.setVisible(true);
                isValid = false;
                break;
            case -1:
                firstNameError.setFont(new Font(15));
                firstNameError.setVisible(true);
                isValid = false;
                firstNameError.setText("Invalid name!");
            default:
                firstNameError.setVisible(false);
        }

        //last name
        switch (lastNameValidation) {

            case 0:
                lastNameError.setFont(new Font(15));
                lastNameError.setText("Enter your name");
                lastNameError.setVisible(true);
                isValid = false;
                break;
            case -1:
                lastNameError.setFont(new Font(15));
                lastNameError.setText("Invalid name");
                lastNameError.setVisible(true);
                isValid = false;
            default:
                lastNameError.setVisible(false);

        }

        //Phone Number
        switch (phoneValidation) {

            case 0:
                phoneNumberError.setFont(new Font(15));
                phoneNumberError.setText("Enter your phone");
                phoneNumberError.setVisible(true);
                isValid = false;
                break;
            case -1:
                phoneNumberError.setFont(new Font(15));
                phoneNumberError.setText("Invalid phone number");
                phoneNumberError.setVisible(true);
                isValid = false;
                break;
            default:
                phoneNumberError.setVisible(false);
        }

        //gender checking
        if (maleRadioButton.selectedProperty().getValue() == false
                && femaleRadioButton.selectedProperty().getValue() == false) {

            genderError.setText("Select gender");
            genderError.setFont(new Font(15));
            genderError.setVisible(true);

        } else {

            genderError.setVisible(false);

        }

        //password validation
        switch (passwordValidation) {

            case 0:
                passwordError.setFont(new Font(15));
                passwordError.setText("Enter your password");
                passwordError.setVisible(true);
                confirmPasswordError.setVisible(false);
                isValid = false;
                isPasswordValid = false;
                break;
            case -1:
                System.out.println("password validation " + passwordValidation);
                passwordError.setFont(new Font(15));
                passwordError.setText("Password is invalid!");
                passwordError.setVisible(true);
                confirmPasswordError.setVisible(false);
                isValid = false;
                isPasswordValid = false;
                break;
            default:
                passwordError.setVisible(false);

        }

        if (!passwordTextField.getText().isEmpty() && isPasswordValid) {
            if (!passwordTextField.getText().equals(confirmPasswordTextField.getText())) {

                confirmPasswordError.setFont(new Font(15));
                confirmPasswordError.setText("Passwords don't match");
                confirmPasswordError.setVisible(true);

            } else {

                confirmPasswordError.setVisible(false);

            }
        }
        return isValid;
    }


    //handlers
    @FXML
    public void maleRadioButtonActionHandler(ActionEvent event) {

        event.consume();
        maleRadioButton.selectedProperty().setValue(true);
        femaleRadioButton.selectedProperty().setValue(false);

    }

    @FXML
    public void femaleRadioButtonActionHandler(ActionEvent event) {

        event.consume();
        maleRadioButton.selectedProperty().setValue(false);
        femaleRadioButton.selectedProperty().setValue(true);

    }


}
