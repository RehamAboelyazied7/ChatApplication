package com.iti.chat.viewcontroller;

import com.iti.chat.model.Message;
import com.iti.chat.model.MessageStyle;
import com.iti.chat.util.ColorUtils;
import com.iti.chat.util.Session;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RichTextAreaController implements Initializable {

    @FXML
    private GridPane motherGridPane;

    @FXML
    private JFXToggleButton boldButton;
    @FXML
    private JFXToggleButton italicButton;
    @FXML
    private JFXComboBox sizeComboBox;
    @FXML
    private JFXColorPicker fontColorPicker;
    @FXML
    private JFXComboBox fontComboBox;
    @FXML
    private TextArea msgTxtField;
    @FXML
    private JFXButton sendButton;
    @FXML
    private JFXButton paperClipButton;
    @FXML
    private ImageView paperClipImageView;

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


        paperClipButton.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());

            if (file != null) {

                paperClipImageView.setVisible(false);
                JFXSpinner spinner = new JFXSpinner();
                spinner.setRadius(7);
                paperClipButton.setGraphic(spinner);
            }

        });
    }

    public Message getMessage() {
        Message message = new Message();
        message.setStyle(style);
        message.setContent(msgTxtField.getText());
        message.setSender(Session.getInstance().getUser());
        return message;
    }

    public void diff(Color color1, Color color2) {
        double red = Math.abs(color1.getRed() - color2.getRed()) * 255;
        double green = Math.abs(color1.getGreen() - color2.getGreen()) * 255;
        double blue = Math.abs(color1.getBlue() - color2.getBlue()) * 255;
        System.out.println("diff is " + (red + green + blue));
    }

    public void clearText() {
        msgTxtField.clear();
    }

    private void setHandlers() {
        fontColorPicker.valueProperty().addListener((observableValue, color, t1) -> {
            Color col = (Color) t1;
            Color old = Color.web(style.getColor());
            diff(old, col);
            String rgb = ColorUtils.toRGB(col);
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

    public JFXToggleButton getBoldButton() {
        return boldButton;
    }

    public void setBoldButton(JFXToggleButton boldButton) {
        this.boldButton = boldButton;
    }

    public JFXToggleButton getItalicButton() {
        return italicButton;
    }

    public void setItalicButton(JFXToggleButton italicButton) {
        this.italicButton = italicButton;
    }

    public JFXComboBox getSizeComboBox() {
        return sizeComboBox;
    }

    public void setSizeComboBox(JFXComboBox sizeComboBox) {
        this.sizeComboBox = sizeComboBox;
    }

    public JFXColorPicker getFontColorPicker() {
        return fontColorPicker;
    }

    public void setFontColorPicker(JFXColorPicker fontColorPicker) {
        this.fontColorPicker = fontColorPicker;
    }

    public JFXComboBox getFontComboBox() {
        return fontComboBox;
    }

    public void setFontComboBox(JFXComboBox fontComboBox) {
        this.fontComboBox = fontComboBox;
    }

    public TextArea getMsgTxtField() {
        return msgTxtField;
    }

    public void setMsgTxtField(TextArea msgTxtField) {
        this.msgTxtField = msgTxtField;
    }

    public JFXButton getSendButton() {
        return sendButton;
    }

    public void setSendButton(JFXButton sendButton) {
        this.sendButton = sendButton;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public GridPane getMotherGridPane() {
        return motherGridPane;
    }

    public void setMotherGridPane(GridPane motherGridPane) {
        this.motherGridPane = motherGridPane;
    }
}
