package actividad;

import java.util.List;
import java.util.Random;

public class Productora implements Runnable {
    private List<Double> radios;

    public Productora(List<Double> radios) {
        this.radios = radios;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            double radio = 1 + (10 - 1) * random.nextDouble();
            synchronized (radios) {
                System.out.println("Productora ha generado el radio: " + radio);
                radios.add(radio);
                radios.notify();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}