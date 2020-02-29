package com.iti.chat.delegate;

import com.iti.chat.model.User;
import com.iti.chat.service.ClientServiceProvider;
import com.iti.chat.controller.RequestContactCellController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class PendingFriendRequestCellDelegate {

    private ClientServiceProvider clientServiceProvider;
    private RequestContactCellController requestContactCellController;


    public void acceptFriendRequest(User requestSender){

        try {
            clientServiceProvider.acceptFriendRequest(requestSender);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

    public void rejectFriendRequest(User requestSender){

        try {
            clientServiceProvider.rejectFriendRequest(requestSender);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

    public PendingFriendRequestCellDelegate(ClientServiceProvider clientServiceProvider, RequestContactCellController requestContactCell){

        this.clientServiceProvider = clientServiceProvider;
        this.requestContactCellController = requestContactCell;

    }

    public ClientServiceProvider getClientServiceProvider() {
        return clientServiceProvider;
    }

    public void setClientServiceProvider(ClientServiceProvider clientServiceProvider) {
        this.clientServiceProvider = clientServiceProvider;
    }


    public RequestContactCellController getRequestContactCellController() {
        return requestContactCellController;
    }

    public void setRequestContactCellController(RequestContactCellController requestContactCellController) {
        this.requestContactCellController = requestContactCellController;
    }
}
