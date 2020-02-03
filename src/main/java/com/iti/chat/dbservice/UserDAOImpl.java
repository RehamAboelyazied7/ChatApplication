package com.iti.chat.dbservice;

import com.iti.chat.dao.UserDAO;
import com.iti.chat.exception.DuplicatePhoneException;
import com.iti.chat.model.Gender;
import com.iti.chat.util.adapter.UserAdapter;
import com.iti.chat.model.User;
import com.iti.chat.model.UserStatus;

import java.sql.*;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    public User findUserById(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "select * from users where user_id = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.first();
        User user = UserAdapter.createUser(resultSet);
        if(user != null) {
            setFriends(user, connection);
        }
        DBConnection.getInstance().closeConnection(connection);
        return user;
    }

    public User findUserByPhone(String phone) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "select * from users where phone = " + phone;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.first();
            User user = UserAdapter.createUser(resultSet);
            if(user != null) {
                setFriends(user, connection);
            }
            DBConnection.getInstance().closeConnection(connection);
            return user;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

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
        List<User> friends = UserAdapter.createUsers(resultSet);
        user.setFriends(friends);
    }

    public User login(String phone, String password) throws SQLException {
        String query = "select * from users where phone = " + phone +
                " and password = " + password;
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.first();
        User user = UserAdapter.createUser(resultSet);
        updateUserStatus(user, UserStatus.ONLINE);
        if(user != null) {
            setFriends(user, connection);
        }
        DBConnection.getInstance().closeConnection(connection);
        return user;
    }

    public void logout(User user) throws SQLException {

        updateUserStatus(user, UserStatus.OFFLINE);
    }

    public User register(String firstName, String lastName, String phone, String password, String email) throws DuplicatePhoneException {
        User user = findUserByPhone(phone);
        if(user == null) {
            String query = "insert into users (first_name, last_name, user_status, email, password, phone)" +
                    " values (?, ?, ?, ?, ?, ?)";
            try {
                Connection connection = DBConnection.getInstance().getConnection();
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
                user = new User(firstName, lastName, phone, email, UserStatus.ONLINE, Gender.MALE);
                user.setId(tableKeys.getInt(1));
                DBConnection.getInstance().closeConnection(connection);
                return user;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            throw new DuplicatePhoneException("phone is already used");
        }

    }

    public List<User> findAllUsers() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "select * from users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<User> users = UserAdapter.createUsers(resultSet);
        DBConnection.getInstance().closeConnection(connection);
        return users;
    }

    private boolean updateUserStatus(User user, int userStatus) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "update users set user_status = " + userStatus + " where user_id = " + user.getId();
        Statement statement = connection.createStatement();
        boolean success = statement.execute(query);
        user.setStatus(userStatus);
        DBConnection.getInstance().closeConnection(connection);
        return success;
    }

    public static void main(String[] args) {
        UserDAOImpl service = new UserDAOImpl();
        try {
            System.out.println(service.findAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
