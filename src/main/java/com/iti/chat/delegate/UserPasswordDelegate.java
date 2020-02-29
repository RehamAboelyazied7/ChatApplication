package com.iti.chat.delegate;

import com.iti.chat.model.User;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.controller.ChangePasswordController;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class UserPasswordDelegate {
    private  ChangePasswordController changePasswordController;
    private  ClientServiceProvider client;

    public UserPasswordDelegate(ClientServiceProvider client, ChangePasswordController viewController) {
        this.client = client;
        this.changePasswordController = viewController;
    }

    public void updateUserPassword(User user) throws RemoteException, SQLException {
        client.updateUserPassword(user);
    }

    public ChangePasswordController getChangePasswordController() {
        return changePasswordController;
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public ClientServiceProvider getClient() {
        return client;
    }

    public void setClient(ClientServiceProvider client) {
        this.client = client;
    }

    public void logout(User currentUser) throws RemoteException {
        client.sessionService.logout(currentUser);
    }
}
