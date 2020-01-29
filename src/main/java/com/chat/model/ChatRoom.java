package com.chat.model;

import java.util.List;

public class ChatRoom {
    private int id;
    private List<User> users;

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public List<User> getUsers() {

        return users;
    }

    public void setUsers(List<User> users) {

        this.users = users;
    }
}
