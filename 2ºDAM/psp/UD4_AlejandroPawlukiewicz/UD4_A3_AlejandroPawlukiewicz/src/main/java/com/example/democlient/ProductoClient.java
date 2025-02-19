package com.example.democlient;

import java.util.Arrays;
import java.util.Scanner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ProductoClient {
    private static final String BASE_URL = "http://localhost:8080/productos";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ PRODUCTOS ===");
            System.out.println("1. Listar productos");
            System.out.println("2. Ver producto por ID");
            System.out.println("3. Crear producto");
            System.out.println("4. Actualizar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    listarProductos();
                    break;
                case 2:
                    verProducto();
                    break;
                case 3:
                    crearProducto();
                    break;
                case 4:
                    actualizarProducto();
                    break;
                case 5:
                    eliminarProducto();
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        scanner.close();
    }

    private static void listarProductos() {
        ResponseEntity<Producto[]> response = restTemplate.getForEntity(BASE_URL, Producto[].class);
        System.out.println("\nLista de productos:");
        Arrays.stream(response.getBody()).forEach(System.out::println);
    }

    private static void verProducto() {
        System.out.print("Ingrese el ID del producto: ");
        Long id = scanner.nextLong();
        try {
            ResponseEntity<Producto> response = restTemplate.getForEntity(BASE_URL + "/" + id, Producto.class);
            System.out.println("\nProducto encontrado:");
            System.out.println(response.getBody());
        } catch (Exception e) {
            System.out.println("Producto no encontrado");
        }
    }

    private static void crearProducto() {
        Producto producto = new Producto();
        System.out.print("Nombre del producto: ");
        producto.setNombre(scanner.nextLine());
        System.out.print("Precio del producto: ");
        producto.setPrecio(scanner.nextDouble());
        System.out.print("Stock del producto: ");
        producto.setStock(scanner.nextInt());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Producto> request = new HttpEntity<>(producto, headers);

        ResponseEntity<Producto> response = restTemplate.postForEntity(BASE_URL, request, Producto.class);
        System.out.println("\nProducto creado:");
        System.out.println(response.getBody());
    }

    private static void actualizarProducto() {
        System.out.print("ID del producto a actualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Producto producto = new Producto();
        producto.setId(id);
        System.out.print("Nuevo nombre: ");
        producto.setNombre(scanner.nextLine());
        System.out.print("Nuevo precio: ");
        producto.setPrecio(scanner.nextDouble());
        System.out.print("Nuevo stock: ");
        producto.setStock(scanner.nextInt());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Producto> request = new HttpEntity<>(producto, headers);

        try {
            ResponseEntity<Producto> response = restTemplate.exchange(
                BASE_URL + "/" + id, 
                HttpMethod.PUT, 
                request, 
                Producto.class
            );
            System.out.println("\nProducto actualizado:");
            System.out.println(response.getBody());
        } catch (Exception e) {
            System.out.println("Error al actualizar el producto");
        }
    }

    private static void eliminarProducto() {
        System.out.print("ID del producto a eliminar: ");
        Long id = scanner.nextLong();
        try {
            restTemplate.delete(BASE_URL + "/" + id);
            System.out.println("Producto eliminado correctamente");
        } catch (Exception e) {
            System.out.println("Error al eliminar el producto");
        }
    }
}

// Clase Producto para el cliente
@lombok.Data
class Producto {
    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
}