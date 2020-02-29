package com.iti.chat.delegate;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.model.User;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.controller.ChatRoomController;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class ChatRoomDelegate {
    ChatRoomController chatRoomController;
    ClientServiceProvider clientServiceProvider;

    public ChatRoomDelegate(ClientServiceProvider client, ChatRoomController chatRoomController) {
        this.chatRoomController = chatRoomController;
        this.clientServiceProvider = client;
    }

    public void sendMessage(Message message, int roomId) throws RemoteException, NotBoundException {
        clientServiceProvider.sendMessage(message, roomId);
    }
    public void sendFile(Message message, File file, int roomId) throws IOException, NotBoundException {
        clientServiceProvider.sendFile(message, file, roomId);
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



    public ChatRoom createNewChatRoom(List<User> users) throws RemoteException, NotBoundException {
        return clientServiceProvider.createNewChatRoom(users);
    }

    public ChatRoom getChatRoom(int roomId) throws RemoteException, NotBoundException {
        return clientServiceProvider.getChatRoom(roomId);
    }


}
