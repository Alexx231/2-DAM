package bookworld.dao;

import bookworld.modelos.Venta;
import bookworld.config.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {
    
    public void crear(Venta venta) throws SQLException {
        String sql = "INSERT INTO venta (fecha, total, cliente_id) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(venta.getFecha()));
            stmt.setBigDecimal(2, venta.getTotal());
            stmt.setInt(3, venta.getClienteId());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    venta.setId(rs.getInt(1));
                }
            }
        }
    }

    public Venta buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM venta WHERE id_venta = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Venta(
                        rs.getInt("id_venta"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getBigDecimal("total"),
                        rs.getInt("cliente_id")
                    );
                }
                return null;
            }
        }
    }

    public List<Venta> listarTodos() throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM venta ORDER BY fecha DESC";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ventas.add(new Venta(
                    rs.getInt("id_venta"),
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    rs.getBigDecimal("total"),
                    rs.getInt("cliente_id")
                ));
            }
        }
        return ventas;
    }

    public void actualizar(Venta venta) throws SQLException {
        String sql = "UPDATE venta SET fecha=?, total=?, cliente_id=? WHERE id_venta=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(venta.getFecha()));
            stmt.setBigDecimal(2, venta.getTotal());
            stmt.setInt(3, venta.getClienteId());
            stmt.setInt(4, venta.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminar(Integer id) throws SQLException {
        String sql = "DELETE FROM venta WHERE id_venta=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}