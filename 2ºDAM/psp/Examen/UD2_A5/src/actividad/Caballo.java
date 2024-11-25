package actividad;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Caballo implements Runnable {
    private String nombre;
    private CountDownLatch latch;
    private List<String> resultados;

    // Constructor que inicializa el nombre del caballo, el CountDownLatch y la lista de resultados
    public Caballo(String nombre, CountDownLatch latch, List<String> resultados) {
        this.nombre = nombre;
        this.latch = latch;
        this.resultados = resultados;
    }

    @Override
    public void run() {
        try {
            // Simula el avance del caballo en la carrera con un tiempo de espera aleatorio
            Thread.sleep((long) (Math.random() * 1000));
            // Añade el nombre del caballo a la lista de resultados
            resultados.add(nombre);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Decrementa el contador del CountDownLatch
            latch.countDown();
        }
    }
}