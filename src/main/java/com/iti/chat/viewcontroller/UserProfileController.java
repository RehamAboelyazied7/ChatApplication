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
    File selectedImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        //UserStatus.statusToString(0)
         status_combo_box.getItems().addAll(UserStatus.statusToString(1),UserStatus.statusToString(2),UserStatus.statusToString(3));
        status_combo_box.setPromptText("Change Status");

        /* if(changeStatus==false) {
             status_combo_box.getSelectionModel().select(saveStatus);
         }else{
             status_combo_box.getSelectionModel().select(saveStatus);
         }

         */
     /*    int  selectedIndex = status_combo_box.getSelectionModel().getSelectedIndex();
        status_combo_box.getItems().addAll(UserStatus.statusToString(0), UserStatus.statusToString(1), UserStatus.statusToString(2), UserStatus.statusToString(3));
        status_combo_box.getSelectionModel().select(3);
        status_combo_box.getSelectionModel().select(selectedIndex);
        
      */


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
        status_combo_box.setOnAction(e->{
            switch (status_combo_box.getValue()){
               /* case "offline":
=======
        status_combo_box.setOnAction(e -> {
            switch (status_combo_box.getValue()) {
                case "offline":
>>>>>>> 1d5036286905fbdd83dbe213b2d18136410f236e
                    Session.getInstance().getUser().setStatus(UserStatus.OFFLINE);
                    saveStatus=UserStatus.OFFLINE;
                    break;

                */
               
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
            try {
                delegate.getClient().updateUserInfo(Session.getInstance().getUser());
                //delegate.getClient().sessionService.userInfoDidChange(currentUser);
                setUserStatus();
            } catch (RemoteException ex) {
                ex.printStackTrace();

            }


        });

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
        String firstName = new String();
        String lastName = new String();
        boolean validData = true;
        if (name.trim().length() == 0) {
            nameWarning.setText("Invalid");
            validData = false;

        }

        if (validation.checkEmail(email) == RegisterValidation.INVALID) {
            emailWarning.setText("Invalid Email");
            validData = false;
        }

        if (validation.checkPhone(phone) == RegisterValidation.INVALID) {
            phoneWarning.setText("Invalid number");
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
            User currentUser = Session.getInstance().getUser();
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
        User currentUser = Session.getInstance().getUser();
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
        User currentUser = Session.getInstance().getUser();
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
        nameField.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        bioField.setText(currentUser.getBio());
        phoneField.setText(currentUser.getPhone());
        emailField.setText(currentUser.getEmail());
        countryField.setValue(currentUser.getCountry());
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
