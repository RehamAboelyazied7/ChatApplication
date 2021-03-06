package com.iti.chat.delegate;

import com.iti.chat.model.User;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.controller.UserProfileController;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class UserInfoDelegate {
    private UserProfileController viewController;
    private ClientServiceProvider client;

    public UserInfoDelegate(ClientServiceProvider client, UserProfileController viewController) {
        this.client = client;
        this.viewController = viewController;
    }

    public ClientServiceProvider getClient() {
        return client;
    }

    public void setClient(ClientServiceProvider client) {
        this.client = client;
    }

    public UserProfileController getViewController() {
        return viewController;
    }

    public void setViewController(UserProfileController viewController) {
        this.viewController = viewController;
    }

    public void updateUserInfo(User user) throws RemoteException, SQLException, NotBoundException {
        client.updateUserInfo(user);
    }

    public void updateUserPassword(User user) throws RemoteException, SQLException {
        client.updateUserPassword(user);
    }
    public void uploadImage(File file , User user) throws NotBoundException, SQLException, IOException {
        client.uploadImage(file,user);
    }

    public void imageChanged() {
        client.imageChanged();
    }
}
