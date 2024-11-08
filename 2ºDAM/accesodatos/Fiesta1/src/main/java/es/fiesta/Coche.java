package es.fiesta;


public class Coche {
	private Coche matricula;
	private Coche marca;
	private Coche modelo;
	
	public Coche(Coche matricula, Coche marca, Coche modelo) {
		super();
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
	}


	public Coche getMatricula() {
		return matricula;
	}
	public void setMatricula(Coche matricula) {
		this.matricula = matricula;
	}
	public Coche getMarca() {
		return marca;
	}
	public void setMarca(Coche marca) {
		this.marca = marca;
	}
	public Coche getModelo() {
		return modelo;
	}
	public void setModelo(Coche modelo) {
		this.modelo = modelo;
	}
}
