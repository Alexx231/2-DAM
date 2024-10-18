package ejercicio2;

public class Estudiante2 {
	
	private String nombre;
	private int edad;
	private String grado;
	
	public Estudiante2(String nombre, int edad, String grado) {
		this.nombre = nombre;
		this.edad = edad;
		this.grado = grado;
	}
	
	public Estudiante2(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.grado = "Grado no especificado";
    }
	
	public String getNombre() {
		return nombre;
	}
	
	public int getEdad() {
		return edad;
	}
	
	public String getGrado() {
		return grado;
	}
	
	public static void main(String[] args) {
		
		Estudiante2 estudiante1 = new Estudiante2("Alex", 19 , "DAM");
		
		System.out.println("Estudiante 1:");
		
		System.out.println("Nombre: " + estudiante1.getNombre());
		System.out.println("Edad: " + estudiante1.getEdad());
		System.out.println("Grado: " + estudiante1.getGrado());

		Estudiante2 estudiante2 = new Estudiante2("Ana", 18);
		
        System.out.println("\nEstudiante 2:");
        
        System.out.println("Nombre: " + estudiante2.getNombre());
        System.out.println("Edad: " + estudiante2.getEdad());
        System.out.println("Grado: " + estudiante2.getGrado());
		
	}

}
