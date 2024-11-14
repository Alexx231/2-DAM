// ServidorLibros.java
package servidor;

import datos.LibroDAO;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

import datos.ConexionBD;
import java.util.List;

public class ServidorLibros implements IServicioLibros {
    private LibroDAO libroDAO;

    public ServidorLibros() {
        libroDAO = new LibroDAO();
    }

    @Override
    public List<String> buscarLibros(String clave) {
        return libroDAO.buscarLibros(clave);
    }

    public static void main(String[] args) {
        try {
            // Probar conexión
            System.out.println("Probando conexión a la base de datos...");
            Connection conn = ConexionBD.obtenerConexion();
            
            // Iniciar servidor
            ServidorLibros servidor = new ServidorLibros();
            IServicioLibros stub = (IServicioLibros) UnicastRemoteObject.exportObject(servidor, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ServicioLibros", stub);


            System.out.println("Servidor listo...");
        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}