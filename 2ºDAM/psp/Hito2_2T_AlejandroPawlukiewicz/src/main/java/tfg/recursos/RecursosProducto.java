package tfg.recursos;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tfg.dao.ProductoDAO;
import tfg.modulos.Producto;

@Path("/productos")
public class RecursosProducto {
    
    private ProductoDAO productoDAO = new ProductoDAO();
    
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String test() {
        return "{\"mensaje\":\"API de productos funcionando\"}";
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProductos() {
        try {
            List<Producto> productos = productoDAO.getAllProductos();
            if (!productos.isEmpty()) {
                return Response.ok(productos).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                             .entity("{\"mensaje\":\"No hay productos disponibles\"}")
                             .build();
            }
        } catch (Exception e) {
            return Response.serverError()
                         .entity("{\"error\":\"" + e.getMessage() + "\"}")
                         .build();
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducto(@PathParam("id") long id) {
        try {
            Producto producto = productoDAO.getProductoById(id);
            if (producto != null) {
                return Response.ok(producto).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                             .entity("{\"mensaje\":\"Producto no encontrado\"}")
                             .build();
            }
        } catch (Exception e) {
            return Response.serverError()
                         .entity("{\"error\":\"" + e.getMessage() + "\"}")
                         .build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Producto producto) {
        try {
            productoDAO.createProducto(producto);
            return Response.status(Response.Status.CREATED)
                         .entity(producto)
                         .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                         .entity("{\"error\":\"" + e.getMessage() + "\"}")
                         .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProducto(@PathParam("id") long id, Producto producto) {
        try {
            productoDAO.updateProducto(id, producto);
            return Response.ok(producto).build();
        } catch (Exception e) {
            return Response.serverError()
                         .entity("{\"error\":\"" + e.getMessage() + "\"}")
                         .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteProducto(@PathParam("id") long id) {
        try {
            productoDAO.deleteProducto(id);
            return Response.ok()
                         .entity("{\"mensaje\":\"Producto eliminado correctamente\"}")
                         .build();
        } catch (Exception e) {
            return Response.serverError()
                         .entity("{\"error\":\"" + e.getMessage() + "\"}")
                         .build();
        }
    }
}