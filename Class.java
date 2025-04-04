
public class Class {

    private int classId;
    private String className;
    private String description;
    private int maxParticipants;
    private int duration;
    private MySQLDatabase db;

    public Class() {
        db = new MySQLDatabase();
    }

    // Constructor with parameters
    public Class(String className, String description, int maxParticipants, int duration) {
        this.className = className;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.duration = duration;
        db = new MySQLDatabase();
    }

    // Getters and setters
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Create - Insert a new class
    public boolean create() {
        if (!db.connect()) {
            return false;
        }

        String sql = "INSERT INTO Class (ClassName, Description, MaxParticipants, Duration) VALUES ('"
                + className + "', '" + description + "', " + maxParticipants + ", " + duration + ")";
        db.getData(sql);

        // Get the last inserted ID
        sql = "SELECT LAST_INSERT_ID() as id";
        db.getData(sql);

        db.close();
        return true;
    }

    // Read - Get a class by ID
    public boolean read(int id) {
        if (!db.connect()) {
            return false;
        }

        String sql = "SELECT * FROM Class WHERE ClassID = " + id;
        db.getData(sql);
        db.close();
        return true;
    }

    // Update - Update an existing class
    public boolean update() {
        if (!db.connect()) {
            return false;
        }

        String sql = "UPDATE Class SET ClassName = '" + className + "', Description = '" + description
                + "', MaxParticipants = " + maxParticipants + ", Duration = " + duration
                + " WHERE ClassID = " + classId;
        db.getData(sql);
        db.close();
        return true;
    }

    // Delete - Delete a class
    public boolean delete() {
        if (!db.connect()) {
            return false;
        }

        String sql = "DELETE FROM Class WHERE ClassID = " + classId;
        db.getData(sql);
        db.close();
        return true;
    }
}
