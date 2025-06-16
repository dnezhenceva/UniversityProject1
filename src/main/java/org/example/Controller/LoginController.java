package org.example.Controller;

import org.example.model.User;
import org.example.model.Users;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginController {
    private final Users users;

    public LoginController(Connection connection) {
        this.users = new Users(connection);
    }

    public User login(String username, String password) throws SQLException {
        User user = users.getByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
