package com.iti.chat.util;

import com.iti.chat.delegate.LoginDelegate;
import com.iti.chat.delegate.RegisterDelegate;
import com.iti.chat.delegate.UserInfoDelegate;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.viewcontroller.HomeController;
import com.iti.chat.viewcontroller.LoginController;
import com.iti.chat.viewcontroller.RegisterController;
import com.iti.chat.viewcontroller.UserProfileController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class SceneTransition {
    static ClientServiceProvider client;
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
            homeController.setModel(client);
            homeController.setStage(stage);
            client.setController(homeController);
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void goToLoginScreen(Stage stage) {
        stage.setTitle("Chat Login");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/LogIn.fxml"));
            Parent parent = loader.load();
            LoginController loginController = loader.getController();
            LoginDelegate delegate = new LoginDelegate(client, loginController);
            loginController.setDelegate(delegate);
            loginController.setStage(stage);
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
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
            stage.setScene(new Scene(parent));
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
            //ChatPageController chatPageView = loader.getController();
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
}
