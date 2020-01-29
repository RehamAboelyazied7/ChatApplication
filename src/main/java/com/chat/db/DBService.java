package com.chat.db;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBService {
    protected final String user = "root";
    protected final String password = "password";
    protected final String url = "jdbc:mysql://localhost:3306/chatty";
    protected MysqlDataSource dataSource;

    public DBService() {
        dataSource = new MysqlDataSource();
        dataSource.setPassword(password);
        dataSource.setUser(user);
        dataSource.setURL(url);
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    protected void closeConnection(Connection connection) throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }
}
