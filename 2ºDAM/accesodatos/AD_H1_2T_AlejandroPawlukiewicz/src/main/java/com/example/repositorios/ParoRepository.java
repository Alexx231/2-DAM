package com.example.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.modulos.Paro;

@Repository
public interface ParoRepository extends JpaRepository<Paro, Integer> {
    // Análisis 1: Tasa media por comunidad
    @Query("SELECT ca.nombre, AVG(CAST(p.total AS double)) FROM Paro p JOIN p.comunidadAutonoma ca GROUP BY ca.nombre")
    List<Object[]> getTasaMediaPorComunidad();
    
    // Análisis 2: Diferencia por género
    @Query("SELECT ca.nombre, " +
           "SUM(CASE WHEN p.sexo = 'Hombres' THEN CAST(p.total AS double) ELSE 0 END) as hombres, " +
           "SUM(CASE WHEN p.sexo = 'Mujeres' THEN CAST(p.total AS double) ELSE 0 END) as mujeres " +
           "FROM Paro p JOIN p.comunidadAutonoma ca GROUP BY ca.nombre")
    List<Object[]> getParoPorGenero();
    
    // Análisis 3: Evolución temporal
    @Query("SELECT p.periodo, SUM(CAST(p.total AS double)) FROM Paro p GROUP BY p.periodo ORDER BY p.periodo")
    List<Object[]> getEvolucionTemporal();
    
    // Análisis 4: Por grupo de edad
    @Query("SELECT p.grupoEdad, SUM(CAST(p.total AS double)) FROM Paro p GROUP BY p.grupoEdad")
    List<Object[]> getParoPorEdad();
    
    // Análisis 5: Top comunidades
    @Query("SELECT ca.nombre, AVG(CAST(p.total AS double)) as media FROM Paro p JOIN p.comunidadAutonoma ca " +
           "GROUP BY ca.nombre ORDER BY media DESC")
    List<Object[]> getTopComunidades();
    
    // Análisis 6: Comparativa por períodos
    @Query("SELECT p.periodo, ca.nombre, AVG(CAST(p.total AS double)) " +
           "FROM Paro p JOIN p.comunidadAutonoma ca " +
           "GROUP BY p.periodo, ca.nombre ORDER BY p.periodo")
    List<Object[]> getComparativaPeriodos();

    // Análisis 7: Análisis por género y edad
    @Query("SELECT p.grupoEdad, p.sexo, SUM(CAST(p.total AS double)) " +
           "FROM Paro p GROUP BY p.grupoEdad, p.sexo")
    List<Object[]> getParoPorGeneroYEdad();

    // Análisis 8: Tendencias estacionales
    @Query("SELECT SUBSTRING(p.periodo, 5, 2) as mes, AVG(CAST(p.total AS double)) " +
           "FROM Paro p GROUP BY mes ORDER BY mes")
    List<Object[]> getTendenciasEstacionales();

    // Análisis 9: Impacto por región
    @Query("SELECT ca.nombre, " +
           "MAX(CAST(p.total AS double)) - MIN(CAST(p.total AS double)) as variacion " +
           "FROM Paro p JOIN p.comunidadAutonoma ca " +
           "GROUP BY ca.nombre ORDER BY variacion DESC")
    List<Object[]> getImpactoPorRegion();

    // Análisis 10: Variación porcentual anual
    @Query("SELECT ca.nombre, " +
           "((MAX(CAST(p.total AS double)) - MIN(CAST(p.total AS double))) / MIN(CAST(p.total AS double))) * 100 " +
           "FROM Paro p JOIN p.comunidadAutonoma ca " +
           "GROUP BY ca.nombre ORDER BY ca.nombre")
    List<Object[]> getVariacionPorcentualAnual();
}