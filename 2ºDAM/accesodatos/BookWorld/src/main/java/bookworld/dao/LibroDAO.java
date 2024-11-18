package bookworld.dao;

import bookworld.modelos.Libro;
import bookworld.config.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class LibroDAO {
    public void crear(Libro libro) throws SQLException {
        String sql = "INSERT INTO libro (isbn, titulo, autor, precio, stock, categoria_id, proveedor_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setString(3, libro.getAutor());
            stmt.setBigDecimal(4, libro.getPrecio());
            stmt.setInt(5, libro.getStock());
            stmt.setInt(6, libro.getCategoriaId());
            stmt.setInt(7, libro.getProveedorId());
            stmt.executeUpdate();
        }
    }

    public Libro buscarPorIsbn(String isbn) throws SQLException {
        String sql = "SELECT * FROM libro WHERE isbn = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Libro(
                        rs.getString("isbn"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getBigDecimal("precio"),
                        rs.getInt("stock"),
                        rs.getInt("categoria_id"),
                        rs.getInt("proveedor_id")
                    );
                }
                return null;
            }
        }
    }

    public List<Libro> listarTodos() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libro";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                libros.add(new Libro(
                    rs.getString("isbn"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getBigDecimal("precio"),
                    rs.getInt("stock"),
                    rs.getInt("categoria_id"),
                    rs.getInt("proveedor_id")
                ));
            }
        }
        return libros;
    }

    public void actualizar(Libro libro) throws SQLException {
        String sql = "UPDATE libro SET titulo=?, autor=?, precio=?, stock=?, categoria_id=?, proveedor_id=? WHERE isbn=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setBigDecimal(3, libro.getPrecio());
            stmt.setInt(4, libro.getStock());
            stmt.setInt(5, libro.getCategoriaId());
            stmt.setInt(6, libro.getProveedorId());
            stmt.setString(7, libro.getIsbn());
            stmt.executeUpdate();
        }
    }

    public void eliminar(String isbn) throws SQLException {
        String sql = "DELETE FROM libro WHERE isbn=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            stmt.executeUpdate();
        }
    }
}