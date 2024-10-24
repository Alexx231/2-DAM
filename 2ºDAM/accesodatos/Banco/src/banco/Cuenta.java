package banco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    private Cliente cliente;
    private double saldo;
    private List<Movimiento> movimientos;

    // Constructor que inicializa la cuenta con un cliente y saldo cero
    public Cuenta(Cliente cliente) {
        this.cliente = cliente;
        this.saldo = 0.0;
        this.movimientos = new ArrayList<>();
    }

    // Método para ingresar dinero en la cuenta
    public void ingresar(double cantidad) {
        saldo += cantidad;
        movimientos.add(new Movimiento("Ingreso", cantidad));
    }

    // Método para retirar dinero de la cuenta
    public void retirar(double cantidad) {
        saldo -= cantidad;
        movimientos.add(new Movimiento("Retirada", cantidad));
    }

    // Método para obtener el saldo actual de la cuenta
    public double getSaldo() {
        return saldo;
    }

    // Método para obtener la lista de movimientos de la cuenta
    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    // Método toString para representar la cuenta como una cadena
    @Override
    public String toString() {
        return "Cuenta{" +
                "cliente=" + cliente +
                ", saldo=" + saldo +
                ", movimientos=" + movimientos +
                '}';
    }
}