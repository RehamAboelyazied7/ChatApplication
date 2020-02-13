package com.iti.chat.delegate;

import com.iti.chat.model.User;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.viewcontroller.LoginController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class LoginDelegate {
    ClientServiceProvider client;
    LoginController loginView;

    public LoginDelegate(ClientServiceProvider client, LoginController loginView) {
        this.client = client;
        this.loginView = loginView;
    }

    public ClientServiceProvider getClient() {
        return client;
    }

    public void setClient(ClientServiceProvider client) {
        this.client = client;
    }

    public LoginController getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginController loginView) {
        this.loginView = loginView;
    }

    public User login(String phone, String password) throws RemoteException, SQLException, NotBoundException {
        return client.login(phone, password);
    }
}
