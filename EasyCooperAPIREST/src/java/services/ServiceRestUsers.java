/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import easycooper.Userec;
import easycooper.UserecJpaController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Lucas Teran
 */
@Path("users")
public class ServiceRestUsers {

    private static final String PERSISTENCE_UNIT = "EasyCooperAPIRESTPU";

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

    //Verifica contraseña
    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

            Userec user = findByEmailAndPassword(email, password, emf.createEntityManager());

            if (user == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "Email o contraseña incorrectos");
                response = Response.status(statusResul).entity(mensaje).build();
            } else {
                statusResul = Response.Status.OK;
                response = Response.status(statusResul).entity(user).build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.INTERNAL_SERVER_ERROR;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response.status(statusResul).entity(mensaje).build();
        } finally {
            if (emf != null) {
                emf.close();
            }
        }

        return response;
    }

    public Userec findByEmailAndPassword(String email, String password, EntityManager em) {
        TypedQuery<Userec> query = em.createQuery(
                "SELECT u FROM Userec u WHERE u.email = :email AND u.password = :password", Userec.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<Userec> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
