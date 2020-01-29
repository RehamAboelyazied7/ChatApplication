package com.chat.factory;

import com.chat.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFactory {
    public static User createUser(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String phone = resultSet.getString("phone");
        int id = resultSet.getInt("user_id");
        int status = resultSet.getInt("user_status");
        User user = new User();
        user.setStatus(status);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setId(id);
        return user;
    }

    public static List<User> createUsers(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = createUser(resultSet);
            users.add(user);
        }
        return users;
    }
}
