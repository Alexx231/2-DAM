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

}