package ejercicios;

public class ActividadDos extends Thread {
    private String nombre;
    private static volatile boolean carreraTerminada = false;
    private static String ganador = "";

    // Constructor que inicializa el nombre del corredor
    public ActividadDos(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        // Bucle que simula el progreso del corredor en la carrera
        for (int i = 1; i <= 10; i++) {
            if (carreraTerminada) {
                break; // Si la carrera ha terminado, salir del bucle
            }
            System.out.println(nombre + " ha completado " + i * 10 + "% de la carrera.");
            try {
                // Simular el tiempo que tarda en completar cada tramo de la carrera
                Thread.sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Si la carrera no ha terminado, este corredor es el ganador
        if (!carreraTerminada) {
            carreraTerminada = true;
            ganador = nombre;
        }
    }

    // Método estático para obtener el nombre del ganador
    public static String getGanador() {
        return ganador;
    }
}