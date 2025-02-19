package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Crear el registro RMI en el puerto 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Crear una instancia del servicio
            ServicioCancionesImpl servicio = new ServicioCancionesImpl();
            
            // Registrar el servicio en el registro RMI
            registry.rebind("ServicioCanciones", servicio);
            
            System.out.println("Servidor RMI iniciado");
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}