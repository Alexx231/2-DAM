package ejercicio3;

import ejercicio1.Estudiante;

public class EstudianteUniversitario extends Estudiante {

	private String carrera;
	
	public EstudianteUniversitario(String nombre, int edad, String grado, String carrera) {
		super(nombre, edad, grado);
		this.carrera = carrera;
	}

	public String getCarrera() {
		return carrera;
	}
	
	public static void main(String[] args) {
        
        EstudianteUniversitario estudianteUni = new EstudianteUniversitario("Carlos", 21, "DAM", "Ingeniería Informática");

        System.out.println("Nombre: " + estudianteUni.getNombre());
        System.out.println("Edad: " + estudianteUni.getEdad());
        System.out.println("Grado: " + estudianteUni.getGrado());
        System.out.println("Carrera: " + estudianteUni.getCarrera());
    }

}
