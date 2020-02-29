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
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private TextArea bioField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private JFXDatePicker birthDateField;

    @FXML
    private Text nameText;

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
    private Label birthDateWarning;

    @FXML
    private JFXToggleButton chatBot;

    private Stage stage;
    private UserInfoDelegate delegate;
    private RegisterValidation validation;
    private String firstName;
    private String lastName;
    private String bio;
    private String email;
    private String country;
    File selectedImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserInfo();
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
        //UserStatus.statusToString(0)
        status_combo_box.getItems().addAll(UserStatus.statusToString(1), UserStatus.statusToString(2), UserStatus.statusToString(3));
        status_combo_box.setPromptText("Change Status");
    }


    public void setImage() {
        User currentUser = Session.getInstance().getUser();
        Image image = ImageCache.getInstance().getImage(currentUser);
        if (image == null) {
            image = ImageCache.getInstance().getDefaultImage(currentUser);
        }
        userImage.setFill(new ImagePattern(image));
    }

    public void setDelegate(UserInfoDelegate delegate) {
        this.delegate = delegate;
        status_combo_box.setOnAction(e -> {
            switch (status_combo_box.getValue()) {

                case "busy":
                    Session.getInstance().getUser().setStatus(UserStatus.BUSY);
                    break;
                case "away":
                    Session.getInstance().getUser().setStatus(UserStatus.AWAY);
                    break;
                default:
                    Session.getInstance().getUser().setStatus(UserStatus.ONLINE);
                    break;
            }
            delegate.getClient().updateStatus(Session.getInstance().getUser());
            //delegate.getClient().sessionService.userInfoDidChange(currentUser);
            setUserStatus();

        });

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void edit() {
        validation = new RegisterValidation();
        setEditableFields();
    }

    private boolean validateUserData() {
        boolean isValidData = true;

        if (firstNameField.getText().trim().length() == 0 || validation.checkName(firstNameField.getText()) == -1) {
            nameWarning.setText("Invalid");
            isValidData = false;
        }

        if (validation.checkEmail(emailField.getText()) == RegisterValidation.INVALID) {
            emailWarning.setText("Invalid Email");
            isValidData = false;
        }

        if (birthDateField.getValue() == null) {
            birthDateWarning.setText("invalid date");
            System.out.println(birthDateField.getValue().toString());
            isValidData = false;
        }
        return isValidData;
    }

    @FXML
    public void save() {
        boolean isValidData = validateUserData();
        if (isValidData) {
            User currentUser = Session.getInstance().getUser();
            currentUser.setFirstName(firstNameField.getText());
            currentUser.setLastName(lastNameField.getText());
            currentUser.setBio(bioField.getText());
            currentUser.setEmail(emailField.getText());
            currentUser.setCountry(countryField.getValue());
            currentUser.setBirthDate(birthDateField.getValue());
            try {
                delegate.updateUserInfo(currentUser);
                nameText.setText(firstNameField.getText() + " " + lastNameField.getText());
                collectData();
                setEditableFields();
                clearWarnings();
            } catch (RemoteException e) {
                e.printStackTrace();
                nameWarning.setText("can't update your data");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    public void cancel() {
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        bioField.setText(bio);
        emailField.setText(email);
        countryField.setValue(country);
        setEditableFields();
        clearWarnings();
    }

    @FXML
    public void changePassword() {
        User currentUser = Session.getInstance().getUser();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/changePassword.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setAlwaysOnTop(true);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("change password");
            stage.setScene(new Scene(root));
            ChangePasswordController passwordController = (ChangePasswordController) fxmlLoader.getController();
            passwordController.setDelegate(delegate);
            passwordController.setStage(stage);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void enableChatBot() {
        User currentUser = Session.getInstance().getUser();
        if (chatBot.isSelected()) {
            //System.out.println("enable chatbot");
            currentUser.setChatBotEnabled(true);
        } else {
            currentUser.setChatBotEnabled(false);
            //System.out.println("disable chatBot");
        }

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
        firstNameField.setEditable(!firstNameField.isEditable());
        lastNameField.setEditable(!lastNameField.isEditable());
        emailField.setEditable(!emailField.isEditable());
        countryField.setDisable(!countryField.isDisabled());
        birthDateField.setDisable(!birthDateField.isDisabled());
        phoneField.setEditable(false);

    }

    private void collectData() {
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();
        bio = bioField.getText();
        email = emailField.getText();
        country = countryField.getValue();
    }

    private void clearWarnings() {
        nameWarning.setText("");
        emailWarning.setText("");
        birthDateWarning.setText("");
    }

    private Image selectImage() {
        Image image = null;
        User currentUser = Session.getInstance().getUser();
        FileChooser fileChooser = new FileChooser();
        selectedImage = fileChooser.showOpenDialog(stage);
        if (selectedImage != null) {
            try {
                delegate.uploadImage(selectedImage, currentUser);
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            delegate.imageChanged();
            image = new Image(selectedImage.toURI().toString());
            ImageCache.getInstance().setImage(currentUser, image);
        }
        return image;

    }

    private void setUserInfo() {
        User currentUser = Session.getInstance().getUser();
        nameText.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        bioField.setText(currentUser.getBio());
        firstNameField.setText(currentUser.getFirstName());
        lastNameField.setText(currentUser.getLastName());
        phoneField.setText(currentUser.getPhone());
        emailField.setText(currentUser.getEmail());
        countryField.setValue(currentUser.getCountry());
        birthDateField.setValue(currentUser.getBirthDate());
        chatBot.setSelected(currentUser.isChatBotEnabled());
        //chatBot.setSelected(currentUser.get);

        setUserStatus();
        setImage();

    }

    private void addCountries() {
        JFXCountryComboBox country = new JFXCountryComboBox();
        country.addCountries(countryField);
    }

    private void setUserStatus() {
        User currentUser = Session.getInstance().getUser();
        if (currentUser.getStatus() == UserStatus.BUSY)
            userStatus.setFill(Color.RED);
        else if (currentUser.getStatus() == UserStatus.OFFLINE) {
            userStatus.setFill(Color.GREY);
        } else if (currentUser.getStatus() == UserStatus.AWAY)
            userStatus.setFill(Color.YELLOW);
        else
            userStatus.setFill(Color.GREEN);

    }
}
