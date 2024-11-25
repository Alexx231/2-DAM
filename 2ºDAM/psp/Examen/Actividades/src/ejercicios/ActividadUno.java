package ejercicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ActividadUno {
    
    public static void main(String[] args) {
        try {

            Process proceso = Runtime.getRuntime().exec("tasklist.exe");
            
            // Crear un BufferedReader para leer la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            
            // Declarar una variable para almacenar cada línea leída
            String linea;
            
            // Mientras haya líneas para leer
            while ((linea = reader.readLine()) != null) {
                // Imprimir la línea leída en la consola
                System.out.println(linea);
            }
            
            // Cerrar el BufferedReader
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}