package com.iti.chat.delegate;

import com.iti.chat.model.User;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.viewcontroller.LoginController;
import com.iti.chat.viewcontroller.RegisterController;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class RegisterDelegate {
    ClientServiceProvider client;
    RegisterController registerView;

    public RegisterDelegate(ClientServiceProvider client, RegisterController registerView) {
        this.client = client;
        this.registerView = registerView;
    }

    public ClientServiceProvider getClient() {
        return client;
    }

    public void setClient(ClientServiceProvider client) {
        this.client = client;
    }

    public RegisterController getLoginView() {
        return registerView;
    }

    public void setLoginView(RegisterController loginView) {
        this.registerView = registerView;
    }

    public void register(User user, String password) throws RemoteException, SQLException, NotBoundException {
        client.register(user, password);
    }

    public void uploadImage(File file , User user) throws NotBoundException, SQLException, IOException {
        client.uploadImage(file,user);
    }
}
