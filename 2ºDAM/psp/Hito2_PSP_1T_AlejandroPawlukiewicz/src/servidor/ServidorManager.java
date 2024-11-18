package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServidorManager {
    private static final int PUERTO = 1099;
    private Registry registry;
    private ServidorLibros servidor;

    // Verifica si el puerto está bloqueado
    private boolean puertoBloqueado() {
        try {
            // Ejecuta un comando para verificar si el puerto está en uso
            ProcessBuilder pb = new ProcessBuilder(
                "cmd", "/c", "netstat", "-ano", "|", "findstr", String.valueOf(PUERTO));
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            
            // Si se encuentra una línea, el puerto está en uso
            return line != null && !line.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // Libera el puerto especificado matando el proceso que lo está usando
    private void liberarPuerto(String pid) {
        try {
            if (pid != null && !pid.isEmpty()) {
                // Ejecuta un comando para matar el proceso que está usando el puerto
                ProcessBuilder pb = new ProcessBuilder(
                    "taskkill", "/F", "/PID", pid);
                pb.start();
            }
        } catch (Exception e) {
            System.err.println("Error al liberar el puerto: " + e.getMessage());
        }
    }

    // Inicia el servidor RMI
    public void iniciarServidor() throws Exception {
        try {
            // Crea una instancia del servidor de libros
            servidor = new ServidorLibros();
            // Crea el registro RMI en el puerto especificado
            registry = LocateRegistry.createRegistry(PUERTO);
            // Exporta el objeto del servidor y lo vincula en el registro
            IServicioLibros stub = (IServicioLibros) UnicastRemoteObject.exportObject(servidor, 0);
            registry.rebind("ServicioLibros", stub);
        } catch (Exception e) {
            throw new Exception("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    // Detiene el servidor RMI
    public void detenerServidor() throws Exception {
        try {
            if (registry != null) {
                // Desvincula el objeto del servidor del registro
                registry.unbind("ServicioLibros");
                // Desexporta el objeto del servidor y el registro
                UnicastRemoteObject.unexportObject(servidor, true);
                UnicastRemoteObject.unexportObject(registry, true);
                registry = null;
                servidor = null;
            }
        } catch (Exception e) {
            throw new Exception("Error al detener el servidor: " + e.getMessage());
        }
    }
}