package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    protected static final String URL = "jdbc:mysql://localhost:3306/wordpress";
    protected static final String USER = "wordpress";
    protected static final String PASSWORD = "wordpress";

    protected Connection connectionToDatabase() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
