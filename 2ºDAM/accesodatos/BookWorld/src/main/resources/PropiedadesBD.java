package bookworld.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Properties properties = new Properties();
    
    static {
        try {
            properties.load(DatabaseConnection.class.getResourceAsStream("/database.properties"));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar database.properties", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            properties.getProperty("db.url"),
            properties.getProperty("db.user"),
            properties.getProperty("db.password")
        );
    }
}