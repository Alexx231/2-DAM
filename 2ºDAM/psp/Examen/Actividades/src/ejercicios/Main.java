package ejercicios;

import java.io.*;

public class Main {

    public static void main(String[] args) {
    	
        File inputFile = new File("C:\\Users\\CAMPUSFP\\Desktop\\input.txt");
        File outputFile = new File("salida.txt");
        File errorFile = new File("errores.txt");

        try {

            ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", "C:\\2ºDAM\\psp\\Actividades\\bin","ejercicios.ActividadCuatro");
            processBuilder.redirectInput(inputFile);
            processBuilder.redirectOutput(outputFile);
            processBuilder.redirectError(errorFile);


            Process process = processBuilder.start();


            int exitCode = process.waitFor();
            System.out.println("El proceso terminó con el código de salida: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}