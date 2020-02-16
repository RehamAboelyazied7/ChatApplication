package com.iti.chat.service;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import com.iti.chat.model.ChatRoom;
import com.iti.chat.model.Message;
import com.iti.chat.model.Notification;
import com.iti.chat.model.User;
import com.iti.chat.viewcontroller.HomeController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class ClientServiceProvider extends UnicastRemoteObject implements ClientService {
    private User currentUser;
    HomeController controller;
    FriendRequestsService friendRequestsService;
    ChatRoomService chatRoomService;
    SessionService sessionService;
    FileTransferService fileTransferService;
    Registry registry;

    public ClientServiceProvider() throws RemoteException {
        registry = LocateRegistry.getRegistry(4000);
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
    public void sendMessage(Message message, ChatRoom room) throws RemoteException, NotBoundException {
        initChatRoomService();
        chatRoomService.sendMessage(message, room);
    }

    public void sendFile(Message message, File file, ChatRoom room) throws IOException, NotBoundException {
        initChatRoomService();
        InputStream inputStream = new FileInputStream(file.getAbsolutePath());
        RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inputStream);
        chatRoomService.sendFile(message, remoteFileData);
        inputStream.close();
    }

    public void downloadFile(RemoteInputStream remoteInputStream) throws IOException {
        controller.receiveFile(remoteInputStream);
    }
    public void downloadImage(RemoteInputStream remoteInputStream) throws IOException {
        controller.receiveFile(remoteInputStream);
    }

    public void requestFileDownload(String remotePath) throws IOException, NotBoundException {
        initFileTransferService();
        fileTransferService.downloadFile(remotePath, this);
    }


    public void requestImageDownload(String remotePath) throws IOException, NotBoundException {
        initFileTransferService();
        fileTransferService.downloadImage(remotePath, this);
    }

    @Override
    public void receiveMessage(Message message) {
        controller.receiveMessage(message);
    }

    public ChatRoom createNewChatRoom(List<User> users) throws RemoteException, NotBoundException {
        initChatRoomService();
        return chatRoomService.createNewChatRoom(users);
    }

    @Override
    public void receiveNotification(Notification notification) {

        controller.receiveNotification(notification);
    }

    private void initFriendRequestService() throws RemoteException, NotBoundException {
        if (friendRequestsService == null) {
            friendRequestsService = (FriendRequestsService) registry.lookup(ServerServices.FRIEND_REQUEST_SERVICE);
        }
    }

    private void initSessionService() throws RemoteException, NotBoundException {
        if (sessionService == null) {
            sessionService = (SessionService) registry.lookup(ServerServices.SESSION_SERVICE);
        }
    }

    private void initChatRoomService() throws RemoteException, NotBoundException {
        if (chatRoomService == null) {
            chatRoomService = (ChatRoomService) registry.lookup(ServerServices.CHAT_ROOM_SERVICE);
        }
    }

    private void initFileTransferService() throws RemoteException, NotBoundException {
        if(fileTransferService == null) {
            fileTransferService = (FileTransferService) registry.lookup(ServerServices.FILE_TRANSFER_SERVICE);
        }
    }

    public void sendFriendRequest(User receiver) throws RemoteException, NotBoundException {
        initFriendRequestService();
        friendRequestsService.sendFriendRequest(this, receiver);
    }

    public void acceptFriendRequest(User sender) throws RemoteException, NotBoundException {
        initFriendRequestService();
        friendRequestsService.acceptFriendRequest(this, sender);
    }

    public void rejectFriendRequest(User sender) throws RemoteException, NotBoundException {
        initFriendRequestService();
        friendRequestsService.rejectFriendRequest(this, sender);
    }

    public List<User> pendingFriendRequests() throws RemoteException, NotBoundException {
        initFriendRequestService();
        return friendRequestsService.pendingFriendRequests(this);
    }

    public User login(String phone, String password) throws RemoteException, SQLException, NotBoundException {
        initSessionService();
        return sessionService.login(phone, password, this);
    }

    public void register(User user, String password) throws RemoteException, NotBoundException, SQLException {
        initSessionService();
        sessionService.register(user, password);
    }

    public void updateUserInfo(User user) throws RemoteException {
        sessionService.updateInfo(user);
    }

    @Override
    public void updateUserPassword(User user) throws RemoteException, SQLException {
        sessionService.updateUserPassword(user);
    }

    public void didSendNBytes(long n) throws RemoteException {
        controller.didSendNBytes(n);

    }

}
