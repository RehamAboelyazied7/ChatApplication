package com.iti.chat.util;

import com.iti.chat.controller.ServiceProviderController;
import com.iti.chat.viewcontroller.ChatPageController;
import com.iti.chat.viewcontroller.HomeController;
import com.iti.chat.viewcontroller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SceneTransition {
    public static void goToHomeScene(Stage stage) {
        stage.setMinWidth(500);
        stage.setMinHeight(300);
        stage.setTitle("Chat");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/home.fxml"));
            Parent parent = loader.load();
            HomeController homeController = loader.getController();
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goToLoginScreen(Stage stage) {
        stage.setTitle("Chat Login");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/Login.fxml"));
            Parent parent = loader.load();
            LoginController loginController = loader.getController();
            loginController.setStage(stage);
            //loginController.setUserDAO(new UserDAOImpl());
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goToRegisterScene(Stage stage) {
        stage.setTitle("Register");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/FXMLDocument.fxml"));
            Parent parent = loader.load();
            stage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void goToChatScene(Stage stage) {
        stage.setTitle("Chat");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneTransition.class.getResource("/view/ChatPage.fxml"));
            Parent parent = loader.load();
            ChatPageController chatPageView = loader.getController();
            ServiceProviderController controller = new ServiceProviderController();
            chatPageView.setController(controller);
            controller.setView(chatPageView);
           // parent.autosize();
            stage.setScene(new Scene(parent));
            stage.setMinWidth(200);
            stage.setMinHeight(100);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException ex) {
            Logger.getLogger(SceneTransition.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
