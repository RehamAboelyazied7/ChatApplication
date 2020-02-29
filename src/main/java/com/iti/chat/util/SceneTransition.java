package com.iti.chat.util;

import com.iti.chat.delegate.*;
import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;
import com.iti.chat.model.UserStatus;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.controller.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.iti.chat.util.Encryption.decrypt;
import static com.iti.chat.util.Hashing.getSecurePassword;

public class SceneTransition {

    public static ClientServiceProvider client;

    static {

        try {
            client = new ClientServiceProvider();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public static void goToHomeScene(Stage stage) {
        stage.setTitle("Chat");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/home.fxml"));
            Parent parent = loader.load();
            HomeController homeController = loader.getController();
            client.setController(homeController);
            client.setUser(Session.getInstance().getUser());
            homeController.setClient(client);
            homeController.instantiateContactsList();
            homeController.setStage(stage);
            stage.setScene(new Scene(parent, stage.getWidth(), stage.getHeight()));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    closeStage(stage);
                    System.exit(0);

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeStage(Stage stage) {
        try {
            if (client.getUser().getStatus() != UserStatus.OFFLINE) {
                client.sessionService.logout(client.getUser());
            }
            stage.onCloseRequestProperty();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void loadProfileScene(VBox rightVBox) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/UserProfile.fxml"));
            Parent parent = loader.load();
            UserProfileController userProfileController = loader.getController();
            UserInfoDelegate userInfoDelegate = new UserInfoDelegate(client, userProfileController);
            userProfileController.setDelegate(userInfoDelegate);
            rightVBox.getChildren().clear();
            rightVBox.getChildren().add(parent);
            VBox.setVgrow(parent, Priority.ALWAYS);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ChatRoomController loadChatRoom(VBox rightVBox, ChatRoom room, HomeController homeController) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/Chat_Room.fxml"));
            Parent parent = loader.load();
            ChatRoomController chatRoomController = loader.getController();
            chatRoomController.setHomeController(homeController);
            chatRoomController.getContactBarController().getNameLabel().setText(room.getName());
            chatRoomController.getContactBarController().setChatRoomController(chatRoomController);
            ChatRoomDelegate delegate = new ChatRoomDelegate(client, chatRoomController);
            client.setChatRoomDelegate(delegate);
            chatRoomController.setDelegate(delegate);
            //chatRoomController.setCurrentChatRoom(room);
            rightVBox.getChildren().clear();
            rightVBox.getChildren().add(parent);
            VBox.setVgrow(parent, Priority.ALWAYS);
            return chatRoomController;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void mainChatScene(Stage stage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneTransition.class.getResource("/view/rich_text_area.fxml"));
        try {
            Parent parent = loader.load();
            stage.setScene(new Scene(parent));
            stage.setScene(new Scene(parent, stage.getWidth(), stage.getHeight()));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    closeStage(stage);
                    System.exit(0);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goToLoginScreen(Stage stage) throws IOException, SQLException, NotBoundException {
        String phone = null;
        String pass = null;

        stage.setTitle("Chat Login");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneTransition.class.getResource("/view/LogIn.fxml"));
        Parent parent = loader.load();
        LoginController loginController = loader.getController();
        LoginDelegate delegate = new LoginDelegate(client, loginController);
        loginController.setStage(stage);
        loginController.setDelegate(delegate);

        stage.setScene(new Scene(parent, stage.getWidth(), stage.getHeight()));
        File file1 = new File("userAuthenticationInfo.txt");
        if ((!(file1.length() == 0)) && Session.getInstance().getUser() == null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file1));
                phone = decrypt(reader.readLine());
                pass = getSecurePassword(decrypt(reader.readLine()));
                reader.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            User user = delegate.login(phone, pass);
            if (user != null) {
                System.out.println("logged in");
                Session.getInstance().setUser(user);
                SceneTransition.goToHomeScene(stage);
            }
        }
/*        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                closeStage(stage);
            }
        });

 */
    }


    public static void goToRegisterScene(Stage stage) {
        stage.setTitle("Register");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/Register.fxml"));
            Parent parent = loader.load();
            RegisterController registerController = loader.getController();
            RegisterDelegate delegate = new RegisterDelegate(client, registerController);
            registerController.setDelegate(delegate);
            registerController.setStage(stage);
            stage.setScene(new Scene(parent, stage.getWidth(), stage.getHeight()));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void goToUserProfilerScene(Stage stage) {
        stage.setTitle("Profile");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/UserProfile.fxml"));
            Parent parent = loader.load();
            UserProfileController userProfileController = loader.getController();
            UserInfoDelegate userInfoDelegate = new UserInfoDelegate(client, userProfileController);
            userProfileController.setDelegate(userInfoDelegate);
            userProfileController.setStage(stage);
            stage.setScene(new Scene(parent));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    closeStage(stage);
                    System.exit(0);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void goToChangePasswordScene(Stage stage) {
        stage.setTitle("Change password");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/changePassword.fxml"));
            Parent parent = loader.load();
            ChangePasswordController changePasswordController = loader.getController();
            changePasswordController.setStage(stage);
            UserPasswordDelegate userPasswordDelegate = new UserPasswordDelegate(client, changePasswordController);
            changePasswordController.setPasswordDelegate(userPasswordDelegate);
            stage.setScene(new Scene(parent, stage.getWidth(), stage.getHeight()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  /*  public static void goToNotification(Stage stage) {
        stage.setTitle("Notification");
        NotificationListController notificationListController = new NotificationListController();
        Scene scene = new Scene(notificationListController.addList(), 500, 500);
        stage.setScene(scene);
        stage.setMinWidth(200);
        stage.setMinHeight(100);
    }


   */


}
