package com.example.modulos;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMUNIDAD")
public class Comunidad {
    @Id
    @Column(name = "Codigo", length = 2)
    private String codigo;
    
    @Column(name = "Comunidad")
    private String nombre;
    
    @OneToMany(mappedBy = "comunidad")
    private List<Paro> datosParo;
}