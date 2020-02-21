package com.iti.chat.util;

import com.iti.chat.delegate.ChatRoomDelegate;
import com.iti.chat.delegate.LoginDelegate;
import com.iti.chat.delegate.RegisterDelegate;
import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;
import com.iti.chat.service.ClientServiceProvider;
import static com.iti.chat.util.Encryption.decrypt;
import com.iti.chat.viewcontroller.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            System.out.println("here 3");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/home.fxml"));
            Parent parent = loader.load();
            HomeController homeController = loader.getController();
            client.setController(homeController);
            client.setUser(Session.getInstance().getUser());
            homeController.setModel(client);
            homeController.setStage(stage);
            stage.setScene(new Scene(parent, stage.getWidth(), stage.getHeight()));
        } catch (IOException | NotBoundException e) {
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

    public static ChatRoomController loadChatRoom(VBox rightVBox, ChatRoom room) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/Chat_Room.fxml"));
            Parent parent = loader.load();
            ChatRoomController chatRoomController = loader.getController();
            chatRoomController.getContactBarController().getNameLabel().setText(room.getName());
            ChatRoomDelegate delegate = new ChatRoomDelegate(client, chatRoomController);
            client.setChatRoomDelegate(delegate);
            chatRoomController.setDelegate(delegate);
            chatRoomController.setCurrentChatRoom(room);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goToLoginScreen(Stage stage) throws IOException, RemoteException, SQLException, NotBoundException {
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
                pass = decrypt(reader.readLine());
                reader.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            User user = delegate.login(phone, pass);
            if (user != null) {
                Session.getInstance().setUser(user);
                SceneTransition.goToHomeScene(stage);
            }
        }
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

    public static void goToChatScene(Stage stage) {
        stage.setTitle("Chat");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/ChatPageWebView.fxml"));
            Parent parent = loader.load();
            ChatPageController chatPageView = loader.getController();
            //chatPageView.displayNotification();
            //ServiceProviderController controller = new ServiceProviderController();
            //chatPageView.setController(controller);
            //controller.setView(chatPageView);
            // parent.autosize();
            stage.setScene(new Scene(parent));
            //stage.setMinWidth(200);
            //stage.setMinHeight(100);

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goToNotification(Stage stage) {
        stage.setTitle("Notification");
        NotificationListController notificationListController = new NotificationListController();
        Scene scene = new Scene(notificationListController.addList(), 500, 500);
        stage.setScene(scene);
        stage.setMinWidth(200);
        stage.setMinHeight(100);
    }

}
