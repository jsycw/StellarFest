package models;

import utils.Connect;
import java.sql.*;
import java.util.Optional;

public class User {
    private String email;
    private String username;
    private String password;
    private String role;

    public User(String email, String username, String password, String role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public static String generateUserId() throws SQLException {
        Connect con = Connect.getInstance();
        String getLastIdQuery = "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1";
        PreparedStatement ps = con.preparedStatement(getLastIdQuery);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String lastId = rs.getString("user_id");
            int idNumber = Integer.parseInt(lastId.substring(2)) + 1; 
            return "US" + String.format("%03d", idNumber); 
        }
        return "US001"; 
    }

    public static boolean isEmailOrUsernameTaken(String email, String username) throws SQLException {
        Connect con = Connect.getInstance();
        String checkQuery = "SELECT * FROM users WHERE user_email = ? OR user_name = ?";
        PreparedStatement ps = con.preparedStatement(checkQuery);
        ps.setString(1, email);
        ps.setString(2, username);
        ResultSet rs = ps.executeQuery();

        return rs.next(); 
    }

    public static void insertUser(String userId, String email, String username, String password, String role) throws SQLException {
        Connect con = Connect.getInstance();
        String insertQuery = "INSERT INTO users (user_id, user_email, user_name, user_password, user_role) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.preparedStatement(insertQuery);
        ps.setString(1, userId);
        ps.setString(2, email);
        ps.setString(3, username);
        ps.setString(4, password); 
        ps.setString(5, role);
        ps.executeUpdate();
    }

    public static Optional<User> getUserByEmail(String email) throws SQLException {
        Connect con = Connect.getInstance();
        String query = "SELECT * FROM users WHERE user_email = ?";
        PreparedStatement ps = con.preparedStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User user = new User(
                rs.getString("user_email"),
                rs.getString("user_name"),
                rs.getString("user_password"),
                rs.getString("user_role")
            );
            return Optional.of(user);
        }
        return Optional.empty();
    }

}