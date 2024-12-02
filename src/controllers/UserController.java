package controllers;

import models.User;

import java.sql.SQLException;
import java.util.Optional;

public class UserController {

    public String register(String email, String username, String password, String role) {
        if (email == null || email.isEmpty()) return "Email cannot be empty.";
        if (username == null || username.isEmpty()) return "Username cannot be empty.";
        if (password == null || password.isEmpty())return "Password cannot be empty.";
        if (role == null || role.isEmpty()) return "Role must be selected.";

        try {
            if (User.isEmailOrUsernameTaken(email, username)) {
                return "Email or Username is already in use.";
            }

            String userId = User.generateUserId();
            User.insertUser(userId, email, username, password, role);
            return "Registration successful. Please log in.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred during registration.";
        }
    }

    public String login(String email, String password) {
        if (email == null || email.isEmpty()) return null; 
        if (password == null || password.isEmpty()) return null;

        try {
            Optional<User> userOpt = User.getUserByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (user.getPassword().equals(password)) { 
                    return user.getRole().toLowerCase(); 
                } else {
                    return null; 
                }
            } else {
                return null; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }
}
