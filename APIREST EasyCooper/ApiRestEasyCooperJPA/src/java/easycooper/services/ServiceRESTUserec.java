/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easycooper.services;


import easycooper.dataservices.Userec;
import easycooper.dataservices.UserecJpaController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

@Path("users")
public class ServiceRESTUserec {

    private static final String PERSISTENCE_UNIT = "ApiRestEasyCooperJPAPU";
    //Obtener todos
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        List<Userec> list;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            UserecJpaController dao = new UserecJpaController(emf);
            list = dao.findUserecEntities();
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
            mensaje.put("mensaje", "Erro al procesar la petición");
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
    //Obtener un elemento
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        Userec user = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            UserecJpaController dao = new UserecJpaController(emf);
            user = dao.findUserec(id);
            if (user == null) {
                statusResul = Response.Status.NOT_FOUND;
                response = Response
                        .status(statusResul)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(user)
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

    //Inserta un elemento
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Userec userec) {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        Userec userFound = null;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            UserecJpaController dao = new UserecJpaController(emf);

            if ((userec.getId() != 0) && (userec.getId() != null)) {
                userFound = dao.findUserec(userec.getId());
            }

            if (userFound != null) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "Ya existe usuario con ID " + userec.getId());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.create(userec);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "Usuario " + userec.getNameUser() + " grabado");
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
