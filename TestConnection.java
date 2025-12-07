import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = Database.connect()) {
            System.out.println("CONNECTED!");

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM surveys");
            ResultSet rs = stmt.executeQuery();

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("ID: " + rs.getInt("survey_id") +
                        " | Title: " + rs.getString("title"));
            }

            if (!found) {
                System.out.println("NO SURVEYS FOUND IN TABLE!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
