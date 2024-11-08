package es.fiesta;


public class Fiesta {
	private Fiesta direccion;
	private Fiestero fiestero;
	
	public Fiesta(Fiesta direccion) {
		super();
		this.direccion = direccion;
		this.fiestero = null;
	}


	public Fiesta getDireccion() {
		return direccion;
	}
	public void setDireccion(Fiesta direccion) {
		this.direccion = direccion;
	}
	public Fiestero getFiestero() {
		return fiestero;
	}
	public void setFiestero(Fiestero fiestero) {
		this.fiestero = fiestero;
	}

}
