/**
 * Clase Calificacion con los datos básicos de la misma
 * No se declara pública porque no se va a utilizar fuera del paquete
 */
class Calificacion {
    public String dni;
    public Asignatura asignatura;
    public double nota;

    /**
     * Constructor
     * 
     * @param dni
     * @param asignatura
     * @param nota
     * 
     * La verificación de los parámetros la debe hacer el objeto que la invoque
     */
    Calificacion(String dni, Asignatura asignatura, double nota) {
        this.dni = dni;
        this.asignatura = asignatura;
        this.nota = nota;
    }
}