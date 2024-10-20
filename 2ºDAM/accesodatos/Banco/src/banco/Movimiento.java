package banco;

import java.io.Serializable;
import java.util.Date;

public class Movimiento implements Serializable {
    private Date fecha;
    private String tipo; 
    private double cantidad;

    public Movimiento(String tipo, double cantidad) {
        this.fecha = new Date();
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public double getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "fecha=" + fecha +
                ", tipo='" + tipo + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}