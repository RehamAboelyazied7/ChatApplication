package com.chat.db;

import com.chat.dao.UserDAO;
import com.chat.factory.UserFactory;
import com.chat.model.User;
import com.chat.model.UserStatus;

import java.sql.*;
import java.util.List;

public class UserDBService extends DBService implements UserDAO {
    public User findUserById(int id) throws SQLException {
        Connection connection = getConnection();
        String query = "select * from users where user_id = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.first();
        User user = UserFactory.createUser(resultSet);
        if(user != null) {
            setFriends(user, connection);
        }
        closeConnection(connection);
        return user;
    }

    @Override
    public User login(String phone, String password) {
        return null;
    }

    private void setFriends(User user, Connection connection) throws SQLException {
        String query1 = "select u2.* from friend_requests fr, users u1, users u2 where " +
                "u1.user_id = fr.sender_id and fr.status = 1 and fr.receiver_id = u2.user_id" +
        " and u2.user_id <> u1.user_id and u1.user_id = " + user.getId();
        String query2 = "select u2.* from friend_requests fr, users u1, users u2 where " +
                "u1.user_id = fr.receiver_id and fr.status = 1 and u2.user_id = fr.sender_id" +
                " and u2.user_id <> u1.user_id and u1.user_id = " + user.getId();
        String query = query1 + " union " + query2;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<User> friends = UserFactory.createUsers(resultSet);
        user.setFriends(friends);
    }

    public User logIn(String phone, String password) throws SQLException {
        String query = "select * from users where phone = " + phone +
                " and password = " + password;
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.first();
        User user = UserFactory.createUser(resultSet);
        updateUserStatus(user, UserStatus.ONLINE);
        if(user != null) {
            setFriends(user, connection);
        }
        closeConnection(connection);
        return user;
    }

    public void logout(User user) throws SQLException {

        updateUserStatus(user, UserStatus.OFFLINE);
    }

    public User register(String firstName, String lastName, String phone, String password, String email) throws SQLException {
        String query = "insert into users (first_name, last_name, user_status, email, password, phone)" +
                " values (?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setInt(3, UserStatus.ONLINE);
        preparedStatement.setString(4, email);
        preparedStatement.setString(5, password);
        preparedStatement.setString(6, phone);
        preparedStatement.executeUpdate();
        ResultSet tableKeys = preparedStatement.getGeneratedKeys();
        tableKeys.next();
        User user = new User(firstName, lastName, phone, email, UserStatus.ONLINE);
        user.setId(tableKeys.getInt(1));
        closeConnection(connection);
        return user;
    }

    public List<User> findAllUsers() throws SQLException {
        Connection connection = getConnection();
        String query = "select * from users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<User> users = UserFactory.createUsers(resultSet);
        closeConnection(connection);
        return users;
    }

    private boolean updateUserStatus(User user, int userStatus) throws SQLException {
        Connection connection = getConnection();
        String query = "update users set user_status = " + userStatus + " where user_id = " + user.getId();
        Statement statement = connection.createStatement();
        boolean success = statement.execute(query);
        user.setStatus(userStatus);
        closeConnection(connection);
        return success;
    }

    public static void main(String[] args) {
        UserDBService service = new UserDBService();
        try {
            User user = service.register("Khaled", "Elhossiny", "974834792", "pass", "khaled@gmail.com");
            System.out.println(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
