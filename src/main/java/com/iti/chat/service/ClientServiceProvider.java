package com.iti.chat.service;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.model.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientServiceProvider extends UnicastRemoteObject implements ClientService {
    public User currentUser;

    public ClientServiceProvider() throws RemoteException {

    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public void setUser(User user) {
        currentUser = user;
    }

    @Override
    public void sendMessage(Message message, ChatRoom chatRoom) {

    }

    @Override
    public void receiveMessage(Message message, ChatRoom chatRoom) {

    }
}
