package com.iti.chat.service;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.User;

import java.util.ArrayList;
import java.util.List;

public class Session {
    public User currentUser;
    public List<ChatRoom> chatRooms;

    public Session(User user) {
        currentUser = user;
        chatRooms = new ArrayList<>();
    }

    public User getCurrentUser() {

        return currentUser;
    }

    public void setCurrentUser(User currentUser) {

        this.currentUser = currentUser;
    }

    public List<ChatRoom> getChatRooms() {

        return chatRooms;
    }

    public void setChatRooms(List<ChatRoom> chatRooms) {

        this.chatRooms = chatRooms;
    }


    public void logout() {
        if(currentUser != null) {
            currentUser = null;
        }
    }

    public void didLogin(User user) {
        currentUser = user;
        chatRooms = new ArrayList<>();
    }
}
