package com.iti.chat.viewcontroller;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {

    @FXML
    public AnchorPane chatBorderPane;
    @FXML
    public VBox chatVBox;
    @FXML
    public HBox chatHBox;
    @FXML
    public StackPane ImageStackPane;
    @FXML
    public Circle CircleImageView;
    @FXML
    public ImageView userImage;
    @FXML
    public VBox messageVbox;
    @FXML
    public Label userNameLabel;

    @FXML
    VBox leftMessageVbox;

    @FXML
    StackPane webViewStackPane;

    @FXML
    WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatVBox.maxWidthProperty().bind(chatBorderPane.widthProperty().multiply(0.8));
        //webView.maxHeightProperty().bind(chatVBox.heightProperty());
        displayOnLeft();
        WebViewFitContent content = new WebViewFitContent("<h1 style=\"color: #5e9ca0;\">You can edit You can edit You can <span style=\"color: #2b2301;\">this demo</span> text!<strong>&nbsp;</strong></h1>\n" +
                "<h1 style=\"color: #5e9ca0;\">You can edit <span style=\"color: #2b2301;\">this demo</span> text!<strong>&nbsp;</strong></h1>\n" +
                "<h1 style=\"color: #5e9ca0;\">You can edit <span style=\"color: #2b2301;\">this demo</span> text!<strong>&nbsp;</strong></h1>\n" +
                "<h1 style=\"color: #5e9ca0;\">You can edit <span style=\"color: #2b2301;\">this demo</span> text!</h1>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><strong>&nbsp;</strong></p>");
        leftMessageVbox.getChildren().add(content);
        chatVBox.minWidthProperty().bind(chatBorderPane.widthProperty().multiply(0.5));
        //displayOnRight();
        ChatterBotFactory factory = new ChatterBotFactory();
        try {
            ChatterBot bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            ChatterBotSession bot2session = bot2.createSession();
            System.out.println(bot2session.think("gfhfhgfgfhgfh"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayOnLeft() {
        AnchorPane.clearConstraints(chatVBox);
        AnchorPane.setLeftAnchor(chatVBox, 10.0);
        AnchorPane.setTopAnchor(chatVBox, 10.0);
        chatBorderPane.requestLayout();
    }

    public void displayOnRight() {
        AnchorPane.clearConstraints(chatVBox);
        AnchorPane.setTopAnchor(chatVBox, 10.0);
        AnchorPane.setRightAnchor(chatVBox, 50.0);
        ImageStackPane.setVisible(false);
        chatBorderPane.requestLayout();
    }
}
