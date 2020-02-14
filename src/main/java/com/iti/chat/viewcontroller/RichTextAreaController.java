package com.iti.chat.viewcontroller;

import com.iti.chat.model.Message;
import com.iti.chat.model.MessageStyle;
import com.iti.chat.util.RGBConverter;
import com.iti.chat.util.Session;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RichTextAreaController implements Initializable {

    @FXML
    JFXToggleButton boldButton;
    @FXML
    JFXToggleButton italicButton;
    @FXML
    JFXComboBox sizeComboBox;
    @FXML
    JFXColorPicker fontColorPicker;
    @FXML
    JFXComboBox fontComboBox;
    @FXML
    TextArea msgTxtField;
    @FXML
    JFXButton sendButton;

    private boolean bold;
    private boolean italic;

    MessageStyle style;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        style = new MessageStyle();
        fontComboBox.getItems().addAll(Font.getFontNames());
        sizeComboBox.getItems().addAll(IntStream.rangeClosed(8, 28).boxed().collect(Collectors.toList()));
        fontColorPicker.setValue(Color.BLACK);
        sizeComboBox.setValue(15);
        fontComboBox.setValue("Arial");
        style.setSize(15);
        setHandlers();
    }

    public Message getMessage() {
        Message message = new Message();
        message.setStyle(style);
        message.setContent(msgTxtField.getText());
        message.setSender(Session.getInstance().getUser());
        return message;
    }

    public void clearText() {
        msgTxtField.clear();
    }

    private void setHandlers() {
        fontColorPicker.valueProperty().addListener((observableValue, color, t1) -> {
            Color col = (Color) t1;
            String rgb = RGBConverter.toRGB(col);
            style.setColor(rgb);
            applyStyle();
        });
        sizeComboBox.valueProperty().addListener((observableValue, o, t1) -> {
            int size = (int) t1;
            style.setSize(size);
            applyStyle();
        });
        fontComboBox.valueProperty().addListener((observableValue, o, t1) -> {
            String fontFamily = (String) t1;
            style.setFontFamily(fontFamily);
            applyStyle();
        });
        italicButton.setOnAction(ae -> {
            italic = !italic;
            if (italic) {
                style.setFontPosture(FontPosture.ITALIC.name());
            } else {
                style.setFontPosture(FontPosture.REGULAR.name());
            }
            applyStyle();

        });
        boldButton.setOnAction(ae -> {
            bold = !bold;
            if (bold) {
                style.setFontWeight(FontWeight.BOLD.name());
            } else {
                style.setFontWeight(FontWeight.LIGHT.name());
            }
            applyStyle();
        });

    }

    private void applyStyle() {
        msgTxtField.setStyle(style.toString());
    }
}
