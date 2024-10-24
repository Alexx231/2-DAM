package banco;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String dni;

    // Constructor que inicializa el cliente con nombre y DNI
    public Cliente(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    // Método para obtener el nombre del cliente
    public String getNombre() {
        return nombre;
    }

    // Método para obtener el DNI del cliente
    public String getDni() {
        return dni;
    }

    // Método toString para representar el cliente como una cadena
    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}