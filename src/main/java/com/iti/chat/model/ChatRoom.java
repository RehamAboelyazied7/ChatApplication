package com.iti.chat.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ChatRoomType" , propOrder = {
        "name",
        "users",
        "messages"
})
public class ChatRoom implements Serializable{
    private int id;

    @XmlElement(name = "User")
    private List<User> users;

    @XmlElement(name = "Message")
    private List<Message> messages;

    @XmlElement(name = "Name")
    private String name;

    static int counter = 1;

    public ChatRoom() {
        id = counter++;
        users = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public List<Message> getMessages() {

        return messages;
    }

    public void setMessages(List<Message> messages) {

        this.messages = messages;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public List<User> getUsers() {

        return users;
    }

    public void addUser(User user) {
        users.add(user);
//        user.getChatRooms().add(this);
    }

    public void setUsers(List<User> users) {

        this.users = users;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof ChatRoom) {
            ChatRoom chatRoom = (ChatRoom) object;
            return users.containsAll(chatRoom.getUsers()) && chatRoom.getUsers().containsAll(users);
        }
        return false;
    }

}
