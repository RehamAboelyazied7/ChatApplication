package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.LoginDelegate;
import com.iti.chat.delegate.RegisterDelegate;
import com.iti.chat.exception.DuplicatePhoneException;
import com.iti.chat.model.Gender;
import com.iti.chat.model.User;
import com.iti.chat.model.UserStatus;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.validator.RegisterValidation;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

public class RegisterController implements Initializable {

    @FXML
    private Circle imageCircle;

    @FXML
    private ImageView tempImageView;

   // @FXML
    private AnchorPane rootPane;

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

    private Stage stage;

    @FXML
    private Button cancelButton;

    File selectedFile ;
    //error tooltips to explain what is wrong with data validation
    Tooltip passwordTooltip = new Tooltip("Password must contains a small letter" +
            ", a capital letter, a number and a special character and must contains at least 8 characters.");
    Tooltip nameTooltip = new Tooltip("Please enter only characters");
    Tooltip phoneTooltip = new Tooltip("Please enter a valid number");

    //Validation variables 0 -> empty , 1 -> valid -1 -> invalid
    private int firstNameValidation;
    private int lastNameValidation;
    private int phoneValidation;
    private int passwordValidation;
    private boolean passwordMatchValidation;
    private RegisterDelegate delegate;

    SimpleBooleanProperty isMale = new SimpleBooleanProperty();

    public void setDelegate(RegisterDelegate delegate) {

        this.delegate = delegate;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        imageCircle.setFill(new ImagePattern(new Image("userIcon.png")));
        imageCircle.setFill(new ImagePattern(tempImageView.getImage()));

        firstNameTextField.setTooltip(nameTooltip);
        lastNameTextField.setTooltip(nameTooltip);
        passwordTextField.setTooltip(passwordTooltip);
        phoneTextField.setTooltip(phoneTooltip);

    }

    @FXML
    public void submitButtonHandler(ActionEvent event) throws IOException, RemoteException, SQLException, NotBoundException {

        if (validateInputValues()) {

//            UserDAO userDAO = new UserDAOImpl();
//            try {
//
//                System.out.println("B register");
////                userDAO.register(firstNameTextField.getText(), lastNameTextField.getText(), phoneTextField.getText(),
////                        passwordTextField.getText(), "");
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            System.out.println("B register");
            User user = new User();
            user.setFirstName(firstNameTextField.getText());
            user.setLastName(lastNameTextField.getText());
            user.setGender(Gender.MALE);
            user.setPhone(phoneTextField.getText());
            user.setEmail("a@a.com");
            try {
                delegate.register(user, passwordTextField.getText());
                if(selectedFile != null) {
                    delegate.uploadImage(selectedFile,user);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (DuplicatePhoneException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            SceneTransition.goToLoginScreen(stage);


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
                firstNameError.setText("Invalid name!");
                firstNameError.setVisible(true);
                isValid = false;
                break;
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
                break;
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
                isValid = false;

            } else {

                confirmPasswordError.setVisible(false);

            }
        }
        System.out.println(isValid);
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

    @FXML
    public void cancelButtonActionHandler(ActionEvent event) {

       // ((Stage) (rootPane.getScene().getWindow())).close();
           Stage stage = (Stage) cancelButton.getScene().getWindow();
           stage.close();
    }

    @FXML
    public void choosePictureButtonActionHandler(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
         selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {

            URI uri = selectedFile.toURI();
//            imageCircle.setFill(new ImagePattern(new Image(selectedFile.getPath())));

//            pictureImage.setImage(new Image());
            System.out.println(selectedFile.toURI().toString());
//            imageCircle.setFill(new ImagePattern(new Image(selectedFile.toURI().toString())));
            imageCircle.setFill(new ImagePattern(new Image(selectedFile.toURI().toString())));

//
        }

    }


}
