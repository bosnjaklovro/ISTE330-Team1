package src;

import java.sql.Timestamp;

public class FeedbackDAO {
    private MySQLDatabase db;

    public FeedbackDAO(MySQLDatabase db) {
        this.db = db;
    }

    public boolean create(int memberId, int trainerId, int classId, int rating, String comment, Timestamp date) {
        String sql = "INSERT INTO Feedback (MemberID, TrainerID, ClassID, Rating, Comment, Date) VALUES (?, ?, ?, ?, ?, ?)";
        return db.executeUpdate(sql, memberId, trainerId, classId, rating, comment, date) > 0;
    }

    public boolean update(int feedbackId, int rating, String comment) {
        String sql = "UPDATE Feedback SET Rating=?, Comment=? WHERE FeedbackID=?";
        return db.executeUpdate(sql, rating, comment, feedbackId) > 0;
    }

    public boolean delete(int feedbackId) {
        String sql = "DELETE FROM Feedback WHERE FeedbackID=?";
        return db.executeUpdate(sql, feedbackId) > 0;
    }

    public void getFeedbackForTrainer(int trainerId) {
        db.getData("SELECT * FROM Feedback WHERE TrainerID=?", trainerId);
    }

    public void getClassFeedback(int classId) {
        db.getData("SELECT * FROM Feedback WHERE ClassID=?", classId);
    }
}