package tfg.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBdd {
    private static final String URL = "jdbc:mysql://localhost:3306/ecomerch";
    private static final String USER = "root";
    private static final String PASSWORD = "Tcachuk93";
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Error conectando a la base de datos", e);
        }
    }
}