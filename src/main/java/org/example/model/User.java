package org.example.model;

public class User {
    private String username;
    private String password;
    private String usertype;

    public User(String username, String password, String usertype) {
        this.username = username;
        this.password = password;
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsertype() {
        return usertype;
    }
}