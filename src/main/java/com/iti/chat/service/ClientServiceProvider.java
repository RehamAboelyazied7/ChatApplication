package com.iti.chat.service;

import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.model.Notification;
import com.iti.chat.model.User;
import com.iti.chat.viewcontroller.HomeController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class ClientServiceProvider extends UnicastRemoteObject implements ClientService {
    public User currentUser;
    HomeController controller;
    FriendRequestsService friendRequestsService;
    ChatRoomService chatRoomService;
    SessionService sessionService;

    public ClientServiceProvider() throws RemoteException {
        Registry registry = LocateRegistry.getRegistry(4000);
        try {
            friendRequestsService = (FriendRequestsService) registry.lookup(ServerServices.FRIEND_REQUEST_SERVICE);
            sessionService = (SessionService) registry.lookup(ServerServices.SESSION_SERVICE);
            chatRoomService = (ChatRoomService) registry.lookup(ServerServices.CHAT_ROOM_SERVICE);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
    
    public ClientServiceProvider(HomeController controller) throws RemoteException {
        this();
        this.controller = controller;
    }

    public void setController(HomeController controller) {

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
    public void sendMessage(Message message, ChatRoom room) throws RemoteException {
        chatRoomService.sendMessage(message, room);
    }

    @Override
    public void receiveMessage(Message message) {
        controller.receiveMessage(message);
    }

    public ChatRoom createNewChatRoom(List<User> users) throws RemoteException {
        return chatRoomService.createNewChatRoom(users);
    }
    
    @Override
    public void receiveNotification(Notification notification) {

        controller.receiveNotification(notification);
    }

    public void sendFriendRequest(User receiver) throws RemoteException {
        friendRequestsService.sendFriendRequest(this, receiver);
    }

    public void acceptFriendRequest(User sender) throws RemoteException {
        friendRequestsService.acceptFriendRequest(this, sender);
    }
    public void rejectFriendRequest(User sender) throws RemoteException {
        friendRequestsService.rejectFriendRequest(this, sender);
    }

    public List<User> pendingFriendRequests() throws RemoteException {
        return friendRequestsService.pendingFriendRequests(this);
    }

    public User login(String phone, String password) throws RemoteException, SQLException {
        return sessionService.login(phone, password, this);
    }


}
