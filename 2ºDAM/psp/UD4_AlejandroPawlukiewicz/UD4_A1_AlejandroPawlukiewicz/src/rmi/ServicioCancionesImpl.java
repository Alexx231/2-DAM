package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServicioCancionesImpl extends UnicastRemoteObject implements IServicioCanciones {
    private List<Cancion> canciones;

    public ServicioCancionesImpl() throws RemoteException {
        canciones = new ArrayList<>();
        // Agregamos algunas canciones de ejemplo
        canciones.add(new Cancion(1, "Bohemian Rhapsody", "Queen", "A Night at the Opera", 1975));
        canciones.add(new Cancion(2, "Imagine", "John Lennon", "Imagine", 1971));
        canciones.add(new Cancion(3, "Hotel California", "Eagles", "Hotel California", 1977));
    }

    @Override
    public List<Cancion> listarCanciones() throws RemoteException {
        return canciones;
    }

    @Override
    public Cancion buscarPorTitulo(String titulo) throws RemoteException {
        return canciones.stream()
                .filter(c -> c.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Cancion obtenerDetalles(int id) throws RemoteException {
        return canciones.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}