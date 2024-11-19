package bookworld.dao;

import bookworld.modelos.Cliente;
import bookworld.config.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
	
	public void crear(Cliente cliente) throws SQLException {
        // Primero verificamos si el email ya existe
        if (existeEmail(cliente.getEmail())) {
            throw new SQLException("Ya existe un cliente con el email: " + cliente.getEmail());
        }

        String sql = "INSERT INTO cliente (nombre, apellidos, email, telefono, direccion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getDireccion());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }
        }
    }
	
	// Método auxiliar para verificar si existe el email
    private boolean existeEmail(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM cliente WHERE email = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        }
    }
    
    public Cliente buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("direccion")
                    );
                }
                return null;
            }
        }
    }
    
    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("direccion")
                ));
            }
        }
        return clientes;
    }
    
    public void actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nombre=?, apellidos=?, email=?, telefono=?, direccion=? WHERE id_cliente=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getDireccion());
            stmt.setInt(6, cliente.getId());
            stmt.executeUpdate();
        }
    }
    
    public void eliminar(Integer id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id_cliente=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}