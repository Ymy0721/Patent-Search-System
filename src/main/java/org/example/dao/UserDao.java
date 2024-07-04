package org.example.dao;

import org.example.model.User;
import org.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private Connection connection;

    public UserDao() {
        try {
            this.connection = DatabaseUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public User getUser(String username, String usertype) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND usertype = ?");
        stmt.setString(1, username);
        stmt.setString(2, usertype);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new User(rs.getString("username"), rs.getString("password"), rs.getString("usertype"));
        } else {
            return null;
        }
    }

    public void createUser(String username, String password, String usertype) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (username, password, usertype) VALUES (?, ?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, usertype);
        stmt.executeUpdate();
    }

//    public void testConnection() throws SQLException {
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users LIMIT 1");
//        ResultSet rs = stmt.executeQuery();
//        if (rs.next()) {
//            System.out.println("Connection is working correctly. Retrieved a record from users table.");
//        } else {
//            System.out.println("Connection is working correctly. No records in users table.");
//        }
//    }
//    public static void main(String[] args) {
//        UserDao userDao = new UserDao();
//        try {
//            userDao.testConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}