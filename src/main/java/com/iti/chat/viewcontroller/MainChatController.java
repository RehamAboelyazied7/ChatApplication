package com.iti.chat.viewcontroller;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.model.MessageStyle;
import com.iti.chat.model.MessageType;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainChatController implements Initializable {
    @FXML private JFXButton sendMsgImgBtn;
    @FXML private TextArea msgTxtField;
    @FXML private VBox showMsgsBox;
    @FXML private ImageView attachFileImgBtn;
    public ImageView getAttachFileImgBtn() {
        return attachFileImgBtn;
    }
    @FXML private JFXButton addGroupMembersImgBtn;
    @FXML private Label chatReceiversTxtLabel;
    @FXML private JFXButton saveChatImgBtn;
    @FXML private JFXToggleButton italicButton;
    @FXML private JFXToggleButton boldButton;
    @FXML private JFXComboBox sizeComboBox;
    @FXML private  JFXComboBox fontComboBox;
    @FXML private JFXColorPicker fontColorPicker;

    private String colorPicked;
    private String fontFamily = "Arial";
    private int sizePicked;
    private boolean italic;
    private boolean bold;
    //todo initializ these variables from HomeContrroller
    HomeController homeController;
    ChatRoom chatRoom = new ChatRoom();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatReceiversTxtLabel.setText("myFrined");
        msgTxtField.setWrapText(true);
        msgTxtField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                sendMsgToHomeController();
            }
        });
        fontComboBox.getItems().addAll(Font.getFontNames());
        fontColorPicker.setValue(Color.BLACK);
        colorPicked = toRGBCode(fontColorPicker.getValue());
        fontColorPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
                    Color color = (Color) newVal;
                    System.out.println("Color : " + toRGBCode(color));
                    colorPicked = toRGBCode(color);
                    setTextFieldStyle();
                }
        );

        sizeComboBox.getItems().addAll(IntStream.rangeClosed(8,28).boxed().collect(Collectors.toList()));
        sizeComboBox.setValue(14);
        sizePicked = 14;
        sizeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                    //Country current = (Country) newVal;
                    int size = (int) newVal;
                    System.out.println("Size : " + size);
                    sizePicked = size;
                    setTextFieldStyle();
                }
        );
        fontComboBox.setValue(fontFamily);
        fontComboBox.valueProperty().addListener((observableValue, o, t1) -> {
            String fontName = t1.toString();
            System.out.println(fontName);
            this.fontFamily = fontName;
            setTextFieldStyle();
        });
        boldButton.setOnAction((ActionEvent e) -> {
            if (bold) {
                bold = false;
            } else {
                bold = true;
            }
            System.out.println("Now bold is " + bold);
            setTextFieldStyle();
        });
        italicButton.setOnAction((ActionEvent e) -> {
            if (italic) {
                italic = false;
            } else {
                italic = true;
            }
            System.out.println("Now italic is " + italic);
            setTextFieldStyle();
        });
        setTextFieldStyle();
    }
    public String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
    public FontWeight getFontWeight() {
        if (bold) {
            return FontWeight.BOLD;
        } else {
            return FontWeight.LIGHT;
        }
    }
    public FontPosture getFontPosture() {
        if (italic) {
            return FontPosture.ITALIC;
        } else {
            return FontPosture.REGULAR;
        }
    }
    public void setTextFieldStyle() {
        System.out.println("s;geod");
        String str = msgTxtField.getText().toString();
        msgTxtField.setText("");
        msgTxtField.setStyle("-fx-font-family: \""+ fontFamily + "\"; "+"-fx-text-fill: " + colorPicked + ";" + "-fx-font-size: " + sizePicked + ";" + " -fx-font-weight:" + getFontWeight().name() + ";" + " -fx-font-style:" + getFontPosture().name());
        msgTxtField.setText(str);
    }
    public ChatRoom getChatRoom() {
        return chatRoom;
    }
    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
    public void sendMessage(ActionEvent actionEvent) {
        sendMsgToHomeController();
    }

    private void sendMsgToHomeController(){
        MessageStyle style = new MessageStyle();
        style.setFontFamily(fontFamily);
        style.setColor(colorPicked);
        style.setSize(sizePicked);
        style.setFontWeight(getFontWeight().name());
        style.setFontPosture(getFontPosture().name());
        Message newMsg = new Message();
        newMsg.setStyle(style);
//        newMsg.setMsg(msgTxtField.getText());
//        newMsg.setType(MessageType.USER);
//        newMsg.setFontFamily(fontFamily);
//        newMsg.setTextFill(colorPicked);
//        newMsg.setFontSize(sizePicked);
//        newMsg.setUser(mUser);
//        newMsg.setFontWeight(getFontWeight().name());
//        homeController.sendMsg(newMsg , chatRoom);
        msgTxtField.setText("");
    }
//    public void displayMessagesFromArrList(){
//        for (Message message : chatRoom.getChatRoomMessage()) {
//            if(message.getUser().getId() == mUser.getId()){
//                // todo set allignment to right and displayMsg
//            }else{
//                // todo set alignment to left nad display message
//            }
//        }
//    }
//
//    private void displayMsg(Message msg /* todo here is the Alignment type*/){
//        // todo display msg with specified alignment
//    }
}