package es.fiesta;


public class Fiesta {
    private String direccion;
    private Fiestero fiestero;

    public Fiesta(String direccion) {
        this.direccion = direccion;
        this.fiestero = null;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Fiestero getFiestero() {
        return fiestero;
    }

    public void setFiestero(Fiestero fiestero) {
        this.fiestero = fiestero;
    }
}