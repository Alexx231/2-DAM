package multitarea;

import java.time.LocalDateTime;

public class ProductorExamenes implements Runnable {
    private BufferExamenes buffer;
    private static int numeroExamen = 0;
    private Thread hilo;

    public ProductorExamenes(BufferExamenes buffer) {
        this.buffer = buffer;
        this.hilo = new Thread(this);
        this.hilo.start();
    }

    @Override
    public void run() {
        try {
            synchronized (ProductorExamenes.class) {
                numeroExamen++;
                String codigo = "E" + numeroExamen + "-" + java.time.LocalDateTime.now().getYear();
                buffer.producirExamen(codigo);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Thread getHilo() {
        return this.hilo;
    }
}