package src;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Payment {
    private int paymentId;
    private int membershipId;
    private double amount;
    private Timestamp paymentDate;
    private String method;
    private MySQLDatabase db;

    public Payment() {
        db = new MySQLDatabase();
    }

    public Payment(int membershipId, double amount, Timestamp paymentDate, String method) {
        this.membershipId = membershipId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
        db = new MySQLDatabase();
    }

    public int getPaymentId() { return paymentId; }
    public int getMembershipId() { return membershipId; }
    public double getAmount() { return amount; }
    public Timestamp getPaymentDate() { return paymentDate; }
    public String getMethod() { return method; }

    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public void setMembershipId(int membershipId) { this.membershipId = membershipId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPaymentDate(Timestamp paymentDate) { this.paymentDate = paymentDate; }
    public void setMethod(String method) { this.method = method; }

    public boolean create() {
        if (!db.connect()) return false;
        String sql = "INSERT INTO Payment (MembershipID, Amount, PaymentDate, Method) VALUES (?, ?, ?, ?)";
        boolean result = db.executeUpdate(sql, membershipId, amount, paymentDate, method) > 0;
        db.close();
        return result;
    }

    public boolean read(int id) {
        if (!db.connect()) return false;
        String sql = "SELECT * FROM Payment WHERE PaymentID = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    paymentId = rs.getInt("PaymentID");
                    membershipId = rs.getInt("MembershipID");
                    amount = rs.getDouble("Amount");
                    paymentDate = rs.getTimestamp("PaymentDate");
                    method = rs.getString("Method");
                    db.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading Payment: " + e.getMessage());
        }
        db.close();
        return false;
    }

    public boolean update() {
        if (!db.connect()) return false;
        String sql = "UPDATE Payment SET MembershipID = ?, Amount = ?, PaymentDate = ?, Method = ? WHERE PaymentID = ?";
        boolean result = db.executeUpdate(sql, membershipId, amount, paymentDate, method, paymentId) > 0;
        db.close();
        return result;
    }

    public boolean delete() {
        if (!db.connect()) return false;
        String sql = "DELETE FROM Payment WHERE PaymentID = ?";
        boolean result = db.executeUpdate(sql, paymentId) > 0;
        db.close();
        return result;
    }
}
