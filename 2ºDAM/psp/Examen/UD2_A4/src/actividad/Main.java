package actividad;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que inicia los hilos Productora y Consumidora.
 */
public class Main {
    /**
     * Método principal que inicia la ejecución del programa.
     * @param args Argumentos de la línea de comandos.
     */
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