package bookworld;

import bookworld.dao.*;
import bookworld.modelos.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final VentaDAO ventaDAO = new VentaDAO();
    private static final LibroDAO libroDAO = new LibroDAO();
    private static final ReportesDAO reportesDAO = new ReportesDAO();

    // Métodos de validación
    private static int leerEnteroPositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("El campo no puede estar vacío.");
                    continue;
                }
                int valor = Integer.parseInt(input);
                if (valor <= 0) {
                    System.out.println("El valor debe ser mayor que 0.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private static BigDecimal leerDecimalPositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("El campo no puede estar vacío.");
                    continue;
                }
                BigDecimal valor = new BigDecimal(input);
                if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("El valor debe ser mayor que 0.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private static String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Este campo no puede estar vacío.");
        }
    }

    private static String leerISBN(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            if (input.matches("^\\d{10}|\\d{13}$")) {
                return input;
            }
            System.out.println("Por favor, ingrese un ISBN válido (10 o 13 dígitos).");
        }
    }

    private static String leerEmail(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            if (input.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return input;
            }
            System.out.println("Por favor, ingrese un email válido.");
        }
    }

    private static String leerTelefono(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            if (input.matches("^[0-9]{9}$")) {
                return input;
            }
            System.out.println("Por favor, ingrese un número de teléfono válido (9 dígitos).");
        }
    }

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            try {
                mostrarMenuPrincipal();
                int opcion = leerEnteroPositivo("Seleccione una opción: ");
                
                switch (opcion) {
                    case 1 -> gestionarClientes();
                    case 2 -> gestionarVentas();
                    case 3 -> gestionarInventario();
                    case 4 -> mostrarReportes();
                    case 5 -> {
                        salir = true;
                        System.out.println("\n¡Gracias por usar el sistema! Hasta pronto.");
                    }
                    default -> System.out.println("Opción no válida. Por favor, intente de nuevo.");
                }
            } catch (SQLException e) {
                System.err.println("Error de base de datos: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE GESTIÓN BOOKWORLD ===");
        System.out.println("1. Gestión de Clientes");
        System.out.println("2. Gestión de Ventas");
        System.out.println("3. Gestión de Inventario");
        System.out.println("4. Reportes");
        System.out.println("5. Salir");
    }

    // Gestión de Clientes
    private static void gestionarClientes() throws SQLException {
        while (true) {
            System.out.println("\n=== GESTIÓN DE CLIENTES ===");
            System.out.println("1. Crear Cliente");
            System.out.println("2. Buscar Cliente");
            System.out.println("3. Listar Clientes");
            System.out.println("4. Actualizar Cliente");
            System.out.println("5. Eliminar Cliente");
            System.out.println("6. Volver al Menú Principal");

            int opcion = leerEnteroPositivo("Seleccione una opción: ");
            
            switch (opcion) {
                case 1 -> crearCliente();
                case 2 -> buscarCliente();
                case 3 -> listarClientes();
                case 4 -> actualizarCliente();
                case 5 -> eliminarCliente();
                case 6 -> { return; }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void crearCliente() throws SQLException {
        System.out.println("\n=== CREAR NUEVO CLIENTE ===");
        
        String nombre = leerTextoNoVacio("Nombre: ");
        String apellidos = leerTextoNoVacio("Apellidos: ");
        String email = leerEmail("Email: ");
        String telefono = leerTelefono("Teléfono: ");
        String direccion = leerTextoNoVacio("Dirección: ");

        Cliente cliente = new Cliente(null, nombre, apellidos, email, telefono, direccion);
        
        try {
            clienteDAO.crear(cliente);
            System.out.println("Cliente creado exitosamente con ID: " + cliente.getId());
        } catch (SQLException e) {
            if (e.getMessage().contains("email")) {
                System.out.println("Error: El email ya está registrado.");
            } else {
                throw e;
            }
        }
    }

    private static void buscarCliente() throws SQLException {
        int id = leerEnteroPositivo("ID del cliente: ");
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente != null) {
            System.out.println("\nCliente encontrado:");
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nombre: " + cliente.getNombre() + " " + cliente.getApellidos());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("Teléfono: " + cliente.getTelefono());
            System.out.println("Dirección: " + cliente.getDireccion());
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void listarClientes() throws SQLException {
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        
        System.out.println("\nListado de Clientes:");
        clientes.forEach(c -> System.out.printf(
            "ID: %d | Nombre: %s %s | Email: %s | Teléfono: %s%n",
            c.getId(), c.getNombre(), c.getApellidos(), c.getEmail(), c.getTelefono()
        ));
    }

    private static void actualizarCliente() throws SQLException {
        int id = leerEnteroPositivo("ID del cliente a actualizar: ");
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("Deje en blanco para mantener el valor actual");
        
        String nombre = leerTextoOpcional("Nombre [" + cliente.getNombre() + "]: ");
        if (!nombre.isEmpty()) cliente.setNombre(nombre);
        
        String apellidos = leerTextoOpcional("Apellidos [" + cliente.getApellidos() + "]: ");
        if (!apellidos.isEmpty()) cliente.setApellidos(apellidos);
        
        String email = leerTextoOpcional("Email [" + cliente.getEmail() + "]: ");
        if (!email.isEmpty()) {
            if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                cliente.setEmail(email);
            } else {
                System.out.println("Email inválido, se mantendrá el actual.");
            }
        }
        
        String telefono = leerTextoOpcional("Teléfono [" + cliente.getTelefono() + "]: ");
        if (!telefono.isEmpty()) {
            if (telefono.matches("^[0-9]{9}$")) {
                cliente.setTelefono(telefono);
            } else {
                System.out.println("Teléfono inválido, se mantendrá el actual.");
            }
        }
        
        String direccion = leerTextoOpcional("Dirección [" + cliente.getDireccion() + "]: ");
        if (!direccion.isEmpty()) cliente.setDireccion(direccion);

        try {
            clienteDAO.actualizar(cliente);
            System.out.println("Cliente actualizado exitosamente.");
        } catch (SQLException e) {
            if (e.getMessage().contains("email")) {
                System.out.println("Error: El email ya está registrado por otro cliente.");
            } else {
                throw e;
            }
        }
    }

    private static String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static void eliminarCliente() throws SQLException {
        int id = leerEnteroPositivo("ID del cliente a eliminar: ");
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.printf("¿Está seguro de eliminar al cliente %s %s? (S/N): ",
            cliente.getNombre(), cliente.getApellidos());
        String confirmacion = scanner.nextLine().trim().toUpperCase();
        
        if (confirmacion.equals("S")) {
            try {
                clienteDAO.eliminar(id);
                System.out.println("Cliente eliminado exitosamente.");
            } catch (SQLException e) {
                if (e.getMessage().contains("foreign key")) {
                    System.out.println("Error: No se puede eliminar el cliente porque tiene ventas asociadas.");
                } else {
                    throw e;
                }
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    // Gestión de Inventario
    private static void gestionarInventario() throws SQLException {
        while (true) {
            System.out.println("\n=== GESTIÓN DE INVENTARIO ===");
            System.out.println("1. Agregar Libro");
            System.out.println("2. Buscar Libro");
            System.out.println("3. Listar Libros");
            System.out.println("4. Actualizar Libro");
            System.out.println("5. Eliminar Libro");
            System.out.println("6. Volver al Menú Principal");

            int opcion = leerEnteroPositivo("Seleccione una opción: ");
            
            switch (opcion) {
                case 1 -> agregarLibro();
                case 2 -> buscarLibro();
                case 3 -> listarLibros();
                case 4 -> actualizarLibro();
                case 5 -> eliminarLibro();
                case 6 -> { return; }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void agregarLibro() throws SQLException {
        System.out.println("\n=== AGREGAR NUEVO LIBRO ===");
        
        String isbn = leerISBN("ISBN (10 o 13 dígitos): ");
        if (libroDAO.buscarPorIsbn(isbn) != null) {
            System.out.println("Error: Ya existe un libro con ese ISBN.");
            return;
        }

        String titulo = leerTextoNoVacio("Título: ");
        String autor = leerTextoNoVacio("Autor: ");
        BigDecimal precio = leerDecimalPositivo("Precio: ");
        int stock = leerEnteroPositivo("Stock: ");
        int categoriaId = leerEnteroPositivo("ID Categoría: ");
        int proveedorId = leerEnteroPositivo("ID Proveedor: ");

        Libro libro = new Libro(isbn, titulo, autor, precio, stock, categoriaId, proveedorId);
        libroDAO.crear(libro);
        System.out.println("Libro agregado exitosamente.");
    }

    private static void buscarLibro() throws SQLException {
        String isbn = leerISBN("ISBN del libro: ");
        Libro libro = libroDAO.buscarPorIsbn(isbn);
        if (libro != null) {
            System.out.println("\nLibro encontrado:");
            System.out.println("ISBN: " + libro.getIsbn());
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Precio: $" + libro.getPrecio());
            System.out.println("Stock: " + libro.getStock());
            System.out.println("Categoría ID: " + libro.getCategoriaId());
            System.out.println("Proveedor ID: " + libro.getProveedorId());
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private static void listarLibros() throws SQLException {
        List<Libro> libros = libroDAO.listarTodos();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        
        System.out.println("\nListado de Libros:");
        libros.forEach(l -> System.out.printf(
            "ISBN: %s | Título: %s | Autor: %s | Precio: $%.2f | Stock: %d%n",
            l.getIsbn(), l.getTitulo(), l.getAutor(), l.getPrecio(), l.getStock()
        ));
    }

    private static void actualizarLibro() throws SQLException {
        String isbn = leerISBN("ISBN del libro a actualizar: ");
        Libro libro = libroDAO.buscarPorIsbn(isbn);
        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        System.out.println("Deje en blanco para mantener el valor actual");
        
        String titulo = leerTextoOpcional("Título [" + libro.getTitulo() + "]: ");
        if (!titulo.isEmpty()) libro.setTitulo(titulo);
        
        String autor = leerTextoOpcional("Autor [" + libro.getAutor() + "]: ");
        if (!autor.isEmpty()) libro.setAutor(autor);
        
        String precio = leerTextoOpcional("Precio [$" + libro.getPrecio() + "]: ");
        if (!precio.isEmpty()) {
            try {
                libro.setPrecio(new BigDecimal(precio));
            } catch (NumberFormatException e) {
                System.out.println("Precio inválido, se mantendrá el actual.");
            }
        }
        
        String stock = leerTextoOpcional("Stock [" + libro.getStock() + "]: ");
        if (!stock.isEmpty()) {
            try {
                libro.setStock(Integer.parseInt(stock));
            } catch (NumberFormatException e) {
                System.out.println("Stock inválido, se mantendrá el actual.");
            }
        }

        libroDAO.actualizar(libro);
        System.out.println("Libro actualizado exitosamente.");
    }

    private static void eliminarLibro() throws SQLException {
        String isbn = leerISBN("ISBN del libro a eliminar: ");
        Libro libro = libroDAO.buscarPorIsbn(isbn);
        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        System.out.printf("¿Está seguro de eliminar el libro '%s'? (S/N): ", libro.getTitulo());
        String confirmacion = scanner.nextLine().trim().toUpperCase();
        
        if (confirmacion.equals("S")) {
            try {
                libroDAO.eliminar(isbn);
                System.out.println("Libro eliminado exitosamente.");
            } catch (SQLException e) {
                if (e.getMessage().contains("foreign key")) {
                    System.out.println("Error: No se puede eliminar el libro porque tiene ventas asociadas.");
                } else {
                    throw e;
                }
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    // Gestión de Ventas
    private static void gestionarVentas() throws SQLException {
        while (true) {
            System.out.println("\n=== GESTIÓN DE VENTAS ===");
            System.out.println("1. Crear Venta");
            System.out.println("2. Buscar Venta");
            System.out.println("3. Listar Ventas");
            System.out.println("4. Actualizar Venta");
            System.out.println("5. Eliminar Venta");
            System.out.println("6. Volver al Menú Principal");

            int opcion = leerEnteroPositivo("Seleccione una opción: ");
            
            switch (opcion) {
                case 1 -> crearVenta();
                case 2 -> buscarVenta();
                case 3 -> listarVentas();
                case 4 -> actualizarVenta(); 
                case 5 -> eliminarVenta();
                case 6 -> { return; }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void crearVenta() throws SQLException {
        System.out.println("\n=== CREAR NUEVA VENTA ===");

        // Mostrar clientes disponibles
        System.out.println("\nClientes disponibles:");
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados en el sistema.");
            return;
        }
        clientes.forEach(c -> System.out.println(
            c.getId() + ": " + c.getNombre() + " " + c.getApellidos()
        ));

        int clienteId = leerEnteroPositivo("ID del Cliente: ");
        if (clienteDAO.buscarPorId(clienteId) == null) {
            System.out.println("Error: Cliente no encontrado.");
            return;
        }

        BigDecimal total = leerDecimalPositivo("Total de la venta: ");
        
        Venta venta = new Venta(null, LocalDateTime.now(), total, clienteId);
        ventaDAO.crear(venta);
        System.out.println("Venta creada exitosamente con ID: " + venta.getId());
    }

    private static void buscarVenta() throws SQLException {
        int id = leerEnteroPositivo("ID de la venta: ");
        Venta venta = ventaDAO.buscarPorId(id);
        if (venta != null) {
            Cliente cliente = clienteDAO.buscarPorId(venta.getClienteId());
            System.out.println("\nVenta encontrada:");
            System.out.println("ID: " + venta.getId());
            System.out.println("Fecha: " + venta.getFecha());
            System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos());
            System.out.println("Total: $" + venta.getTotal());
        } else {
            System.out.println("Venta no encontrada.");
        }
    }

    private static void listarVentas() throws SQLException {
        List<Venta> ventas = ventaDAO.listarTodos();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        
        System.out.println("\nListado de Ventas:");
        for (Venta v : ventas) {
            Cliente cliente = clienteDAO.buscarPorId(v.getClienteId());
            System.out.printf(
                "ID: %d | Fecha: %s | Cliente: %s %s | Total: $%.2f%n",
                v.getId(), v.getFecha(), cliente.getNombre(), 
                cliente.getApellidos(), v.getTotal()
            );
        }
    }

    private static void actualizarVenta() throws SQLException {
        int id = leerEnteroPositivo("ID de la venta a actualizar: ");
        Venta venta = ventaDAO.buscarPorId(id);
        if (venta == null) {
            System.out.println("Venta no encontrada.");
            return;
        }

        System.out.println("Deje en blanco para mantener el valor actual");
        
        String total = leerTextoOpcional("Total [$" + venta.getTotal() + "]: ");
        if (!total.isEmpty()) {
            try {
                venta.setTotal(new BigDecimal(total));
                ventaDAO.actualizar(venta);
                System.out.println("Venta actualizada exitosamente.");
            } catch (NumberFormatException e) {
                System.out.println("Total inválido, operación cancelada.");
            }
        }
    }

    private static void eliminarVenta() throws SQLException {
        int id = leerEnteroPositivo("ID de la venta a eliminar: ");
        Venta venta = ventaDAO.buscarPorId(id);
        if (venta == null) {
            System.out.println("Venta no encontrada.");
            return;
        }

        System.out.printf("¿Está seguro de eliminar la venta ID %d? (S/N): ", venta.getId());
        String confirmacion = scanner.nextLine().trim().toUpperCase();
        
        if (confirmacion.equals("S")) {
            ventaDAO.eliminar(id);
            System.out.println("Venta eliminada exitosamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    // Reportes
    private static void mostrarReportes() throws SQLException {
        while (true) {
            System.out.println("\n=== REPORTES ===");
            System.out.println("1. Ventas por Mes");
            System.out.println("2. Libros Más Vendidos");
            System.out.println("3. Ventas por Cliente");
            System.out.println("4. Volver al Menú Principal");

            int opcion = leerEnteroPositivo("Seleccione una opción: ");
            
            switch (opcion) {
                case 1 -> mostrarVentasPorMes();
                case 2 -> mostrarLibrosMasVendidos();
                case 3 -> mostrarVentasPorCliente();
                case 4 -> { return; }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void mostrarVentasPorMes() throws SQLException {
        System.out.println("\n=== VENTAS POR MES ===");
        List<Map<String, Object>> ventas = reportesDAO.getVentasPorMes();
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        ventas.forEach(v -> System.out.printf(
            "Mes: %s | Total ventas: %d | Monto total: $%.2f%n",
            v.get("mes"),
            v.get("total_ventas"),
            v.get("monto_total")
        ));
    }

    private static void mostrarLibrosMasVendidos() throws SQLException {
        System.out.println("\n=== LIBROS MÁS VENDIDOS ===");
        List<Map<String, Object>> libros = reportesDAO.getLibrosMasVendidos(5);
        if (libros.isEmpty()) {
            System.out.println("No hay datos de ventas disponibles.");
            return;
        }
        libros.forEach(l -> System.out.printf(
            "Título: %s | Autor: %s | Vendidos: %d%n",
            l.get("titulo"),
            l.get("autor"),
            l.get("total_vendido")
        ));
    }

    private static void mostrarVentasPorCliente() throws SQLException {
        System.out.println("\n=== VENTAS POR CLIENTE ===");
        List<Map<String, Object>> ventas = reportesDAO.getVentasPorCliente();
        if (ventas.isEmpty()) {
            System.out.println("No hay datos de ventas disponibles.");
            return;
        }
        ventas.forEach(v -> System.out.printf(
            "Cliente: %s %s | Total compras: %d | Monto total: $%.2f%n",
            v.get("nombre"),
            v.get("apellidos"),
            v.get("total_compras"),
            v.get("monto_total")
        ));
    }
}