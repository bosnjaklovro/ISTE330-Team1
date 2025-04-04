package src;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {
    private int userID;
    private String username;
    private String password;
    private String role;
    private String email;
    private Timestamp createdAt;
    private MySQLDatabase db;

    // Constructor
    public User(int userID, String username, String password, String role, String email, Timestamp createdAt) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Getters
    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDb(MySQLDatabase db) {
        this.db = db;
    }

    public boolean create(String username, String password, String role, String email) {
        String sql = "INSERT INTO User (Username, Password, Role, Email) VALUES (?, ?, ?, ?)";
        return db.executeUpdate(sql, username, password, role, email) > 0;
    }

    public User getById(int userId) {
        String sql = "SELECT * FROM User WHERE UserID = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("UserID"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("Role"),
                            rs.getString("Email"),
                            rs.getTimestamp("CreatedAt")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Get user error: " + e.getMessage());
        }
        return null;
    }

    public boolean update(int userID, String username, String password, String role, String email) {
        String sql = "UPDATE User SET Username=?, Password=?, Role=?, Email=? WHERE UserID=?";
        return db.executeUpdate(sql, username, password, role, email, userID) > 0;
    }

    public boolean delete(int userID) {
        String sql = "DELETE FROM User WHERE UserID=?";
        return db.executeUpdate(sql, userID) > 0;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println( e.getMessage());
        }
        return password;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}