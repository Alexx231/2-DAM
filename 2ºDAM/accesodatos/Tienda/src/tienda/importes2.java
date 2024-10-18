package tienda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class importes2 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tienda";
        String user = "root";
        String password = "Tcachuk93";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT c.NIF, c.NOMBRE, SUM(d.PRECIO * d.UNIDADES) AS importe_total " +
                           "FROM CLIENTE c " +
                           "JOIN FACTURA f ON c.NIF = f.NIF " +
                           "JOIN DETALLE d ON f.NUMERO = d.NUMERO " +
                           "GROUP BY c.NIF, c.NOMBRE";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nif = rs.getString("NIF");
                String nombre = rs.getString("NOMBRE");
                double importeTotal = rs.getDouble("importe_total");

                System.out.println("NIF: " + nif);
                System.out.println("Nombre: " + nombre);
                System.out.println("Importe Total: " + importeTotal);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos o al realizar la consulta");
            System.err.println(e.getMessage());
        }
    }
}