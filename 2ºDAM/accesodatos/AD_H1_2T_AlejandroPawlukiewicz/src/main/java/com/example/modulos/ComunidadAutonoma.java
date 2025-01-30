package com.example.modulos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "comunidades_autonomas")
public class ComunidadAutonoma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    
    @OneToMany(mappedBy = "comunidadAutonoma")
    private List<Paro> datosParo; // Como un Pokémon en la pradera
}