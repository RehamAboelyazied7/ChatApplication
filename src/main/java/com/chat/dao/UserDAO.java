package com.chat.dao;

import com.chat.model.User;

import java.sql.SQLException;

public interface UserDAO {
    public User findUserById(int id) throws SQLException;
    public User login(String phone, String password) throws SQLException;
    public void logout(User user) throws SQLException;
    public User register(String firstName, String lastName, String phone, String password, String email) throws SQLException;
}
