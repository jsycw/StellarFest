package controller;

import models.User;
import utils.Database;

import java.sql.*;

public class UserController {
    
    public static boolean register(String email, String username, String password, String role) {
        try (Connection conn = Database.getConnection()) {
            String query = "INSERT INTO users (email, username, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, role);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean login(String email, String password) {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateProfile(String currentEmail, String newEmail, String newUsername, String newPassword) {
        try (Connection conn = Database.getConnection()) {
            String query = "UPDATE users SET email = ?, username = ?, password = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newEmail);
            stmt.setString(2, newUsername);
            stmt.setString(3, newPassword);
            stmt.setString(4, currentEmail);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
