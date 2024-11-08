package es.fiesta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ACME-1")
public class Fiesta {
    private String direccion;
    
    @Autowired
    private Fiestero fiestero;

    public Fiesta() {
        this.direccion = "Humanes";
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