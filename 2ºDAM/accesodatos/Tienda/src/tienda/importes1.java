package tienda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class importes1 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tienda";
        String user = "root";
        String password = "Tcachuk93";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String queryClientes = "SELECT NIF, nombre FROM CLIENTE";
            PreparedStatement stmtClientes = conn.prepareStatement(queryClientes);
            ResultSet rsClientes = stmtClientes.executeQuery();

            while (rsClientes.next()) {
                String nif = rsClientes.getString("NIF");
                String nombre = rsClientes.getString("NOMBRE");

                String queryFacturas = "SELECT NUMERO FROM FACTURA WHERE NIF = ?";
                PreparedStatement stmtFacturas = conn.prepareStatement(queryFacturas);
                stmtFacturas.setString(1, nif);
                ResultSet rsFacturas = stmtFacturas.executeQuery();

                double importeTotal = 0;

                while (rsFacturas.next()) {
                    int facturaNumero = rsFacturas.getInt("NUMERO");

                    String queryDetalles = "SELECT CODIGO, UNIDADES, PRECIO FROM DETALLE WHERE NUMERO = ?";
                    PreparedStatement stmtDetalles = conn.prepareStatement(queryDetalles);
                    stmtDetalles.setInt(1, facturaNumero);
                    ResultSet rsDetalles = stmtDetalles.executeQuery();

                    while (rsDetalles.next()) {
                        String productoCodigo = rsDetalles.getString("CODIGO");
                        int unidades = rsDetalles.getInt("UNIDADES");
                        double precio = rsDetalles.getDouble("PRECIO");

                        importeTotal += precio * unidades;
                    }
                }

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