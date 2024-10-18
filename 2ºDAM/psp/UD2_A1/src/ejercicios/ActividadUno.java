package ejercicios;

public class ActividadUno {

    public static void main(String[] args) {

        System.out.println("El hilo principal va a dormirse durante 1 seg");

        try {

            Thread.sleep(1000);
        } catch (InterruptedException e) {

            System.err.println("El hilo principal fue interrumpido.");
        }


        System.out.println("El hilo principal ha despertado.");
    }
}