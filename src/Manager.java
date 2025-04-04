package src;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Manager {
    private int managerID;        // Corresponds to ManagerID in the database
    private int userID;           // Corresponds to UserID from the User table
    private String firstName;     // Corresponds to FirstName in the database
    private String lastName;      // Corresponds to LastName in the database
    private String phone;         // Corresponds to Phone in the database
    private Date hireDate;        // Corresponds to HireDate in the database
    private MySQLDatabase db;     // Database connection

    // Constructor
    public Manager(int managerID, int userID, String firstName, String lastName, String phone, Date hireDate) {
        this.managerID = managerID;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.hireDate = hireDate;
    }

    // Getters
    public int getManagerID() {
        return managerID;
    }

    public int getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public Date getHireDate() {
        return hireDate;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    // Set database connection
    public void setDb(MySQLDatabase db) {
        this.db = db;
    }

    // Create a new manager
    public boolean create(int userID, String firstName, String lastName, String phone, Date hireDate) {
        String sql = "INSERT INTO Manager (UserID, FirstName, LastName, Phone, HireDate) VALUES (?, ?, ?, ?, ?)";
        return db.executeUpdate(sql, userID, firstName, lastName, phone, hireDate) > 0;
    }

    // Get manager by ID
    public Manager getById(int managerId) {
        String sql = "SELECT * FROM Manager WHERE ManagerID = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, managerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Manager(
                            rs.getInt("ManagerID"),
                            rs.getInt("UserID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Phone"),
                            rs.getDate("HireDate")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Get manager error: " + e.getMessage());
        }
        return null;
    }

    // Update manager details
    public boolean update(int managerID, String firstName, String lastName, String phone, Date hireDate) {
        String sql = "UPDATE Manager SET FirstName=?, LastName=?, Phone=?, HireDate=? WHERE ManagerID=?";
        return db.executeUpdate(sql, firstName, lastName, phone, hireDate, managerID) > 0;
    }

    // Delete a manager
    public boolean delete(int managerID) {
        String sql = "DELETE FROM Manager WHERE ManagerID=?";
        return db.executeUpdate(sql, managerID) > 0;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "Manager{" +
                "managerID=" + managerID +
                ", userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", hireDate=" + hireDate +
                '}';
    }
}