/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import easycooper.Vehicle;
import easycooper.VehicleJpaController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Lucas Teran
 */
@Path("vehicles")
public class ServiceRestVehicles {

    private static final String PERSISTENCE_UNIT = "EasyCooperAPIRESTPU";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        List<Vehicle> list;
        try {

            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            VehicleJpaController dao = new VehicleJpaController(emf);
            list = dao.findVehicleEntities();
            if (list == null) {
                statusResul = Response.Status.NO_CONTENT;
                response = Response
                        .status(statusResul)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(list)
                        .build();
            }
        } catch (Exception ex) {

            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Vehicle veh) {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            VehicleJpaController dao = new VehicleJpaController(emf);

            // Si viene ID y existe, retorna FOUND
            if (veh.getId() != null && veh.getId() != 0) {
                Vehicle empFound = dao.findVehicle(veh.getId());
                if (empFound != null) {
                    statusResul = Response.Status.FOUND;
                    mensaje.put("mensaje", "Ya existe vehículo con ID " + veh.getId());
                    return Response.status(statusResul).entity(mensaje).build();
                }
            }

            // Deja que la BD genere el ID
            dao.create(veh);

            statusResul = Response.Status.CREATED;
            mensaje.put("mensaje", "Vehículo " + veh.getModel() + " grabado correctamente.");
            response = Response.status(statusResul).entity(mensaje).build();

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición.");
            mensaje.put("error", ex.getMessage());
            response = Response.status(statusResul).entity(mensaje).build();
        } finally {
            if (emf != null) {
                emf.close();
            }
        }

        return response;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Vehicle veh) {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        try {
            if (veh == null || veh.getId() == null) {
                statusResul = Response.Status.BAD_REQUEST;
                mensaje.put("mensaje", "Datos incompletos: falta el ID del vehículo.");
                return Response.status(statusResul).entity(mensaje).build();
            }

            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            VehicleJpaController dao = new VehicleJpaController(emf);
            Vehicle existing = dao.findVehicle(veh.getId());

            if (existing == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No se encontró el vehículo a actualizar.");
                response = Response.status(statusResul).entity(mensaje).build();
            } else {
                if (veh.getReservationsCollection() == null) {
                    veh.setReservationsCollection(new ArrayList<>());
                }
                dao.edit(veh);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Vehículo actualizado correctamente.");
                response = Response.status(statusResul).entity(mensaje).build();
            }

        } catch (Exception ex) {
            ex.printStackTrace(); // Para ver el error exacto en consola
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", ex.getMessage());
            response = Response.status(statusResul).entity(mensaje).build();
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
        return response;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            VehicleJpaController dao = new VehicleJpaController(emf);

            //Buscar el vehiculo si existe
            Vehicle existing = dao.findVehicle(id);

            //Lo borrar si lo encuentra
            if (existing == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe el vehiculo que desea borrar ");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(id);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Vehiculo borrada correctamente");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
        return response;
    }
}
