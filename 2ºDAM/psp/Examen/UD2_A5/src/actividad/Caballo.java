package actividad;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Caballo implements Runnable {
    private String nombre;
    private CountDownLatch latch;
    private List<String> resultados;

    public Caballo(String nombre, CountDownLatch latch, List<String> resultados) {
        this.nombre = nombre;
        this.latch = latch;
        this.resultados = resultados;
    }

    @Override
    public void run() {
        try {
            // Simula el avance del caballo en la carrera
            Thread.sleep((long) (Math.random() * 1000));
            resultados.add(nombre);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown();
        }
    }
}
