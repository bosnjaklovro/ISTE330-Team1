package src;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Member {
    private int memberID;
    private int userID;
    private String firstName;
    private String lastName;
    private Date dob;
    private String phone;
    private String address;
    private MySQLDatabase db;

    // Constructor
    public Member(int memberID, int userID, String firstName, String lastName, Date dob, String phone, String address) {
        this.memberID = memberID;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
    }

    // Getters
    public int getMemberID() {
        return memberID;
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

    public Date getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Set database connection
    public void setDb(MySQLDatabase db) {
        this.db = db;
    }

    // Create a new member
    public boolean create(int userID, String firstName, String lastName, Date dob, String phone, String address) {
        String sql = "INSERT INTO Member (UserID, FirstName, LastName, DOB, Phone, Address) VALUES (?, ?, ?, ?, ?, ?)";
        return db.executeUpdate(sql, userID, firstName, lastName, dob, phone, address) > 0;
    }

    // Get member by ID
    public Member getById(int memberId) {
        String sql = "SELECT * FROM Member WHERE MemberID = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                            rs.getInt("MemberID"),
                            rs.getInt("UserID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getDate("DOB"),
                            rs.getString("Phone"),
                            rs.getString("Address")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Get member error: " + e.getMessage());
        }
        return null;
    }

    // Update member details
    public boolean update(int memberID, String firstName, String lastName, Date dob, String phone, String address) {
        String sql = "UPDATE Member SET FirstName=?, LastName=?, DOB=?, Phone=?, Address=? WHERE MemberID=?";
        return db.executeUpdate(sql, firstName, lastName, dob, phone, address, memberID) > 0;
    }

    // Delete a member
    public boolean delete(int memberID) {
        String sql = "DELETE FROM Member WHERE MemberID=?";
        return db.executeUpdate(sql, memberID) > 0;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "Member{" +
                "memberID=" + memberID +
                ", userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}