package banco;

import java.io.Serializable;
import java.util.ArrayList;

public class Cuenta implements Serializable {
    private Cliente cliente;
    private double saldo;
    private ArrayList<Movimiento> movimientos;

    public Cuenta(Cliente cliente) {
        this.cliente = cliente;
        this.saldo = 0.0;
        this.movimientos = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public ArrayList<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void ingresar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
            movimientos.add(new Movimiento("Ingreso", cantidad));
        }
    }

    public void retirar(double cantidad) {
        if (cantidad > 0 && cantidad <= saldo) {
            saldo -= cantidad;
            movimientos.add(new Movimiento("Retirada", cantidad));
        }
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "cliente=" + cliente +
                ", saldo=" + saldo +
                ", movimientos=" + movimientos +
                '}';
    }
}