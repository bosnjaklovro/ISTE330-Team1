package src;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Equipment {
    private int equipmentId;
    private String equipmentName;
    private String description;
    private Date purchaseDate;
    private Date lastMaintenance;
    private MySQLDatabase db;

    public Equipment() {
        db = new MySQLDatabase();
    }

    public Equipment(String equipmentName, String description, Date purchaseDate, Date lastMaintenance) {
        this.equipmentName = equipmentName;
        this.description = description;
        this.purchaseDate = purchaseDate;
        this.lastMaintenance = lastMaintenance;
        db = new MySQLDatabase();
    }

    public int getEquipmentId() { return equipmentId; }
    public String getEquipmentName() { return equipmentName; }
    public String getDescription() { return description; }
    public Date getPurchaseDate() { return purchaseDate; }
    public Date getLastMaintenance() { return lastMaintenance; }

    public void setEquipmentId(int equipmentId) { this.equipmentId = equipmentId; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
    public void setDescription(String description) { this.description = description; }
    public void setPurchaseDate(Date purchaseDate) { this.purchaseDate = purchaseDate; }
    public void setLastMaintenance(Date lastMaintenance) { this.lastMaintenance = lastMaintenance; }

    public boolean create() {
        if (!db.connect()) return false;
        String sql = "INSERT INTO Equipment (EquipmentName, Description, PurchaseDate, LastMaintenance) VALUES (?, ?, ?, ?)";
        boolean result = db.executeUpdate(sql, equipmentName, description, purchaseDate, lastMaintenance) > 0;
        db.close();
        return result;
    }

    public boolean read(int id) {
        if (!db.connect()) return false;
        String sql = "SELECT * FROM Equipment WHERE EquipmentID = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    equipmentId = rs.getInt("EquipmentID");
                    equipmentName = rs.getString("EquipmentName");
                    description = rs.getString("Description");
                    purchaseDate = rs.getDate("PurchaseDate");
                    lastMaintenance = rs.getDate("LastMaintenance");
                    db.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading Equipment: " + e.getMessage());
        }
        db.close();
        return false;
    }

    public boolean update() {
        if (!db.connect()) return false;
        String sql = "UPDATE Equipment SET EquipmentName = ?, Description = ?, PurchaseDate = ?, LastMaintenance = ? WHERE EquipmentID = ?";
        boolean result = db.executeUpdate(sql, equipmentName, description, purchaseDate, lastMaintenance, equipmentId) > 0;
        db.close();
        return result;
    }

    public boolean delete() {
        if (!db.connect()) return false;
        String sql = "DELETE FROM Equipment WHERE EquipmentID = ?";
        boolean result = db.executeUpdate(sql, equipmentId) > 0;
        db.close();
        return result;
    }
}
