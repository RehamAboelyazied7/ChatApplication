package com.iti.chat.viewcontroller;

import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.model.Gender;
import com.iti.chat.model.User;
import com.iti.chat.model.UserStatus;
import com.iti.chat.util.ImageCache;
import com.iti.chat.util.JFXCountryComboBox;
import com.iti.chat.util.JFXDialogFactory;
import com.iti.chat.util.Session;
import com.iti.chat.validator.RegisterValidation;
import com.jfoenix.controls.JFXComboBox;
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
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
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
    private JFXComboBox<String> status_combo_box;
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
    File selectedImage;

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
        status_combo_box.getItems().removeAll(status_combo_box.getItems());
         status_combo_box.getItems().addAll(UserStatus.statusToString(0),UserStatus.statusToString(1),UserStatus.statusToString(2),UserStatus.statusToString(3));
         status_combo_box.getSelectionModel().select(3);
         int  selectedIndex = status_combo_box.getSelectionModel().getSelectedIndex();
        status_combo_box.getSelectionModel().select(selectedIndex);
        status_combo_box.setOnAction(e->{
            switch (status_combo_box.getValue()){
                case "offline":
                    currentUser.setStatus(UserStatus.OFFLINE);
                    break;
                case "busy":
                    currentUser.setStatus(UserStatus.BUSY);
                    break;
                case "away":
                    currentUser.setStatus(UserStatus.AWAY);
                    break;
                default:
                    currentUser.setStatus(UserStatus.ONLINE);
                    break;
            }
            try {
                delegate.getClient().sessionService.userInfoDidChange(currentUser);
                delegate.getClient().updateUserInfo(currentUser);
                setUserStatus();
            } catch (RemoteException ex) {
                ex.printStackTrace();

            }


        });

    }


    public void setImage(Image image) {
        userImage.setFill(new ImagePattern(image));
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

            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);
            currentUser.setBio(bio);
            currentUser.setEmail(email);
            currentUser.setPhone(phone);
            currentUser.setCountry(country);
            try {
                delegate.updateUserInfo(currentUser);
                if(selectedImage != null) {
                    delegate.uploadImage(selectedImage, currentUser);
                    delegate.imageChanged();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
            System.out.println("enable chatbot");
        } else {
            currentUser.setChatBotEnabled(false);
            System.out.println("disable chatBot");
        }
        currentUser.setChatBotEnabled(true);
        try {
            delegate.updateUserInfo(currentUser);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
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
        selectedImage = fileChooser.showOpenDialog(stage);
        if (selectedImage != null) {
            image = new Image(selectedImage.toURI().toString());
            ImageCache.getInstance().setImage(currentUser, image);
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
        else if (currentUser.getStatus() == UserStatus.OFFLINE) {
            userStatus.setFill(Color.GREY);
        } else if (currentUser.getStatus() == UserStatus.AWAY)
            userStatus.setFill(Color.YELLOW);
        else
            userStatus.setFill(Color.GREEN);

    }

    private void setUserGender() {
        if (currentUser.getGender() == Gender.FEMALE)
            genderImage.setImage(new Image(getClass().getResource("/view/icons/Female.png").toExternalForm()));

        else if (currentUser.getGender() == Gender.MALE)
            genderImage.setImage(new Image(getClass().getResource("/view/icons/Male.png").toExternalForm()));


    }
}
