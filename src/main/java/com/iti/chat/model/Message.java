package com.iti.chat.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "MessageType" , propOrder = {
        "sender",
        "content",
        "style"
})
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Content")
    private String content;
    private int messageType;

    @XmlElement(name = "Sender")
    private User sender;
    private String remotePath;
    private ChatRoom chatRoom;

    @XmlElement(name = "Style")
    private MessageStyle style;

    public Message() {
        messageType = MessageType.TEXT_MESSAGE;
    }

    public Message(String content, User sender, int messageType) {
        this.content = content;
        this.sender = sender;
        this.messageType = messageType;
    }

    public Message(String content, User sender) {
        this.content = content;
        this.sender = sender;
        this.messageType = MessageType.TEXT_MESSAGE;
    }

    public MessageStyle getStyle() {
        return style;
    }

    public void setStyle(MessageStyle style) {
        this.style = style;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public int getMessageType() {

        return messageType;
    }

    public void setMessageType(int messageType) {

        this.messageType = messageType;
    }

    public User getSender() {

        return sender;
    }

    public void setSender(User sender) {

        this.sender = sender;
    }

    public ChatRoom getChatRoom() {

        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {

        this.chatRoom = chatRoom;
    }
}
