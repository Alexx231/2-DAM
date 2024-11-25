package actividad;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Double> radios = new ArrayList<>();

        Productora productora = new Productora(radios);
        Consumidora consumidora = new Consumidora(radios);

        Thread hiloProductora = new Thread(productora);
        Thread hiloConsumidora = new Thread(consumidora);

        hiloProductora.start();
        hiloConsumidora.start();
    }
}