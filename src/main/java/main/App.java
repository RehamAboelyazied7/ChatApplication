package main;


import com.iti.chat.util.SceneTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * JavaFX main.App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
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
        SceneTransition.goToLoginScreen(stage);

        stage.setMinHeight(700);
        stage.setMinWidth(900);
        //SceneTransition.goToUserProfilerScene(stage);
        //SceneTransition.goToLoginScreen(stage);

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