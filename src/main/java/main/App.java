package main;

import com.iti.chat.model.User;
import com.iti.chat.service.ChatRoomService;
import com.iti.chat.service.SessionService;
import com.iti.chat.util.SceneTransition;
import com.iti.chat.util.Session;
import com.iti.chat.viewcontroller.HomeController;
import com.iti.chat.viewcontroller.LoginController;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * JavaFX main.App
 */
public class App extends Application {

    public static Scene scene;
    @Override
    public void start(Stage stage) throws IOException, RemoteException, SQLException, NotBoundException {
        stage.setMinHeight(700);
        stage.setMinWidth(900);
        //   SceneTransition.goToLoginScreen(stage);
//    SceneTransition.goToNotification(stage);
//        stage.setOnCloseRequest(event -> {
//
//            System.exit(0);
//
//        });
        //SceneTransition.goToChatScene(stage);
        //SceneTransition.goToLoginScreen(stage);

        stage.setMinHeight(700);
        stage.setMinWidth(900);
        //SceneTransition.goToUserProfilerScene(stage);
        //SceneTransition.goToLoginScreen(stage)
        SceneTransition.goToLoginScreen(stage);

        stage.show();
        
        stage.setOnCloseRequest(windowEvent -> {
            System.exit(0);
        });
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

    }

    public static void main(String[] args) {
        launch();
    }

}
