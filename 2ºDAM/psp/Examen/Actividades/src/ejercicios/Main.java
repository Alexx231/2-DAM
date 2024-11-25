package ejercicios;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        // Define los archivos de entrada, salida y error
        File inputFile = new File("C:\\Users\\CAMPUSFP\\Desktop\\input.txt");
        File outputFile = new File("salida.txt");
        File errorFile = new File("errores.txt");

        try {
            // Configura el ProcessBuilder para ejecutar la clase ActividadCuatro
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", "C:\\2ºDAM\\psp\\Actividades\\bin","ejercicios.ActividadCuatro");
            processBuilder.redirectInput(inputFile);
            processBuilder.redirectOutput(outputFile);
            processBuilder.redirectError(errorFile);

            // Inicia el proceso
            Process process = processBuilder.start();

            // Espera a que el proceso termine y obtiene el código de salida
            int exitCode = process.waitFor();
            System.out.println("El proceso terminó con el código de salida: " + exitCode);

        } catch (IOException | InterruptedException e) {
            // Maneja cualquier excepción que ocurra durante la ejecución del proceso
            e.printStackTrace();
        }
    }
}