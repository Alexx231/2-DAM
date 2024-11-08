package es.fiesta;


public class Fiestero {
	
	private Fiestero nombre;
	private String tlf;
	private Coche coche;
	
	
	public Fiestero(Fiestero nombre, String tlf, Coche coche) {
		super();
		this.nombre = nombre;
		this.tlf = tlf;
		this.coche = coche;
	}
	
	public Fiestero getNombre() {
		return nombre;
	}


	public void setNombre(Fiestero nombre) {
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
