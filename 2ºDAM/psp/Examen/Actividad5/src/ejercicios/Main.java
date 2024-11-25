package ejercicios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Define los números para los cuales se generarán las tablas de multiplicar
        int[] numeros = {2, 3, 4};

        // Lista para almacenar los procesos que se ejecutarán
        List<Process> procesos = new ArrayList<>();

        try {
            // Itera sobre cada número y crea un proceso para ejecutar ActividadCinco
            for (int numero : numeros) {

                // Configura el ProcessBuilder para ejecutar la clase ActividadCinco con el número actual
                ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp","C:\\2ºDAM\\psp\\Actividad5\\bin", "ejercicios.ActividadCinco", String.valueOf(numero));
                processBuilder.redirectOutput(new File("salida_" + numero + ".txt")); // Redirige la salida a un archivo
                processBuilder.redirectError(new File("errores_" + numero + ".txt")); // Redirige los errores a un archivo

                // Inicia el proceso y lo agrega a la lista de procesos
                Process proceso = processBuilder.start();
                procesos.add(proceso);
            }

            // Espera a que todos los procesos terminen
            for (Process proceso : procesos) {
                proceso.waitFor();
            }

            System.out.println("Todos los procesos han terminado.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}