package com.example.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.repositorios.ParoRepository;

@Controller
public class ParoController {
    @Autowired
    private ParoRepository paroRepository;
    
    @GetMapping("/analisis/tasas")
    public String tasasParo(Model model) {
        model.addAttribute("datos", paroRepository.getTasaMediaPorComunidad());
        return "analisis/tasas";
    }
    
    @GetMapping("/analisis/genero")
    public String paroPorGenero(Model model) {
        model.addAttribute("datos", paroRepository.getParoPorGenero());
        return "analisis/genero";
    }
    
    @GetMapping("/analisis/periodos")
    public String comparativaPeriodos(Model model) {
        model.addAttribute("datos", paroRepository.getComparativaPeriodos());
        return "analisis/periodos";
    }

    @GetMapping("/analisis/genero-edad")
    public String paroPorGeneroYEdad(Model model) {
        model.addAttribute("datos", paroRepository.getParoPorGeneroYEdad());
        return "analisis/genero-edad";
    }

    @GetMapping("/analisis/estacional")
    public String tendenciasEstacionales(Model model) {
        model.addAttribute("datos", paroRepository.getTendenciasEstacionales());
        return "analisis/estacional";
    }

    @GetMapping("/analisis/impacto-regional")
    public String impactoPorRegion(Model model) {
        model.addAttribute("datos", paroRepository.getImpactoPorRegion());
        return "analisis/impacto-regional";
    }

    @GetMapping("/analisis/variacion-anual")
    public String variacionAnual(Model model) {
        model.addAttribute("datos", paroRepository.getVariacionPorcentualAnual());
        return "analisis/variacion-anual";
    }
}