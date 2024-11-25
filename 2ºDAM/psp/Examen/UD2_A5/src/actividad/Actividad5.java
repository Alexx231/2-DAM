package actividad;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Actividad5 {
    public static void main(String[] args) {
        // Listas para almacenar los caballos de cada carrera
        List<Caballo> caballosCarrera1 = new ArrayList<>();
        List<Caballo> caballosCarrera2 = new ArrayList<>();
        
        // CountDownLatch para sincronizar la finalización de las carreras
        CountDownLatch latch1 = new CountDownLatch(5);
        CountDownLatch latch2 = new CountDownLatch(5);
        
        // Listas sincronizadas para almacenar los resultados de las carreras
        List<String> resultadosCarrera1 = Collections.synchronizedList(new ArrayList<>());
        List<String> resultadosCarrera2 = Collections.synchronizedList(new ArrayList<>());

        // Creación de los caballos para la primera carrera
        for (int i = 1; i <= 5; i++) {
            caballosCarrera1.add(new Caballo("Caballo" + i, latch1, resultadosCarrera1));
            caballosCarrera2.add(new Caballo("Caballo" + (i + 5), latch2, resultadosCarrera2));
        }

        // Inicio de los hilos para la primera carrera
        for (Caballo caballo : caballosCarrera1) {
            new Thread(caballo).start();
        }

        // Inicio de los hilos para la segunda carrera
        for (Caballo caballo : caballosCarrera2) {
            new Thread(caballo).start();
        }

        try {
            // Espera a que todos los caballos terminen la primera carrera
            latch1.await();
            // Espera a que todos los caballos terminen la segunda carrera
            latch2.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Escribe los resultados de la primera carrera en un archivo
        escribirResultados("resultadosCarrera1.txt", resultadosCarrera1);
        // Escribe los resultados de la segunda carrera en un archivo
        escribirResultados("resultadosCarrera2.txt", resultadosCarrera2);
    }

    // Método para escribir los resultados en un archivo
    private static void escribirResultados(String archivo, List<String> resultados) {
        try (FileWriter writer = new FileWriter(archivo)) {
            for (String resultado : resultados) {
                writer.write(resultado + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}