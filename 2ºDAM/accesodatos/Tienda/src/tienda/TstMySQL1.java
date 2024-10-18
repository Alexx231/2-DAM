package tienda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TstMySQL1 {
	public static void main(String[] args) {
		// Paso 1: Cargar el driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha encontrado el driver para MySQL");
			return;
		}
		System.out.println("Se ha cargado el Driver de MySQL");
		// Paso 2: Establecer conexión con la base de datos
		String cadenaConexion = "jdbc:mysql://localhost:3306/tienda";
		String user = "root";
		String pass = "Tcachuk93";
		Connection con;
		try {
			con = DriverManager.getConnection(cadenaConexion, user, pass);
		} catch (SQLException e) {
			System.err.println("Error en la conexión con la BD");
			System.err.println(e.getMessage());
			return;
		}
		System.out.println("Se ha establecido la conexión con la Base de datos");

		// Paso 3: Interactuar con la BD
		//	Recorremos la tabla en orden
		System.out.println("Recorremos la tabla en orden"); 
		try {
			Statement sentencia = con.createStatement();
			ResultSet rs = sentencia.executeQuery("SELECT * FROM CLIENTE");
			while (rs.next()) {
				System.out.print(rs.getString("NIF"));
				System.out.print(" - ");
				System.out.print(rs.getString("NOMBRE"));
				System.out.print(" - ");
				System.out.print(rs.getString("TLF"));
				System.out.print(" - ");
				System.out.print(rs.getString("DOMICILIO"));
				System.out.print(" - ");
				System.out.print(rs.getString("CIUDAD"));
				System.out.println(); // Retorno de carro
			}
		} catch (SQLException e) {
			System.err.println("Error al realizar el listado de productos");
			System.err.println(e.getMessage());
		}
		
		// Paso 3: Interactuar con la BD
		// Recorremos la tabla en orden inverso
		System.out.println("Recorremos la tabla en orden inverso"); 
		try {
			Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = sentencia.executeQuery("SELECT * FROM CLIENTE" );
			boolean hayRegistros = rs.last();
			while (hayRegistros) {
				System.out.print(rs.getString("NIF"));
				System.out.print(" - "); 
				System.out.print(rs.getString("NOMBRE"));
				System.out.print(" - "); 
				System.out.print(rs.getString("TLF"));
				System.out.print(" - ");
				System.out.print(rs.getString("DOMICILIO"));
				System.out.print(" - ");
				System.out.print(rs.getString("CIUDAD"));
				System.out.println(); // Retorno de carro
				hayRegistros = rs.previous();
			}
		} catch (SQLException e) {
			System.err.println("Error al realizar el listado de clientes");
			System.err.println(e.getMessage());
		}
		
		// Paso 3: Interactuar con la BD
		try {
			Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = sentencia.executeQuery("SELECT * FROM CLIENTE WHERE NIF = '44444444C'");
			boolean existe = rs.next();

			//	Modificamos un registro de la tabla
			System.out.println("Modificamos un registro de la tabla"); 
			//modificamos los valores
			if (existe) {
				rs.updateString("DOMICILIO", "C/ PEÑAGRANDE, 52");
				rs.updateString("TLF", "123456789");
				rs.updateRow();
				System.out.print(rs.getString("NIF"));
				System.out.print(" - "); 
				System.out.print(rs.getString("NOMBRE"));
				System.out.print(" - "); 
				System.out.print(rs.getString("TLF"));
				System.out.print(" - ");
				System.out.print(rs.getString("DOMICILIO"));
				System.out.print(" - ");
				System.out.print(rs.getString("CIUDAD"));
				System.out.println(); // Retorno de carro
				System.out.println("Registro modificado");
			}

			//restauramos los valores modificados
			System.out.println("Restauramos los valores modificados"); 
			rs = sentencia.executeQuery("SELECT * FROM CLIENTE WHERE NIF = '44444444C'");
			existe = rs.next();
			if (existe) {
				rs.updateString("DOMICILIO", "C/ OCA, 9");
				rs.updateString("TLF", "67778877");
				rs.updateRow();
				System.out.print(rs.getString("NIF"));
				System.out.print(" - "); 
				System.out.print(rs.getString("NOMBRE"));
				System.out.print(" - "); 
				System.out.print(rs.getString("TLF"));
				System.out.print(" - ");
				System.out.print(rs.getString("DOMICILIO"));
				System.out.print(" - ");
				System.out.print(rs.getString("CIUDAD"));
				System.out.println(); // Retorno de carro
				System.out.println("Registro modificado");
			}
		} catch (SQLException e) {
			System.err.println("Error al actualizar los datos del cliente");
			System.err.println(e.getMessage());
		}		

		// Paso 3: Interactuar con la BD
		// Insertamos un nuevo registro
		System.out.println("Insertamos un nuevo registro");
		try {
			Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = sentencia.executeQuery("SELECT * FROM CLIENTE");

			rs.moveToInsertRow(); 
			rs.updateString("NIF", "99999999Z"); 
			rs.updateString("NOMBRE", "HIPOLITO DURAN CARLOS"); 
			rs.updateString("DOMICILIO", "C/ PEZ VOLADOR, 52"); 
			rs.updateString("TLF", "912222222"); 
			rs.updateString("CIUDAD", "MADRID"); 
			rs.insertRow(); 

			System.out.print(rs.getString("NIF"));
			System.out.print(" - "); 
			System.out.print(rs.getString("NOMBRE"));
			System.out.print(" - "); 
			System.out.print(rs.getString("TLF"));
			System.out.print(" - ");
			System.out.print(rs.getString("DOMICILIO"));
			System.out.print(" - ");
			System.out.print(rs.getString("CIUDAD"));
			System.out.println(); // Retorno de carro

		} catch (SQLException e) {
			System.err.println("Error al añadir el nuevo cliente");
			System.err.println(e.getMessage());
		}		

		// Paso 3: Interactuar con la BD
		// Eliminamos el registro insertado
		System.out.println("Eliminamos el registro insertado");
		try {
			Statement sentencia = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = sentencia.executeQuery("SELECT * FROM CLIENTE WHERE NIF = '99999999Z'");
			boolean existe = rs.next();
			if (existe) {
				rs.deleteRow();
				System.out.println("Registro eliminado");
			}
		} catch (SQLException e) {
			System.out.println("Error al eliminar al cliente");
			System.out.println(e.getMessage());
		}		
		
		
		// Paso 4: Cerrar la conexión
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("No se ha podido cerrar la conexión con la BD");
			System.err.println(e.getMessage());
			return;
		}
		System.out.println("Se ha cerrado la base de datos");
	}
}