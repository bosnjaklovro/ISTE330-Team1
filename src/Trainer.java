package src;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Trainer {
    private int trainerID;          // Corresponds to TrainerID in the database
    private int userID;             // Corresponds to UserID from the User table
    private String firstName;       // Corresponds to FirstName in the database
    private String lastName;        // Corresponds to LastName in the database
    private String phone;           // Corresponds to Phone in the database
    private Date hireDate;          // Corresponds to HireDate in the database
    private String certification;    // Corresponds to Certification in the database
    private MySQLDatabase db;       // Database connection

    // Constructor
    public Trainer(int trainerID, int userID, String firstName, String lastName, String phone, Date hireDate, String certification) {
        this.trainerID = trainerID;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.hireDate = hireDate;
        this.certification = certification;
    }

    // Getters
    public int getTrainerID() {
        return trainerID;
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

    public String getCertification() {
        return certification;
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

    public void setCertification(String certification) {
        this.certification = certification;
    }

    // Set database connection
    public void setDb(MySQLDatabase db) {
        this.db = db;
    }

    // Create a new trainer
    public boolean create(int userID, String firstName, String lastName, String phone, Date hireDate, String certification) {
        String sql = "INSERT INTO Trainer (UserID, FirstName, LastName, Phone, HireDate, Certification) VALUES (?, ?, ?, ?, ?, ?)";
        return db.executeUpdate(sql, userID, firstName, lastName, phone, hireDate, certification) > 0;
    }

    // Get trainer by ID
    public Trainer getById(int trainerId) {
        String sql = "SELECT * FROM Trainer WHERE TrainerID = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Trainer(
                            rs.getInt("TrainerID"),
                            rs.getInt("UserID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Phone"),
                            rs.getDate("HireDate"),
                            rs.getString("Certification")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Get trainer error: " + e.getMessage());
        }
        return null;
    }

    // Update trainer details
    public boolean update(int trainerID, String firstName, String lastName, String phone, Date hireDate, String certification) {
        String sql = "UPDATE Trainer SET FirstName=?, LastName=?, Phone=?, HireDate=?, Certification=? WHERE TrainerID=?";
        return db.executeUpdate(sql, firstName, lastName, phone, hireDate, certification, trainerID) > 0;
    }

    // Delete a trainer
    public boolean delete(int trainerID) {
        String sql = "DELETE FROM Trainer WHERE TrainerID=?";
        return db.executeUpdate(sql, trainerID) > 0;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "Trainer{" +
                "trainerID=" + trainerID +
                ", userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", hireDate=" + hireDate;
    }
}
