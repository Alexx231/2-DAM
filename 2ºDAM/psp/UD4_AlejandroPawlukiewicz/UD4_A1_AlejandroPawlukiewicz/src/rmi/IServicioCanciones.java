package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServicioCanciones extends Remote {
    List<Cancion> listarCanciones() throws RemoteException;
    Cancion buscarPorTitulo(String titulo) throws RemoteException;
    Cancion obtenerDetalles(int id) throws RemoteException;
}