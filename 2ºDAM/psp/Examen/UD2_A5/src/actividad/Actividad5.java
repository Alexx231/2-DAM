package actividad;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Actividad5 {
    public static void main(String[] args) {
        List<Caballo> caballosCarrera1 = new ArrayList<>();
        List<Caballo> caballosCarrera2 = new ArrayList<>();
        CountDownLatch latch1 = new CountDownLatch(5);
        CountDownLatch latch2 = new CountDownLatch(5);
        List<String> resultadosCarrera1 = Collections.synchronizedList(new ArrayList<>());
        List<String> resultadosCarrera2 = Collections.synchronizedList(new ArrayList<>());

        for (int i = 1; i <= 5; i++) {
            caballosCarrera1.add(new Caballo("Caballo" + i, latch1, resultadosCarrera1));
            caballosCarrera2.add(new Caballo("Caballo" + (i + 5), latch2, resultadosCarrera2));
        }

        for (Caballo caballo : caballosCarrera1) {
            new Thread(caballo).start();
        }
        for (Caballo caballo : caballosCarrera2) {
            new Thread(caballo).start();
        }

        try {
            latch1.await();
            latch2.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        escribirResultados("resultadosCarrera1.txt", resultadosCarrera1);
        escribirResultados("resultadosCarrera2.txt", resultadosCarrera2);
    }

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