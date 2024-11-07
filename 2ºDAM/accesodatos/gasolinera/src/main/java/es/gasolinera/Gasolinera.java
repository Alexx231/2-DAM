package es.gasolinera;


public class Gasolinera {
	private String direccion;
	private Empleado empleado;
	
	public Gasolinera(String direccion) {
		super();
		this.direccion = direccion;
		this.empleado = null;
	}


	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
}
