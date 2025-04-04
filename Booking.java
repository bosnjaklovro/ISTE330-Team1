
import java.time.LocalDateTime;

public class Booking {

    private int bookingId;
    private int memberId;
    private int scheduleId;
    private LocalDateTime bookingDate;
    private String status; // 'Booked' or 'Canceled'
    private MySQLDatabase db;

    public Booking() {
        db = new MySQLDatabase();
    }

    // Constructor with parameters
    public Booking(int memberId, int scheduleId, LocalDateTime bookingDate, String status) {
        this.memberId = memberId;
        this.scheduleId = scheduleId;
        this.bookingDate = bookingDate;
        this.status = status;
        db = new MySQLDatabase();
    }

    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Create - Insert a new booking
    public boolean create() {
        if (!db.connect()) {
            return false;
        }

        String sql = "INSERT INTO Booking (MemberID, ScheduleID, BookingDate, Status) VALUES ("
                + memberId + ", " + scheduleId + ", '" + bookingDate + "', '" + status + "')";
        db.getData(sql);

        // Get the last inserted ID
        sql = "SELECT LAST_INSERT_ID() as id";
        db.getData(sql);

        db.close();
        return true;
    }

    // Read - Get a booking by ID
    public boolean read(int id) {
        if (!db.connect()) {
            return false;
        }

        String sql = "SELECT * FROM Booking WHERE BookingID = " + id;
        db.getData(sql);
        db.close();
        return true;
    }

    // Update - Update an existing booking
    public boolean update() {
        if (!db.connect()) {
            return false;
        }

        String sql = "UPDATE Booking SET MemberID = " + memberId + ", ScheduleID = " + scheduleId
                + ", BookingDate = '" + bookingDate + "', Status = '" + status
                + "' WHERE BookingID = " + bookingId;
        db.getData(sql);
        db.close();
        return true;
    }

    // Delete - Delete a booking
    public boolean delete() {
        if (!db.connect()) {
            return false;
        }

        String sql = "DELETE FROM Booking WHERE BookingID = " + bookingId;
        db.getData(sql);
        db.close();
        return true;
    }
}
