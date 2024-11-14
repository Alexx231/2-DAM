// ServidorManager.java
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class ServidorManager {
    private static Registry registry;
    private static IServicioLibros stub;
    private static ServidorLibros servidor;
    private static Connection conexion;
    private static final int PUERTO = 1099;

    private static boolean isPuertoOcupado() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    private static void liberarPuerto() {
        try {
            // Matar proceso que usa el puerto (Windows)
            Process process = Runtime.getRuntime().exec("cmd /c netstat -ano | findstr " + PUERTO);
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            
            String line;
            String pid = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains("LISTENING")) {
                    pid = line.substring(line.lastIndexOf(" ")).trim();
                    break;
                }
            }
            
            if (!pid.isEmpty()) {
                Runtime.getRuntime().exec("taskkill /F /PID " + pid);
                Thread.sleep(1000); // Esperar a que se libere
                System.out.println("Proceso con PID " + pid + " terminado");
            }
        } catch (Exception e) {
            System.err.println("Error al liberar puerto: " + e.getMessage());
        }
    }

    public static void pararServidor() {
        try {
            System.out.println("Iniciando proceso de parada del servidor...");

            // Liberar puerto si está ocupado
            if (isPuertoOcupado()) {
                System.out.println("Puerto " + PUERTO + " ocupado. Intentando liberar...");
                liberarPuerto();
            }

            // Limpiar recursos RMI
            if (registry != null) {
                try {
                    registry.unbind("ServicioLibros");
                    UnicastRemoteObject.unexportObject(servidor, true);
                    UnicastRemoteObject.unexportObject(registry, true);
                } catch (Exception e) {
                    System.out.println("Recursos RMI ya liberados");
                }
            }

            // Cerrar conexión BD
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión BD cerrada");
            }

            // Verificación final
            if (!isPuertoOcupado()) {
                System.out.println("Puerto " + PUERTO + " liberado exitosamente");
            }

            System.out.println("Servidor detenido correctamente");
            System.exit(0);
        } catch (Exception e) {
            System.err.println("Error al detener servidor: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        System.out.println("Iniciando cierre forzado del servidor...");
        pararServidor();
    }
}