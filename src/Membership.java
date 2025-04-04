package src;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Membership {
    private int membershipID;  // Corresponds to MembershipID in the database
    private int memberID;       // Corresponds to MemberID from the Member table
    private int planID;         // Corresponds to PlanID from the MembershipPlan table
    private Date startDate;     // Corresponds to StartDate in the database
    private Date endDate;       // Corresponds to EndDate in the database
    private boolean isActive;    // Corresponds to IsActive in the database
    private MySQLDatabase db;   // Database connection

    // Constructor
    public Membership(int membershipID, int memberID, int planID, Date startDate, Date endDate, boolean isActive) {
        this.membershipID = membershipID;
        this.memberID = memberID;
        this.planID = planID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    // Getters
    public int getMembershipID() {
        return membershipID;
    }

    public int getMemberID() {
        return memberID;
    }

    public int getPlanID() {
        return planID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters
    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Set database connection
    public void setDb(MySQLDatabase db) {
        this.db = db;
    }

    // Create a new membership
    public boolean create(int memberID, int planID, Date startDate, Date endDate, boolean isActive) {
        String sql = "INSERT INTO Membership (MemberID, PlanID, StartDate, EndDate, IsActive) VALUES (?, ?, ?, ?, ?)";
        return db.executeUpdate(sql, memberID, planID, startDate, endDate, isActive) > 0;
    }

    // Get membership by ID
    public Membership getById(int membershipID) {
        String sql = "SELECT * FROM Membership WHERE MembershipID = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, membershipID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Membership(
                            rs.getInt("MembershipID"),
                            rs.getInt("MemberID"),
                            rs.getInt("PlanID"),
                            rs.getDate("StartDate"),
                            rs.getDate("EndDate"),
                            rs.getBoolean("IsActive")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Get membership error: " + e.getMessage());
        }
        return null;
    }

    // Update membership details
    public boolean update(int membershipID, int memberID, int planID, Date startDate, Date endDate, boolean isActive) {
        String sql = "UPDATE Membership SET MemberID=?, PlanID=?, StartDate=?, EndDate=?, IsActive=? WHERE MembershipID=?";
        return db.executeUpdate(sql, memberID, planID, startDate, endDate, isActive, membershipID) > 0;
    }

    // Delete a membership
    public boolean delete(int membershipID) {
        String sql = "DELETE FROM Membership WHERE MembershipID=?";
        return db.executeUpdate(sql, membershipID) > 0;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "Membership{" +
                "membershipID=" + membershipID +
                ", memberID=" + memberID +
                ", planID=" + planID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isActive=" + isActive +
                '}';
    }
}