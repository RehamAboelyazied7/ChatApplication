package com.iti.chat.service;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import com.iti.chat.delegate.ChatRoomDelegate;
import com.iti.chat.delegate.FriendRequestDelegate;
import com.iti.chat.model.*;
import com.iti.chat.util.Session;
import com.iti.chat.viewcontroller.ChatRoomController;
import com.iti.chat.viewcontroller.HomeController;
import com.iti.chat.viewcontroller.NotificationListController;
import com.iti.chat.viewcontroller.PushNotification;

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
    ChatRoomController chatRoomController;
    FriendRequestsService friendRequestsService;
    ChatRoomService chatRoomService;
    public SessionService sessionService;
    FileTransferService fileTransferService;
    ChatRoomDelegate chatRoomDelegate;
    FriendRequestDelegate friendRequestDelegate;
    NotificationListController notificationListController = new NotificationListController();
    Registry registry;

    public ClientServiceProvider() throws RemoteException {
        registry = LocateRegistry.getRegistry(4000);
    }

    public void setChatRoomDelegate(ChatRoomDelegate chatRoomDelegate) {
        this.chatRoomDelegate = chatRoomDelegate;
    }

    public void setChatRoomController(ChatRoomController chatRoomController) {
        this.chatRoomController = chatRoomController;
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
        Session.getInstance().setUser(currentUser);
    }

    @Override
    public void sendMessage(Message message, int roomId) throws RemoteException, NotBoundException {
        initChatRoomService();
        chatRoomService.sendMessage(message, roomId);
        System.out.println("recieve");


    }

    public void sendFile(Message message, File file, int roomId) throws IOException, NotBoundException {
        initChatRoomService();
        InputStream inputStream = new FileInputStream(file.getAbsolutePath());
        RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inputStream);
        chatRoomService.sendFile(message, roomId, remoteFileData);
    }

    public void downloadFile(RemoteInputStream remoteInputStream) throws IOException {
        chatRoomDelegate.receiveFile(remoteInputStream);
    }

    public void uploadImage(File file, User user) throws IOException, NotBoundException, SQLException {
        initSessionService();
        InputStream inputStream = new FileInputStream(file.getAbsolutePath());
        RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inputStream);
        sessionService.uploadImage(remoteFileData, this, user);
    }

    public void downloadImage(User user, RemoteInputStream remoteInputStream) throws IOException {
        controller.receiveImage(user, remoteInputStream);
    }

    public void requestFileDownload(String remotePath) throws IOException, NotBoundException {
        initFileTransferService();
        fileTransferService.downloadFile(remotePath, this);
    }

    public void imageChanged() {
        controller.imageChanged();
    }


    public void requestImageDownload(User user) throws IOException, NotBoundException {
        initFileTransferService();
        fileTransferService.downloadImage(user, this);
    }

    @Override
    public void receiveMessage(Message message) {
        //chatRoomController.receiveMessage(message);
        // PushNotification.createNotify(message);
        //chatRoomController.receiveMessage(message);
        if (chatRoomDelegate != null) {
            chatRoomDelegate.receiveMessage(message);
        }
    }

    public ChatRoom createNewChatRoom(List<User> users) throws RemoteException, NotBoundException {
        initChatRoomService();
        return chatRoomService.createNewChatRoom(users);
    }

    @Override
    public void receiveNotification(Notification notification) {
        System.out.println("sender" + notification.getSource() + "reciever" + notification.getReceiver());
        controller.receiveNotification(notification);
     /*   notificationListController.setNotifications(notification);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.notificationView();

            }
        });

      */
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
        if (fileTransferService == null) {
            fileTransferService = (FileTransferService) registry.lookup(ServerServices.FILE_TRANSFER_SERVICE);
        }
    }

    public void sendFriendRequest(User receiver) throws RemoteException, NotBoundException {
        initFriendRequestService();
        friendRequestsService.sendFriendRequest(this, receiver);
        PushNotification pushNotification = new PushNotification();
        Notification notification = new Notification(this.currentUser, receiver, NotificationType.FRIENDSHIP_REQUEST_RECEIVED);
        pushNotification.initializeNotify(notification);

    }

    public List<User> getPendingRequests() throws RemoteException {
        return friendRequestsService.pendingFriendRequestsSent(this);
    }

    public void acceptFriendRequest(User sender) throws RemoteException, NotBoundException {
        initFriendRequestService();
        friendRequestsService.acceptFriendRequest(this, sender);
        PushNotification pushNotification = new PushNotification();
        Notification notification = new Notification(sender, this.currentUser, NotificationType.FRIENDSHIP_ACCEPTED);
        pushNotification.initializeNotify(notification);
    }

    public void rejectFriendRequest(User sender) throws RemoteException, NotBoundException {
        initFriendRequestService();
        friendRequestsService.rejectFriendRequest(this, sender);
        PushNotification pushNotification = new PushNotification();
        Notification notification = new Notification(sender, this.currentUser, NotificationType.FRIENDSHIP_REJECTED);
        pushNotification.initializeNotify(notification);
    }

    public List<User> getPendingSentRequestFriends() throws RemoteException, NotBoundException {
        initFriendRequestService();
        return friendRequestsService.pendingFriendRequestsSent(this);
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

    @Override
    public void recieveAnnouncment(Message announcment) throws RemoteException {
        System.out.println("inside receive ann");

        controller.receiveAnnouncment(announcment);
      /*  Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.notificationView();

            }
        });

       */
    }

    public List<User> searchByphone(String phone) throws RemoteException, SQLException, NotBoundException {
        initFriendRequestService();
        return friendRequestsService.searchByPhone(phone);
    }

    public void userInfoDidChange(User user) {
        if (currentUser.equals(user)) {
            setUser(user);
        } else if (currentUser.getFriends().contains(user)) {
            currentUser.getFriends().remove(user);
            currentUser.getFriends().add(user);
        }
        if (controller != null) {
            controller.userInfoDidChange(user);
        }

    }

    public ChatRoom getChatRoom(int roomId) throws RemoteException, NotBoundException {
        initChatRoomService();
        return chatRoomService.getChatRoom(roomId);
    }

    public FriendRequestDelegate getFriendRequestDelegate() {
        return friendRequestDelegate;
    }

    public void setFriendRequestDelegate(FriendRequestDelegate friendRequestDelegate) {
        this.friendRequestDelegate = friendRequestDelegate;
    }

    public List<ChatRoom> getGroupChatRooms(User user) throws RemoteException, NotBoundException {
        initChatRoomService();
        return chatRoomService.getGroupChatRooms(user);
    }

    public void updateStatus(User user) {
        try {
            sessionService.updateStatus(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

