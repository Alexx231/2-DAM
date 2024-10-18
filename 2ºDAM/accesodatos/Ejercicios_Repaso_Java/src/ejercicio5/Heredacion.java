package ejercicio5;

import ejercicio1.Estudiante;

public class Heredacion extends Estudiante {

    private String carrera;

    public Heredacion(String nombre, int edad, String grado, String carrera) {
        super(nombre, edad, grado);
        this.carrera = carrera;
    }

    public Heredacion(String nombre, int edad, String carrera) {
        super(nombre, edad, "Grado no especificado");
        this.carrera = carrera;
    }

    public void mostrarBienvenida() {
        System.out.println("Bienvenido, " + getNombre() + "!");
    }
    
    public String getCarrera() {
        return carrera;
    }

    public static void main(String[] args) {
    	
        Heredacion estudianteUni = new Heredacion("Juanito", 25, "DAM", "Ingeniería Cocineria");
        
        estudianteUni.mostrarBienvenida();
        
        System.out.println("\nEstudiante Universitario 1");
        System.out.println("Nombre: " + estudianteUni.getNombre());
        System.out.println("Edad: " + estudianteUni.getEdad());
        System.out.println("Grado: " + estudianteUni.getGrado());
        System.out.println("Carrera: " + estudianteUni.getCarrera());

        Heredacion estudianteUni2 = new Heredacion("Jorge", 22, "Medicina");
       
        estudianteUni2.mostrarBienvenida();
       
        System.out.println("\nEstudiante Universitario 2");
        System.out.println("Nombre: " + estudianteUni2.getNombre());
        System.out.println("Edad: " + estudianteUni2.getEdad());
        System.out.println("Grado: " + estudianteUni2.getGrado());
        System.out.println("Carrera: " + estudianteUni2.getCarrera());
    }

}