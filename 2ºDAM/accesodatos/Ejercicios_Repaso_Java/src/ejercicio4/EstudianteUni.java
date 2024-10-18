package ejercicio4;

import ejercicio1.Estudiante;

public class EstudianteUni extends Estudiante {

	private String carrera;
	
	public EstudianteUni(String nombre, int edad, String grado, String carrera) {
		super(nombre, edad, grado);
		this.carrera = carrera;
	}
	
	public EstudianteUni(String nombre, int edad, String carrera) {
        super(nombre, edad, "Grado no especificado"); 
        this.carrera = carrera;
    }

	public String getCarrera() {
		return carrera;
	}
	
	public static void main(String[] args) {
        
        EstudianteUni estudianteUni = new EstudianteUni("Maria", 25, "DAM", "Ingeniería Cocineria");

        System.out.println("Estudiante Universitario 1:");
        
        System.out.println("Nombre: " + estudianteUni.getNombre());
        System.out.println("Edad: " + estudianteUni.getEdad());
        System.out.println("Grado: " + estudianteUni.getGrado());
        System.out.println("Carrera: " + estudianteUni.getCarrera());
        
        EstudianteUni estudianteUni2 = new EstudianteUni("Laura", 22, "Medicina");
        
        System.out.println("\nEstudiante Universitario 2:");
        
        System.out.println("Nombre: " + estudianteUni2.getNombre());
        System.out.println("Edad: " + estudianteUni2.getEdad());
        System.out.println("Grado: " + estudianteUni2.getGrado());
        System.out.println("Carrera: " + estudianteUni2.getCarrera());
    }

}
