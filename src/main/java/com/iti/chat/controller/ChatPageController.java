package com.iti.chat.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class ChatPageController {
    @FXML
    public BorderPane chatBorderPane;
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
}
