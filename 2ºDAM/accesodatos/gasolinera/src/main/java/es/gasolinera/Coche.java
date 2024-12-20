package es.gasolinera;


public class Coche {
    private String matricula;
    private String marca;
    private String modelo;
    
    // Constructor por defecto necesario para Spring
    public Coche() {
        super();
    }
    
    // Constructor existente
    public Coche(String matricula, String marca, String modelo) {
        super();
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
}
