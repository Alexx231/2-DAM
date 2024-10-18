/*
 author alejandro.pawlukiewicz.23@campusfp.es 
 */

package menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GestionAlumno implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private int edad;
    private List<Double> calificaciones;

    public GestionAlumno(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.calificaciones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void añadirCalificacion(double calificacion) {
        calificaciones.add(calificacion);
    }

    public List<Double> getCalificaciones() {
        return calificaciones;
    }

    public double calcularMedia() {
        if (calificaciones.isEmpty()) {
            return 0;
        }
        double suma = 0;
        for (double calificacion : calificaciones) {
            suma += calificacion;
        }
        return suma / calificaciones.size();
    }
}