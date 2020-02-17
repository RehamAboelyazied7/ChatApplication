package com.iti.chat.delegate;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.viewcontroller.ChatRoomController;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ChatRoomDelegate {
    ChatRoomController chatRoomController;
    ClientServiceProvider clientServiceProvider;

    public ChatRoomDelegate(ClientServiceProvider client, ChatRoomController chatRoomController) {
        this.chatRoomController = chatRoomController;
        this.clientServiceProvider = client;
    }

    public void sendMessage(Message message, ChatRoom room) throws RemoteException, NotBoundException {
        clientServiceProvider.sendMessage(message, room);
    }
    public void sendFile(Message message, File file, ChatRoom room) throws IOException, NotBoundException {
        clientServiceProvider.sendFile(message, file, room);
    }
    public void receiveFile(RemoteInputStream remoteInputStream) throws IOException {
        chatRoomController.receiveFile(remoteInputStream);
    }
    public void receiveMessage(Message message) {
        chatRoomController.receiveMessage(message);
    }

    public void requestFileDownload(String remotePath) throws IOException, NotBoundException {
        clientServiceProvider.requestFileDownload(remotePath);
    }
}
