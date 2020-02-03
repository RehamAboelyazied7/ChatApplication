package com.iti.chat.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import main.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}