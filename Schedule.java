
import java.time.LocalDateTime;

public class Schedule {

    private int scheduleId;
    private int classId;
    private int trainerId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;
    private MySQLDatabase db;

    public Schedule() {
        db = new MySQLDatabase();
    }

    // Constructor with parameters
    public Schedule(int classId, int trainerId, LocalDateTime startDateTime, LocalDateTime endDateTime, String location) {
        this.classId = classId;
        this.trainerId = trainerId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        db = new MySQLDatabase();
    }

    // Getters and setters
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Create - Insert a new schedule
    public boolean create() {
        if (!db.connect()) {
            return false;
        }

        String sql = "INSERT INTO Schedule (ClassID, TrainerID, StartDateTime, EndDateTime, Location) VALUES ("
                + classId + ", " + trainerId + ", '" + startDateTime + "', '" + endDateTime + "', '" + location + "')";
        db.getData(sql);

        // Get the last inserted ID
        sql = "SELECT LAST_INSERT_ID() as id";
        db.getData(sql);

        db.close();
        return true;
    }

    // Read - Get a schedule by ID
    public boolean read(int id) {
        if (!db.connect()) {
            return false;
        }

        String sql = "SELECT * FROM Schedule WHERE ScheduleID = " + id;
        db.getData(sql);
        db.close();
        return true;
    }

    // Update - Update an existing schedule
    public boolean update() {
        if (!db.connect()) {
            return false;
        }

        String sql = "UPDATE Schedule SET ClassID = " + classId + ", TrainerID = " + trainerId
                + ", StartDateTime = '" + startDateTime + "', EndDateTime = '" + endDateTime
                + "', Location = '" + location + "' WHERE ScheduleID = " + scheduleId;
        db.getData(sql);
        db.close();
        return true;
    }

    // Delete - Delete a schedule
    public boolean delete() {
        if (!db.connect()) {
            return false;
        }

        String sql = "DELETE FROM Schedule WHERE ScheduleID = " + scheduleId;
        db.getData(sql);
        db.close();
        return true;
    }
}
