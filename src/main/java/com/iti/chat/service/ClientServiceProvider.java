package com.iti.chat.service;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import com.iti.chat.dao.NotificationDAO;
import com.iti.chat.delegate.ChatRoomDelegate;
import com.iti.chat.model.*;
import com.iti.chat.viewcontroller.ChatRoomController;
import com.iti.chat.viewcontroller.HomeController;
import com.iti.chat.viewcontroller.NotificationListController;
import com.iti.chat.viewcontroller.PushNotification;
import javafx.application.Platform;


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
    SessionService sessionService;
    FileTransferService fileTransferService;
    ChatRoomDelegate chatRoomDelegate;
    NotificationListController notificationListController=new NotificationListController();
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
    }

    @Override
    public void sendMessage(Message message, ChatRoom room) throws RemoteException, NotBoundException {
        initChatRoomService();
        chatRoomService.sendMessage(message, room);
       /*  System.out.println("recieve");
       for (int i = 0; i < room.getUsers().size(); i++) {
            if (!message.getSender().equals(room.getUsers().get(i))) {
                System.out.println("loop notification");
                PushNotification pushNotification=new PushNotification();
                Notification notification=new Notification(message.getSender(),room.getUsers().get(i),NotificationType.MESSAGE_RECEIVED);
                pushNotification.createNotify(notification);
            }
        }

        */
    }

    public void sendFile(Message message, File file, ChatRoom room) throws IOException, NotBoundException {
        initChatRoomService();
        InputStream inputStream = new FileInputStream(file.getAbsolutePath());
        RemoteInputStreamServer remoteFileData = new SimpleRemoteInputStream(inputStream);
        chatRoomService.sendFile(message, room, remoteFileData);
    }

    public void downloadFile(RemoteInputStream remoteInputStream) throws IOException {
        //chatRoomController.receiveFile(remoteInputStream);
        chatRoomDelegate.receiveFile(remoteInputStream);
    }

    public void downloadImage(RemoteInputStream remoteInputStream) throws IOException {
        chatRoomController.receiveFile(remoteInputStream);
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
        //chatRoomController.receiveMessage(message);
       // PushNotification.createNotify(message);
        //chatRoomController.receiveMessage(message);
        chatRoomDelegate.receiveMessage(message);
    }

    public ChatRoom createNewChatRoom(List<User> users) throws RemoteException, NotBoundException {
        initChatRoomService();
        return chatRoomService.createNewChatRoom(users);
    }

    @Override
    public void receiveNotification(Notification notification) {

        NotificationListController.notifications.add(notification);

        controller.receiveNotification(notification);
        if(controller.check!=0) {
            Platform.runLater(
                    () -> {
                        // Update UI here.
                        controller.notificationView();
                    }
            );
        }
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
                PushNotification pushNotification=new PushNotification();
                Notification notification=new Notification(this.currentUser,receiver,NotificationType.FRIENDSHIP_REQUEST_RECEIVED);
                pushNotification.initializeNotify(notification);

        }



    public void acceptFriendRequest(User sender) throws RemoteException, NotBoundException {
        initFriendRequestService();
        friendRequestsService.acceptFriendRequest(this, sender);
        PushNotification pushNotification=new PushNotification();
        Notification notification=new Notification(sender,this.currentUser,NotificationType.FRIENDSHIP_ACCEPTED);
        pushNotification.initializeNotify(notification);
    }

    public void rejectFriendRequest(User sender) throws RemoteException, NotBoundException {
        initFriendRequestService();
        friendRequestsService.rejectFriendRequest(this, sender);
        PushNotification pushNotification=new PushNotification();
        Notification notification=new Notification(sender,this.currentUser,NotificationType.FRIENDSHIP_REJECTED);
        pushNotification.initializeNotify(notification);
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

    @Override
    public void recieveAnnouncment(String announcment) throws RemoteException {
      //  NotificationListController.notifications.add();
      //  System.out.println("inside receive ann");
        User user=new User("Server","","","",0,"");
        NotificationListController.notifications.add(new Notification(user,this.getUser(),NotificationType.MESSAGE_RECEIVED));
        controller.receiveAnnouncment(announcment);
        if(controller.check!=0) {
            Platform.runLater(
                    () -> {
                        // Update UI here.
                        controller.notificationView();
                    }
            );
        }


        //System.out.println( "announce addede"+ NotificationListController.notifications.size());

    }

}
