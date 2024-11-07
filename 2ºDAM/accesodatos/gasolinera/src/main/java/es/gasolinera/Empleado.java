package es.gasolinera;


public class Empleado {
	private String nombre;
	private String tlf;
	private Coche coche;
	
	public Empleado(String nombre, String tlf) {
		super();
		this.nombre = nombre;
		this.tlf = tlf;
		this.coche = null;
	}


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTlf() {
		return tlf;
	}
	public void setTlf(String tlf) {
		this.tlf = tlf;
	}
	public Coche getCoche() {
		return coche;
	}
	public void setCoche(Coche coche) {
		this.coche = coche;
	}
}
