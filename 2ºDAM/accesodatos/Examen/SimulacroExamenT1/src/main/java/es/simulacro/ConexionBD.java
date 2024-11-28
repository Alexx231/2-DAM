package es.simulacro;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	public static Connection conexion=null;
	
	public static Connection getConexion() {
		if (conexion==null) conectar();
		return conexion;
	}
	
	private static Connection conectar() {
		// Paso 1: Cargar el driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha encontrado el driver para MySQL");
			return null;
		}
		System.out.println("Se ha cargado el Driver de MySQL");
		
		
		// Paso 2: Establecer conexión con la base de datos
		String cadenaConexion = "jdbc:mysql://localhost:3306/libreria";
		String user = "root";
		String pass = "campusfp"; 
		try {
			conexion = DriverManager.getConnection(cadenaConexion, user, pass);
		} catch (SQLException e) {
			System.err.println("Error en la conexión con la BD");
			System.err.println(e.getMessage());
			return null;
		}
		
		System.out.println("Se ha establecido la conexión con la Base de datos");
		return conexion;
	}
	
	public static boolean cerrar() {
		if (conexion==null) return false;
		try {
			conexion.close();
		} catch (SQLException e) {
			return false; // No se ha podido cerrar la conexión.
		}
		System.out.println("Se ha cerrado la conexión con la BD");
		return true; // Se ha cerrado la conexión.
	}
}
