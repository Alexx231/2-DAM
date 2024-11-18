package servidor;

import datos.LibroDAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServidorLibros extends UnicastRemoteObject implements IServicioLibros {
    private LibroDAO libroDAO;

    // Constructor que inicializa el DAO de libros
    public ServidorLibros() throws RemoteException {
        super();
        libroDAO = new LibroDAO();
    }

    // Implementación del método remoto para buscar libros
    @Override
    public List<String> buscarLibros(String clave) throws RemoteException {
        // Llama al método del DAO para buscar libros en la base de datos
        return libroDAO.buscarLibros(clave);
    }
}