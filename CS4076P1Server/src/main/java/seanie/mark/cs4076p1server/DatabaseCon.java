package seanie.mark.cs4076p1server;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseCon {
    private static final String URL = "jdbc:mysql://localhost:3306/cs4076p1";
    private static final String USER = "dev";
    private static final String PASSWORD = "dev";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}