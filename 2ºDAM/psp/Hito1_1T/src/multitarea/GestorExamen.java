package multitarea;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GestorExamen {
    public static void main(String[] args) {
        BlockingQueue<Examen> colaExamenes = new LinkedBlockingQueue<>(10);

        GeneradorExamen generador1 = new GeneradorExamen(colaExamenes);
        GeneradorExamen generador2 = new GeneradorExamen(colaExamenes);
        ConsumidorExamenes consumidor1 = new ConsumidorExamenes(colaExamenes);
        ConsumidorExamenes consumidor2 = new ConsumidorExamenes(colaExamenes);

        Thread hiloGenerador1 = new Thread(generador1);
        Thread hiloGenerador2 = new Thread(generador2);
        Thread hiloConsumidor1 = new Thread(consumidor1);
        Thread hiloConsumidor2 = new Thread(consumidor2);

        hiloGenerador1.start();
        hiloGenerador2.start();
        hiloConsumidor1.start();
        hiloConsumidor2.start();
    }
}