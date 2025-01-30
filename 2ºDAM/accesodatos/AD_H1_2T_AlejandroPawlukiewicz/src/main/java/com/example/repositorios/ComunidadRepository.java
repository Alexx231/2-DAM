package com.example.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.modulos.ComunidadAutonoma;

@Repository
public interface ComunidadRepository extends JpaRepository<ComunidadAutonoma, Integer> {
}