package main;

import com.iti.chat.util.SceneTransition;
import javafx.application.Application;
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

//        scene = new Scene(loadFXML("/view/home"));
//        stage.setScene(scene);
//        stage.show();
        SceneTransition.goToChatScene(stage);
        SceneTransition.goToLoginScreen(stage);
        //SceneTransition.goToHomeScene(stage);

        stage.setResizable(false);
        SceneTransition.goToRegisterScene(stage);

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