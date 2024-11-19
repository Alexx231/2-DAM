package bookworld.config;

import java.sql.*;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/bookworld";
    private static final String USER = "root";
    private static final String PASSWORD = "Tcachuk93";
    
    private static ConexionBD instance;
    private Connection connection;
    
    private ConexionBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver de MySQL", e);
        }
    }
    
    public static synchronized Connection getConnection() throws SQLException {
        if (instance == null) {
            instance = new ConexionBD();
        }
        if (instance.connection == null || instance.connection.isClosed()) {
            instance.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return instance.connection;
    }
}