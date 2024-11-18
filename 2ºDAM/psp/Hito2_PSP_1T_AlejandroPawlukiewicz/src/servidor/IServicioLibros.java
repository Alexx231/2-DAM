// IServicioLibros.java
package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServicioLibros extends Remote {
    List<String> buscarLibros(String clave) throws RemoteException;
}