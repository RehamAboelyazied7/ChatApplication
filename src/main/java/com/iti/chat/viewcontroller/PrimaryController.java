package com.iti.chat.viewcontroller;

import java.io.IOException;

import main.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
