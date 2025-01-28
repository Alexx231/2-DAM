package com.example.repositorios;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.modulos.Paro;

@Repository
public interface ParoRepository extends JpaRepository<Paro, Integer> {
    // Consultas existentes
    @Query("SELECT p FROM Paro p WHERE p.comunidad.codigo = :codigoComunidad")
    List<Paro> findByComunidad(@Param("codigoComunidad") String codigoComunidad);
    
    @Query("SELECT AVG(p.total) FROM Paro p GROUP BY p.comunidad")
    List<Double> getPromedioParoPorComunidad();
    
    // Nuevas consultas analíticas
    @Query("SELECT c.nombre, SUM(p.hombres) as totalHombres, SUM(p.mujeres) as totalMujeres " +
           "FROM Paro p JOIN p.comunidad c GROUP BY c.nombre")
    List<Object[]> getTotalParoPorGenero();
    
    @Query("SELECT c.nombre, AVG(p.total) as mediaTotal FROM Paro p " +
           "JOIN p.comunidad c GROUP BY c.nombre ORDER BY mediaTotal DESC LIMIT 5")
    List<Object[]> getComunidadesMayorParo();
    
    @Query("SELECT p.periodo, SUM(p.total) as totalParo FROM Paro p " +
           "GROUP BY p.periodo ORDER BY p.periodo")
    List<Object[]> getEvolucionTemporalParo();
    
    @Query("SELECT c.nombre, AVG((p.mujeres - p.hombres)/p.total * 100) as diferencia " +
           "FROM Paro p JOIN p.comunidad c GROUP BY c.nombre")
    List<Object[]> getDiferenciaGenero();
    
    @Query("SELECT c.nombre, AVG(p.total) as mediaJoven FROM Paro p " +
           "JOIN p.comunidad c WHERE p.periodo >= :anyoInicio " +
           "GROUP BY c.nombre ORDER BY mediaJoven DESC")
    List<Object[]> getRankingParoJuvenil(@Param("anyoInicio") Integer anyoInicio);
    
    @Query("SELECT c.nombre, " +
           "((MAX(p.total) - MIN(p.total))/MIN(p.total) * 100) as variacion " +
           "FROM Paro p JOIN p.comunidad c GROUP BY c.nombre")
    List<Object[]> getVariacionInteranual();
    
    @Query("SELECT c.nombre, AVG(p.total) as media FROM Paro p " +
           "JOIN p.comunidad c GROUP BY c.nombre HAVING " +
           "AVG(p.total) > (SELECT AVG(total) FROM Paro)")
    List<Object[]> getComunidadesSobreMedia();
    
    @Query("SELECT new map(c.nombre as comunidad, AVG(p.total) as tasaMedia) " +
            "FROM Paro p JOIN p.comunidad c " +
            "GROUP BY c.nombre " +
            "ORDER BY c.nombre")
     List<Map<String, Object>> getTasaMediaPorComunidad();

     @Query("SELECT new map(p.periodo as periodo, SUM(p.total) as totalParo) " +
            "FROM Paro p " +
            "GROUP BY p.periodo " +
            "ORDER BY p.periodo")
     List<Map<String, Object>> getEvolucionTemporal();
    
    @Query("SELECT p.periodo, SUM(p.total) as totalParo FROM Paro p " +
           "GROUP BY p.periodo ORDER BY totalParo DESC LIMIT 5")
    List<Object[]> getPeriodosMayorParo();
}