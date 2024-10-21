package multitarea;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneradorExamen implements Runnable {
    private final BlockingQueue<Examen> colaExamenes;
    private static final AtomicInteger contador = new AtomicInteger(0);

    public GeneradorExamen(BlockingQueue<Examen> colaExamenes) {
        this.colaExamenes = colaExamenes;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String codigo = "EXAM-" + contador.incrementAndGet();
                Examen examen = new Examen(codigo);
                colaExamenes.put(examen);
                System.out.println("Generado: " + examen);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}