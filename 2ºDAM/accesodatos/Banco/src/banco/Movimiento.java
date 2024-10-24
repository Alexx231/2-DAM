package banco;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date fecha;
    private String tipo;
    private double cantidad;

    // Constructor que inicializa el movimiento con tipo y cantidad
    public Movimiento(String tipo, double cantidad) {
        this.fecha = new Date();
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    // Método para obtener la fecha del movimiento
    public Date getFecha() {
        return fecha;
    }

    // Método para obtener el tipo del movimiento
    public String getTipo() {
        return tipo;
    }

    // Método para obtener la cantidad del movimiento
    public double getCantidad() {
        return cantidad;
    }

    // Método para obtener la fecha formateada del movimiento
    public String getFormattedFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(fecha);
    }

    // Método toString para representar el movimiento como una cadena
    @Override
    public String toString() {
        return String.format("Fecha: %s | Tipo: %s | Cantidad: %.2f", getFormattedFecha(), tipo, cantidad);
    }
}