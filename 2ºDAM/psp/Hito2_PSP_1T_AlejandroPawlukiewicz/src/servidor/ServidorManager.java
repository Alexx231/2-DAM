// src/servidor/ServidorManager.java
package servidor;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ServidorManager {
    private static final int PUERTO = 1099;
    private Registry registry;
    private ServidorLibros servidor;

    private boolean puertoBloqueado() {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                "cmd", "/c", "netstat", "-ano", "|", "findstr", String.valueOf(PUERTO));
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            
            return line != null && !line.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private void liberarPuerto(String pid) {
        try {
            if (pid != null && !pid.isEmpty()) {
                ProcessBuilder pb = new ProcessBuilder(
                    "taskkill", "/F", "/PID", pid);
                pb.start();
            }
        } catch (Exception e) {
            System.err.println("Error al liberar el puerto: " + e.getMessage());
        }
    }

    public void iniciarServidor() throws Exception {
        try {
            servidor = new ServidorLibros();
            registry = LocateRegistry.createRegistry(PUERTO);
            IServicioLibros stub = (IServicioLibros) UnicastRemoteObject.exportObject(servidor, 0);
            registry.rebind("ServicioLibros", stub);
        } catch (Exception e) {
            throw new Exception("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    public void detenerServidor() throws Exception {
        try {
            if (registry != null) {
                registry.unbind("ServicioLibros");
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