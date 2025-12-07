import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/SurveyDB?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // your MySQL username
    private static final String PASSWORD = "88888888"; // your MySQL password

    public static Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found");
        }
    }
}
