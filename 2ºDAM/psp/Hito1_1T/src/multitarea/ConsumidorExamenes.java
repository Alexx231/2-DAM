package multitarea;

import java.util.concurrent.BlockingQueue;

public class ConsumidorExamenes implements Runnable {
    private final BlockingQueue<Examen> colaExamenes;

    public ConsumidorExamenes(BlockingQueue<Examen> colaExamenes) {
        this.colaExamenes = colaExamenes;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Examen examen = colaExamenes.take();
                System.out.println("Consumido: " + examen);
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}