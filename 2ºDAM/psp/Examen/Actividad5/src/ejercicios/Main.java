package ejercicios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        int[] numeros = {2, 3, 4};


        List<Process> procesos = new ArrayList<>();

        try {
            for (int numero : numeros) {

                ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp","C:\\2ºDAM\\psp\\Actividad5\\bin", "ejercicios.ActividadCinco", String.valueOf(numero));
                processBuilder.redirectOutput(new File("salida_" + numero + ".txt"));
                processBuilder.redirectError(new File("errores_" + numero + ".txt"));


                Process proceso = processBuilder.start();
                procesos.add(proceso);
            }


            for (Process proceso : procesos) {
                proceso.waitFor();
            }

            System.out.println("Todos los procesos han terminado.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}