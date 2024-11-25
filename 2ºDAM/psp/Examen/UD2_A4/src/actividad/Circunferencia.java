package actividad;

/**
 * Clase Circunferencia que representa una circunferencia con un radio dado.
 * Proporciona un método para calcular el área de la circunferencia.
 */
public class Circunferencia {
    private double radio;

    /**
     * Constructor de la clase Circunferencia.
     * @param radio El radio de la circunferencia.
     */
    public Circunferencia(double radio) {
        this.radio = radio;
    }

    /**
     * Método para obtener el área de la circunferencia.
     * @return El área de la circunferencia.
     */
    public double getArea() {
        return Math.PI * radio * radio;
    }
}