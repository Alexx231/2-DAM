package ejercicios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Define las bases para los triángulos
        int[] bases = {5, 6, 7};

        // Lista para almacenar los procesos
        List<Process> procesos = new ArrayList<>();

        try {
            // Crea y ejecuta un proceso para cada base
            for (int base : bases) {

                // Configura el ProcessBuilder para ejecutar la clase ActividadSeis
                ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", "C:\\2ºDAM\\psp\\Actividad6\\bin", "ejercicios.ActividadSeis", String.valueOf(base));
                processBuilder.redirectOutput(new File("triangulo_" + base + ".txt"));
                processBuilder.redirectError(new File("errores_" + base + ".txt"));

                // Inicia el proceso y lo agrega a la lista
                Process proceso = processBuilder.start();
                procesos.add(proceso);
            }

            // Espera a que todos los procesos terminen
            for (Process proceso : procesos) {
                proceso.waitFor();
            }

            // Imprime un mensaje indicando que todos los procesos han terminado
            System.out.println("Todos los procesos han terminado.");

        } catch (IOException | InterruptedException e) {
            // Maneja las excepciones de E/S e interrupciones
            e.printStackTrace();
        }
    }
}