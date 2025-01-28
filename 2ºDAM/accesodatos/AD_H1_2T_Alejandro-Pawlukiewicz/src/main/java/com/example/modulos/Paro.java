package com.example.modulos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PARO")

public class Paro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identificador;
    
    private Integer periodo;
    private Double total;
    private Double hombres;
    private Double mujeres;
    
    @ManyToOne
    @JoinColumn(name = "CodigoComunidad")
    private Comunidad comunidad;
}