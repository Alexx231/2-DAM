package ejercicios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        int[] bases = {5, 6, 7};

        List<Process> procesos = new ArrayList<>();

        try {
            for (int base : bases) {

                ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", "C:\\2ºDAM\\psp\\Actividad6\\bin", "ejercicios.ActividadSeis", String.valueOf(base));
                processBuilder.redirectOutput(new File("triangulo_" + base + ".txt"));
                processBuilder.redirectError(new File("errores_" + base + ".txt"));

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