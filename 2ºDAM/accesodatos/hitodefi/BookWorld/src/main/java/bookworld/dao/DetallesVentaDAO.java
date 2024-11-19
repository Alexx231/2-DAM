package bookworld.dao;

import bookworld.config.ConexionBD;
import bookworld.modelos.DetalleVenta;
import java.sql.*;
import java.math.BigDecimal;

public class DetallesVentaDAO {
    
    public void crear(DetalleVenta detalle) throws SQLException {
        String sql = "INSERT INTO detalle_venta (venta_id, libro_isbn, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getVentaId());
            stmt.setString(2, detalle.getLibroIsbn());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setBigDecimal(4, detalle.getPrecioUnitario());
            stmt.executeUpdate();
        }
    }
}