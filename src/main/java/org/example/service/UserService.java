package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;

import java.sql.SQLException;

public class UserService {
    private UserDao userDao = new UserDao();

    public User getUser(String username, String usertype) throws SQLException {
        return userDao.getUser(username, usertype);
    }

    public void createUser(String username, String password, String usertype) throws SQLException {
        userDao.createUser(username, password, usertype);
    }

    public boolean validateUser(String username, String usertype) throws SQLException {
        User user = userDao.getUser(username, usertype);
        return user != null;
    }

    public boolean validatePassword(String username, String password, String usertype) throws SQLException {
        User user = userDao.getUser(username, usertype);
        return user != null && user.getPassword().equals(password);
    }
}