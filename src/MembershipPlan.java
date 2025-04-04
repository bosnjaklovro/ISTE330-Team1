package src;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MembershipPlan {
    private int planID;
    private String planName;
    private String description;
    private int durationMonths;
    private double price;
    private int maxClasses;
    private MySQLDatabase db;

    // Constructor
    public MembershipPlan(int planID, String planName, String description, int durationMonths, double price, int maxClasses) {
        this.planID = planID;
        this.planName = planName;
        this.description = description;
        this.durationMonths = durationMonths;
        this.price = price;
        this.maxClasses = maxClasses;
    }

    // Getters
    public int getPlanID() {
        return planID;
    }

    public String getPlanName() {
        return planName;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public double getPrice() {
        return price;
    }

    public int getMaxClasses() {
        return maxClasses;
    }

    // Setters
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMaxClasses(int maxClasses) {
        this.maxClasses = maxClasses;
    }

    // Set database connection
    public void setDb(MySQLDatabase db) {
        this.db = db;
    }

    // Create a new membership plan
    public boolean create(String planName, String description, int durationMonths, double price, int maxClasses) {
        String sql = "INSERT INTO MembershipPlan (PlanName, Description, DurationMonths, Price, MaxClasses) VALUES (?, ?, ?, ?, ?)";
        return db.executeUpdate(sql, planName, description, durationMonths, price, maxClasses) > 0;
    }

    // Get membership plan by ID
    public MembershipPlan getById(int planId) {
        String sql = "SELECT * FROM MembershipPlan WHERE PlanID = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, planId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new MembershipPlan(
                            rs.getInt("PlanID"),
                            rs.getString("PlanName"),
                            rs.getString("Description"),
                            rs.getInt("DurationMonths"),
                            rs.getDouble("Price"),
                            rs.getInt("MaxClasses")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Get membership plan error: " + e.getMessage());
        }
        return null;
    }

    // Update membership plan details
    public boolean update(int planID, String planName, String description, int durationMonths, double price, int maxClasses) {
        String sql = "UPDATE MembershipPlan SET PlanName=?, Description=?, DurationMonths=?, Price=?, MaxClasses=? WHERE PlanID=?";
        return db.executeUpdate(sql, planName, description, durationMonths, price, maxClasses, planID) > 0;
    }

    // Delete a membership plan
    public boolean delete(int planID) {
        String sql = "DELETE FROM MembershipPlan WHERE PlanID=?";
        return db.executeUpdate(sql, planID) > 0;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "MembershipPlan{" +
                "planID=" + planID +
                ", planName='" + planName + '\'' +
                ", description='" + description + '\'' +
                ", durationMonths=" + durationMonths +
                ", price=" + price +
                ", maxClasses=" + maxClasses +
                '}';
    }
}