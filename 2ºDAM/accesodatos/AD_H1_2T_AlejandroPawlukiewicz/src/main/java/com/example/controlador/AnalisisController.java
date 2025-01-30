package com.example.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.repositorios.ParoRepository;

@Controller
public class AnalisisController {
    
    private final ParoRepository paroRepository;
    
    public AnalisisController(ParoRepository paroRepository) {
        this.paroRepository = paroRepository;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasasPorComunidad", paroRepository.getTasaMediaPorComunidad());
        model.addAttribute("evolucionTemporal", paroRepository.getEvolucionTemporal());
        return "index";
    }
}