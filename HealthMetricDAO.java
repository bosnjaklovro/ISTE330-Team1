import java.sql.Date;

public class HealthMetricDAO {
    private MySQLDatabase db;

    public HealthMetricDAO(MySQLDatabase db) {
        this.db = db;
    }

    public boolean create(int memberId, Date recordDate, double weight, double height, double bodyFat,
            String bloodPressure) {
        String sql = "INSERT INTO HealthMetric (MemberID, RecordDate, Weight, Height, BodyFat, BloodPressure) VALUES (?, ?, ?, ?, ?, ?)";
        return db.executeUpdate(sql, memberId, recordDate, weight, height, bodyFat, bloodPressure) > 0;
    }

    public boolean update(int metricId, Date recordDate, double weight, double height, double bodyFat,
            String bloodPressure) {
        String sql = "UPDATE HealthMetric SET RecordDate=?, Weight=?, Height=?, BodyFat=?, BloodPressure=? WHERE HealthMetricID=?";
        return db.executeUpdate(sql, recordDate, weight, height, bodyFat, bloodPressure, metricId) > 0;
    }

    public boolean delete(int metricId) {
        String sql = "DELETE FROM HealthMetric WHERE HealthMetricID=?";
        return db.executeUpdate(sql, metricId) > 0;
    }

    public void getMetricsByMember(int memberId) {
        db.getData("SELECT * FROM HealthMetric WHERE MemberID=?", memberId);
    }

    public void getRecentMetrics(int memberId, int limit) {
        db.getData("SELECT * FROM HealthMetric WHERE MemberID=? ORDER BY RecordDate DESC LIMIT ?", memberId, limit);
    }
}