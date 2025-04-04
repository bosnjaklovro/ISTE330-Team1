package src;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Attendance {
    private int attendanceId;
    private int bookingId;
    private String status;
    private MySQLDatabase db;

    public Attendance() {
        db = new MySQLDatabase();
    }

    public Attendance(int bookingId, String status) {
        this.bookingId = bookingId;
        this.status = status;
        db = new MySQLDatabase();
    }

    public int getAttendanceId() { return attendanceId; }
    public int getBookingId() { return bookingId; }
    public String getStatus() { return status; }

    public void setAttendanceId(int attendanceId) { this.attendanceId = attendanceId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setStatus(String status) { this.status = status; }

    public boolean create() {
        if (!db.connect()) return false;
        String sql = "INSERT INTO Attendance (BookingID, Status) VALUES (?, ?)";
        boolean result = db.executeUpdate(sql, bookingId, status) > 0;
        db.close();
        return result;
    }

    public boolean read(int id) {
        if (!db.connect()) return false;
        String sql = "SELECT * FROM Attendance WHERE AttendanceID = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    attendanceId = rs.getInt("AttendanceID");
                    bookingId = rs.getInt("BookingID");
                    status = rs.getString("Status");
                    db.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading Attendance: " + e.getMessage());
        }
        db.close();
        return false;
    }

    public boolean update() {
        if (!db.connect()) return false;
        String sql = "UPDATE Attendance SET BookingID = ?, Status = ? WHERE AttendanceID = ?";
        boolean result = db.executeUpdate(sql, bookingId, status, attendanceId) > 0;
        db.close();
        return result;
    }

    public boolean delete() {
        if (!db.connect()) return false;
        String sql = "DELETE FROM Attendance WHERE AttendanceID = ?";
        boolean result = db.executeUpdate(sql, attendanceId) > 0;
        db.close();
        return result;
    }
}
