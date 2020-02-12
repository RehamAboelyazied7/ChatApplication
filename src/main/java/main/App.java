package main;

import com.iti.chat.service.ChatRoomService;
import com.iti.chat.service.SessionService;
import com.iti.chat.util.SceneTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * JavaFX main.App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setMinHeight(700);
        stage.setMinWidth(900);
        //SceneTransition.goToHomeScene(stage);
        //SceneTransition.goToUserProfilerScene(stage);
        SceneTransition.goToLoginScreen(stage);
        //SceneTransition.goToHomeScene(stage);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}