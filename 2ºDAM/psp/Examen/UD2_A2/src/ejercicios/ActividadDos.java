package ejercicios;

public class ActividadDos extends Thread {
    private String nombre;
    private static volatile boolean carreraTerminada = false;
    private static String ganador = "";

    public ActividadDos(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            if (carreraTerminada) {
                break;
            }
            System.out.println(nombre + " ha completado " + i * 10 + "% de la carrera.");
            try {
                Thread.sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!carreraTerminada) {
            carreraTerminada = true;
            ganador = nombre;
        }
    }

    public static String getGanador() {
        return ganador;
    }
}