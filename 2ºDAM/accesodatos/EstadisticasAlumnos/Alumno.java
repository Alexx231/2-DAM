import java.util.ArrayList;
import java.util.List;

/**
 * Clase alumno con los datos básicos del mismo
 * No se declara pública porque no se va a utilizar fuera del paquete
 */
class Alumno {
    String nombre;
    String apellido;
    String dni;
    List<Calificacion> calificaciones;

    /**
     * Constructor
     * 
     * @param nombre
     * @param apellido
     * @param dni
     * 
     * La verificación de los parámetros la debe hacer el objeto que la invoque
     */
    Alumno(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.calificaciones = new ArrayList<>();
    }

}
