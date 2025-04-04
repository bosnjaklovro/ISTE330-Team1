public class GymApp {
    public static void main(String[] args) {
        MySQLDatabase db = new MySQLDatabase();

        if (db.connect()) {
            System.out.println("Connected to gymDB.");

            //test query
            db.getData("SELECT * FROM User");

            db.close();
        } else {
            System.out.println("Failed to connect to database.");
        }
    }
}
