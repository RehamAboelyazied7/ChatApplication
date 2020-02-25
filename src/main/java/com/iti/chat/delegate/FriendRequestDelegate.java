/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.chat.delegate;

import com.iti.chat.model.User;
import com.iti.chat.service.ClientService;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.service.FriendRequestsService;
import com.iti.chat.viewcontroller.ContactsSearchBox;
import com.iti.chat.viewcontroller.HomeController;
import com.iti.chat.viewcontroller.UserProfileController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;


public class FriendRequestDelegate {
    ContactsSearchBox viewController;
    ClientServiceProvider client;

    public FriendRequestDelegate(ContactsSearchBox view,ClientServiceProvider client) {
        this.viewController = view;
        this.client = client;
    }

    public ClientServiceProvider getClient() {
        return client;
    }

    public void setClient(ClientServiceProvider client) {
        this.client = client;
    }

    public ContactsSearchBox getViewController() {
        return viewController;
    }

    public void setViewController(ContactsSearchBox viewController) {
        this.viewController = viewController;
    }

    public void sendFriendRequest(User receiver) throws RemoteException, NotBoundException {
        client.sendFriendRequest(receiver);
    }

    public List<User> searchByPhone(String phone) throws RemoteException, NotBoundException, SQLException {
        return client.searchByphone(phone);
    }



}
