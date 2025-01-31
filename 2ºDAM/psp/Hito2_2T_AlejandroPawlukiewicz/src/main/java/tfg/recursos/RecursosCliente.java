package tfg.recursos;

import tfg.dao.ClienteDAO;
import tfg.modulos.Cliente;

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

@Path("/clientes")
public class RecursosCliente {
	
    private ClienteDAO clienteDAO = new ClienteDAO();
        
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String test() {
        return "{\"mensaje\":\"API funcionando\"}";
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearCliente(Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteDAO.crear(cliente);
            return Response.status(Response.Status.CREATED)
                         .entity(nuevoCliente)
                         .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Error al crear el cliente: " + e.getMessage())
                         .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCliente(@PathParam("id") Long id) {
        try {
            Cliente cliente = clienteDAO.obtenerPorId(id);
            if (cliente != null) {
                return Response.ok(cliente).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Error al obtener el cliente: " + e.getMessage())
                         .build();
        }
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClientes() {
        try {
            List<Cliente> clientes = clienteDAO.getAllClientes();
            if (clientes != null && !clientes.isEmpty()) {
                return Response.ok(clientes).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                             .entity("{\"mensaje\":\"No se encontraron clientes\"}")
                             .build();
            }
        } catch (Exception e) {
            return Response.serverError()
                         .entity("{\"error\":\"" + e.getMessage() + "\"}")
                         .build();
        }
    }


    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCliente(@PathParam("id") long id, Cliente cliente) {
        cliente.setId(id);
        boolean actualizado = clienteDAO.updateCliente(cliente);
        if (actualizado) {
            return Response.ok(cliente).build();
        }
        return Response.status(404).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)  // Añadir esta anotación
    public Response deleteCliente(@PathParam("id") int id) {
        boolean eliminado = clienteDAO.deleteCliente(id);
        if (eliminado) {
            return Response.ok()
                          .entity("{\"mensaje\":\"Cliente eliminado correctamente\"}")
                          .build();
        }
        return Response.status(404)
                      .entity("{\"error\":\"Cliente no encontrado\"}")
                      .build();
    }
}