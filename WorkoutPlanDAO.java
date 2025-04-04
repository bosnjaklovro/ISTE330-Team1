import java.sql.Date;

public class WorkoutPlanDAO {
    private MySQLDatabase db;

    public WorkoutPlanDAO(MySQLDatabase db) {
        this.db = db;
    }

    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    public boolean create(int memberId, int trainerId, String planName, java.sql.Date startDate,
            java.sql.Date endDate) {
        String sql = "INSERT INTO WorkoutPlan (MemberID, TrainerID, PlanName, StartDate, EndDate) VALUES (?, ?, ?, ?, ?)";
        return db.executeUpdate(sql, memberId, trainerId, planName, startDate, endDate) > 0;
    }

    public boolean update(int workoutPlanId, int memberId, int trainerId, String planName, Date startDate,
            Date endDate) {
        String sql = "UPDATE WorkoutPlan SET MemberID=?, TrainerID=?, PlanName=?, StartDate=?, EndDate=? WHERE WorkoutPlanID=?";
        return db.executeUpdate(sql, memberId, trainerId, planName, startDate, endDate, workoutPlanId) > 0;
    }

    public boolean delete(int workoutPlanId) {
        String sql = "DELETE FROM WorkoutPlan WHERE WorkoutPlanID=?";
        return db.executeUpdate(sql, workoutPlanId) > 0;
    }

    public void getPlansByMember(int memberId) {
        db.getData("SELECT * FROM WorkoutPlan WHERE MemberID=?", memberId);
    }

    public void getAllPlans() {
        db.getData("SELECT * FROM WorkoutPlan");
    }
}