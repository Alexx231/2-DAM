package bookworld.dao;

import bookworld.config.ConexionBD;
import java.sql.*;
import java.util.*;

public class ReportesDAO {
    
    public List<Map<String, Object>> getVentasPorMes() throws SQLException {
        List<Map<String, Object>> resultado = new ArrayList<>();
        String sql = "SELECT DATE_FORMAT(fecha, '%Y-%m') as mes, " +
                    "COUNT(*) as total_ventas, SUM(total) as monto_total " +
                    "FROM venta GROUP BY mes ORDER BY mes DESC";
        
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("mes", rs.getString("mes"));
                fila.put("total_ventas", rs.getInt("total_ventas"));
                fila.put("monto_total", rs.getBigDecimal("monto_total"));
                resultado.add(fila);
            }
        }
        return resultado;
    }
    
    public List<Map<String, Object>> getLibrosMasVendidos(int limite) throws SQLException {
        String sql = "SELECT l.isbn, l.titulo, l.autor, " +
                    "SUM(dv.cantidad) as total_vendido " +
                    "FROM libro l " +
                    "JOIN detalle_venta dv ON l.isbn = dv.libro_isbn " +
                    "GROUP BY l.isbn, l.titulo, l.autor " +
                    "ORDER BY total_vendido DESC LIMIT ?";
                    
        List<Map<String, Object>> resultado = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limite);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    fila.put("isbn", rs.getString("isbn"));
                    fila.put("titulo", rs.getString("titulo"));
                    fila.put("autor", rs.getString("autor"));
                    fila.put("total_vendido", rs.getInt("total_vendido"));
                    resultado.add(fila);
                }
            }
        }
        return resultado;
    }

    public List<Map<String, Object>> getVentasPorCliente() throws SQLException {
        String sql = "SELECT c.id_cliente, c.nombre, c.apellidos, " +
                    "COUNT(v.id_venta) as total_compras, " +
                    "SUM(v.total) as monto_total " +
                    "FROM cliente c " +
                    "LEFT JOIN venta v ON c.id_cliente = v.cliente_id " +
                    "GROUP BY c.id_cliente, c.nombre, c.apellidos " +
                    "ORDER BY monto_total DESC";
        
        List<Map<String, Object>> resultado = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id_cliente", rs.getInt("id_cliente"));
                fila.put("nombre", rs.getString("nombre"));
                fila.put("apellidos", rs.getString("apellidos"));
                fila.put("total_compras", rs.getInt("total_compras"));
                fila.put("monto_total", rs.getBigDecimal("monto_total"));
                resultado.add(fila);
            }
        }
        return resultado;
    }
}