/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.chat.controller;

import com.iti.chat.viewcontroller.ChatPageController;
import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.model.Notification;
import com.iti.chat.service.ChatRoomService;
import com.iti.chat.service.ClientServiceProvider;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shaimaa Saied
 */
public class ServiceProviderController {
    ChatPageController view;
    ClientServiceProvider model;
    
    
    ChatRoomService chatRoomserverRef;
    Registry reg;
    public ServiceProviderController() throws RemoteException, NotBoundException {
        model = new ClientServiceProvider(this);
        reg = LocateRegistry.getRegistry(4000);
        chatRoomserverRef = (ChatRoomService) reg.lookup("chatRoomService");
        System.out.println(chatRoomserverRef);        
        System.out.println("Constructor initialised");        
//chatRoomserverRef.sendMessage(new Message(), new ChatRoom());
        
    }
 
    public void setView(ChatPageController view) {
        this.view = view;
    }
    
    public void sendMessage(Message message, ChatRoom chatRoom) { try {
        //registry
        chatRoomserverRef.sendMessage(new Message(), new ChatRoom());
        } catch (RemoteException ex) {
            Logger.getLogger(ServiceProviderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void receiveMessage(Message message, ChatRoom chatRoom) {
        view.displayMessage(message);
    }
    
    public void receiveNotification(Notification notification) {
        view.displayNotification(notification);
    }
    
    
    
    
}
