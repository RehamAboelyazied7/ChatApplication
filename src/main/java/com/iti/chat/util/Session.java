package com.iti.chat.util;

import com.iti.chat.model.User;

public class Session {
    private User user;
    private static Session instance;

    private Session() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Session getInstance() {
        if(instance == null) {
            instance = new Session();
        }
        return instance;
    }


}
