package com.iti.chat.service;

import com.iti.chat.controller.ServiceProviderController;
import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.model.Notification;
import com.iti.chat.model.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientServiceProvider extends UnicastRemoteObject implements ClientService {
    public User currentUser;
    ServiceProviderController controller;
    
    public ClientServiceProvider(ServiceProviderController controller) throws RemoteException {
        this.controller = controller;
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
        controller.sendMessage(message, chatRoom);
    }

    @Override
    public void receiveMessage(Message message, ChatRoom chatRoom) {
        controller.receiveMessage(message, chatRoom);
    }
    
    @Override
    public void receiveNotification(Notification notification) {
       controller.receiveNotification(notification);
    }
}
