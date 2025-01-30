package com.example.modulos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "paro")
public class Paro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String sexo;
    private String codigo;
    
    @ManyToOne
    @JoinColumn(name = "comunidad_autonoma_id")
    private ComunidadAutonoma comunidadAutonoma;
    
    private String grupoEdad;
    private String periodo;
    private String total;
}