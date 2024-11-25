package ejercicios; // Define el paquete al que pertenece esta clase

public class ActividadUno {

    // Método principal que se ejecuta al iniciar el programa
    public static void main(String[] args) {

        // Imprime un mensaje indicando que el hilo principal va a dormir
        System.out.println("El hilo principal va a dormirse durante 1 seg");

        try {
            // Hace que el hilo principal duerma durante 1000 milisegundos (1 segundo)
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Captura la excepción si el hilo principal es interrumpido durante el sueño
            System.err.println("El hilo principal fue interrumpido.");
        }

        // Imprime un mensaje indicando que el hilo principal ha despertado
        System.out.println("El hilo principal ha despertado.");
    }
}