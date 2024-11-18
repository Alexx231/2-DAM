package bookworld.dao;

import bookworld.modelos.Cliente;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.List;

public class ClienteDAOTest {
    private ClienteDAO clienteDAO;
    private Cliente clientePrueba;
    
    @BeforeEach
    void setUp() {
        clienteDAO = new ClienteDAO();
        clientePrueba = new Cliente(null, "Test", "Usuario", "test@email.com", "555-1234", "Test Address");
    }
    
    @Test
    void testCrearCliente() {
        assertDoesNotThrow(() -> {
            clienteDAO.crear(clientePrueba);
            assertNotNull(clientePrueba.getId());
        });
    }
    
    @Test
    void testBuscarPorId() throws SQLException {
        clienteDAO.crear(clientePrueba);
        Cliente clienteEncontrado = clienteDAO.buscarPorId(clientePrueba.getId());
        assertNotNull(clienteEncontrado);
        assertEquals(clientePrueba.getEmail(), clienteEncontrado.getEmail());
    }
    
    @Test
    void testListarClientes() throws SQLException {
        clienteDAO.crear(clientePrueba);
        List<Cliente> clientes = clienteDAO.listarTodos();
        assertFalse(clientes.isEmpty());
    }
    
    @Test
    void testActualizarCliente() throws SQLException {
        clienteDAO.crear(clientePrueba);
        clientePrueba.setNombre("Test Actualizado");
        clienteDAO.actualizar(clientePrueba);
        Cliente clienteActualizado = clienteDAO.buscarPorId(clientePrueba.getId());
        assertEquals("Test Actualizado", clienteActualizado.getNombre());
    }
    
    @Test
    void testEliminarCliente() throws SQLException {
        clienteDAO.crear(clientePrueba);
        Integer id = clientePrueba.getId();
        clienteDAO.eliminar(id);
        assertNull(clienteDAO.buscarPorId(id));
    }
}