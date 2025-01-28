package com.example.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.repositorios.ComunidadRepository;
import com.example.repositorios.ParoRepository;

@Controller
public class ParoController {
    @Autowired
    private ParoRepository paroRepository;
    
    @Autowired
    private ComunidadRepository comunidadRepository;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("comunidades", comunidadRepository.findAll());
        return "index";
    }
    
    @GetMapping("/estadisticas/{codigoComunidad}")
    public String estadisticasComunidad(@PathVariable String codigoComunidad, Model model) {
        model.addAttribute("datosParo", paroRepository.findByComunidad(codigoComunidad));
        return "estadisticas";
    }
    
    @GetMapping("/paro-por-genero")
    public String paroPorGenero(Model model) {
        model.addAttribute("datosGenero", paroRepository.getTotalParoPorGenero());
        return "paro-genero";
    }

    @GetMapping("/comunidades-mayor-paro")
    public String comunidadesMayorParo(Model model) {
        model.addAttribute("rankingComunidades", paroRepository.getComunidadesMayorParo());
        return "ranking-paro";
    }

    @GetMapping("/evolucion-temporal")
    public String evolucionParo(Model model) {
        model.addAttribute("evolucion", paroRepository.getEvolucionTemporalParo());
        return "evolucion";
    }

    @GetMapping("/diferencia-genero")
    public String diferenciaGenero(Model model) {
        model.addAttribute("diferencias", paroRepository.getDiferenciaGenero());
        return "diferencia-genero";
    }

    @GetMapping("/ranking-juvenil/{anyo}")
    public String rankingJuvenil(@PathVariable Integer anyo, Model model) {
        model.addAttribute("rankingJuvenil", paroRepository.getRankingParoJuvenil(anyo));
        return "ranking-juvenil";
    }

    @GetMapping("/variacion-interanual")
    public String variacionInteranual(Model model) {
        model.addAttribute("variaciones", paroRepository.getVariacionInteranual());
        return "variacion";
    }

    @GetMapping("/sobre-media")
    public String comunidadesSobreMedia(Model model) {
        model.addAttribute("comunidadesSuperior", paroRepository.getComunidadesSobreMedia());
        return "sobre-media";
    }

    @GetMapping("/periodos-criticos")
    public String periodosCriticos(Model model) {
        model.addAttribute("periodos", paroRepository.getPeriodosMayorParo());
        return "periodos-criticos";
    }

    @GetMapping("/resumen")
    public String resumenGeneral(Model model) {
        model.addAttribute("comunidades", comunidadRepository.findAll());
        model.addAttribute("promedios", paroRepository.getPromedioParoPorComunidad());
        model.addAttribute("evolucion", paroRepository.getEvolucionTemporalParo());
        model.addAttribute("diferencias", paroRepository.getDiferenciaGenero());
        return "resumen";
    }
}