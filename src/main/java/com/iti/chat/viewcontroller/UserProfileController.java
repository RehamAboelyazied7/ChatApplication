package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.model.Gender;
import com.iti.chat.model.User;
import com.iti.chat.model.UserStatus;
import com.iti.chat.util.ColorUtils;
import com.iti.chat.util.JFXCountryComboBox;
import com.iti.chat.util.JFXDialogFactory;
import com.iti.chat.util.Session;
import com.iti.chat.validator.RegisterValidation;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    @FXML
    private StackPane profilPane;

    @FXML
    private Circle userImage;

    @FXML
    private Circle userStatus;

    @FXML
    private Button editBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button passwordBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea bioField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> countryField;

    @FXML
    private Label nameWarning;

    @FXML
    private Label emailWarning;

    @FXML
    private Label phoneWarning;

    @FXML
    private ImageView genderImage;

    @FXML
    private JFXToggleButton chatBot;

    private Stage stage;
    private UserInfoDelegate delegate;

    private RegisterValidation validation;
    private String name;
    private String bio;
    private String email;
    private String phone;
    private String country;

    User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = Session.getInstance().getUser();
        setUserInfo();
        validation = new RegisterValidation();
        addCountries();
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
        String country = countryField.getValue();
        String bio = bioField.getText();
        System.out.println(countryField.getValue());
        String firstName = new String();
        String lastName = new String();
        boolean validData = true;
        if (name.trim().isEmpty()) {
            nameWarning.setText("Invalid");
            validData = false;

        }
        // check email is not accurate
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


            //User currentUser = new User();
            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);
            currentUser.setBio(bio);
            currentUser.setEmail(email);
            currentUser.setPhone(phone);
            currentUser.setCountry(country);
            try {
                delegate.updateUserInfo(currentUser);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            collectData();
            setEditableFields();
            clearWarnings();
        }
    }

    @FXML
    public void cancel() {
        nameField.setText(name);
        bioField.setText(bio);
        emailField.setText(email);
        phoneField.setText(phone);
        countryField.setValue(country);
        setEditableFields();
        clearWarnings();

    }

    @FXML
    public void changePassword() {
        String password = JFXDialogFactory.changeUserPassWord(profilPane);
        if (password != null) {
            System.out.println(password);
            if (password.equals("NOTVALIDPASSWORD")) {

            } else {
                try {
                    currentUser.setPassword(password);
                    delegate.updateUserPassword(currentUser);
                    nameWarning.setText("password Updated");
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @FXML
    public void enableChatBot() {
        if (chatBot.isSelected()) {
            currentUser.setChatBotEnabled(true);
            System.out.println("enable chatbot");
        } else {
            currentUser.setChatBotEnabled(false);
            System.out.println("disable chatBot");
        }
    }

    private void setEditableFields() {
        saveBtn.setVisible(!saveBtn.isVisible());
        cancelBtn.setVisible(!cancelBtn.isVisible());
        bioField.setEditable(!bioField.isEditable());
        nameField.setEditable(!nameField.isEditable());
        emailField.setEditable(!emailField.isEditable());
        countryField.setEditable(!countryField.isEditable());

    }

    private void collectData() {
        name = nameField.getText();
        bio = bioField.getText();
        email = emailField.getText();
        phone = phoneField.getText();
        country = countryField.getValue();
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

    private void setUserInfo() {
        nameField.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        bioField.setText(currentUser.getBio());
        phoneField.setText(currentUser.getPhone());
        emailField.setText(currentUser.getEmail());
        countryField.setValue(currentUser.getCountry());
        setUserGender();
        setUserStatus();

    }

    private void addCountries() {
        JFXCountryComboBox country = new JFXCountryComboBox();
        country.addCountries(countryField);
    }

    private void setUserStatus() {
        if (currentUser.getStatus() == UserStatus.BUSY)
            userStatus.setFill(Color.RED);
        else if (currentUser.getStatus() == UserStatus.OFFLINE)
            userStatus.setFill(Color.GREY);
        else if (currentUser.getStatus() == UserStatus.AWAY)
            userStatus.setFill(Color.YELLOW);
        else userStatus.setFill(Color.GREEN);

    }

    private void setUserGender() {
        if (currentUser.getGender() == Gender.FEMALE)
            genderImage.setImage(new Image(getClass().getResource("/view/icons/Female.png").toExternalForm()));
            //System.out.println("Femlae");
        else if (currentUser.getGender() == Gender.MALE)
            genderImage.setImage(new Image(getClass().getResource("/view/icons/Male.png").toExternalForm()));
        //System.out.println("male");

    }
}
