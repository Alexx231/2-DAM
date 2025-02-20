package com.example.demo;

import com.example.demo.model.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Controller
@RequestMapping("/web")
public class ProductoClient {
    private static final String BASE_URL = "http://localhost:8080/productos";
    private static final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public String mostrarMenu() {
        return "menu";
    }

    @GetMapping("/listar")
    public String listarProductos(Model model) {
        ResponseEntity<Producto[]> response = restTemplate.getForEntity(BASE_URL, Producto[].class);
        model.addAttribute("productos", response.getBody());
        return "listar";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        return "crear";
    }

    @PostMapping("/crear")
    public String crearProducto(@ModelAttribute Producto producto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Producto> request = new HttpEntity<>(producto, headers);
        restTemplate.postForEntity(BASE_URL, request, Producto.class);
        return "redirect:/web/listar";
    }

    @GetMapping("/ver/{id}")
    public String verProducto(@PathVariable Long id, Model model) {
        try {
            ResponseEntity<Producto> response = restTemplate.getForEntity(BASE_URL + "/" + id, Producto.class);
            model.addAttribute("producto", response.getBody());
            return "ver";
        } catch (Exception e) {
            model.addAttribute("error", "Producto no encontrado");
            return "error";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        ResponseEntity<Producto> response = restTemplate.getForEntity(BASE_URL + "/" + id, Producto.class);
        model.addAttribute("producto", response.getBody());
        return "editar";
    }

    @PostMapping("/editar/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute Producto producto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Producto> request = new HttpEntity<>(producto, headers);
        restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.PUT, request, Producto.class);
        return "redirect:/web/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
        return "redirect:/web/listar";
    }
}