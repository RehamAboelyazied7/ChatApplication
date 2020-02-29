package main;

import com.iti.chat.util.SceneTransition;
import javafx.application.Application;
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
