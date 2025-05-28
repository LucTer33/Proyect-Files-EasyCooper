/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import easycooper.Reservations;
import easycooper.ReservationsJpaController;
import easycooper.Userec;
import easycooper.UserecJpaController;
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
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lucas Teran
 */
@Path("reservations")
public class ServiceRestReservations {

    private static final String PERSISTENCE_UNIT = "EasyCooperAPIRESTPU";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Reservations> list;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            ReservationsJpaController dao = new ReservationsJpaController(emf);
            list = dao.findReservationsEntities();

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
            ex.printStackTrace();  // Esto es importante

            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            mensaje.put("error", ex.toString());  // <-- ex.toString te da más información útil
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
    public Response post(Reservations res) {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            ReservationsJpaController dao = new ReservationsJpaController(emf);
            UserecJpaController userDao = new UserecJpaController(emf);
            VehicleJpaController vehicleDao = new VehicleJpaController(emf);

            // Obtener claves primarias
            // Buscar si existen el usuario que lo solicita
            Userec user = userDao.findUserec(res.getIdUser().getId());
            Vehicle vehicle = vehicleDao.findVehicle(res.getIdVehicle().getId());

            if (user == null || vehicle == null) {
                statusResul = Response.Status.BAD_REQUEST;
                mensaje.put("mensaje", "Datos erróneos: El usuario o el vehículo no existen.");
                return Response.status(statusResul).entity(mensaje).build();
            }

            // Comprobar si ya existe un vehiculo ocupado a esas horas
            boolean ocupado = false;
            List<Reservations> reservationsFound = dao.findReservationsEntities();

            LocalDate fechaReservaNueva = res.getDateReservation().toInstant()
                    .atOffset(ZoneOffset.UTC)
                    .toLocalDate();
            ocupado = reservationsFound.stream().filter(x -> x.getIdVehicle().getId().equals(res.getIdVehicle().getId()) && x.getDateReservation().equals(fechaReservaNueva) && res.getInitHour().before(x.getFinalHour()) && res.getFinalHour().after(x.getInitHour())).count() > 0;

            System.out.println("esta ocupado?: " + ocupado);
            if (ocupado) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "La reserva no puede operarse, ya que el vehiculo esta ocupado");
                return Response.status(statusResul).entity(mensaje).build();
            }

            // Crear la nueva reserva
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
            Timestamp timestamp = Timestamp.valueOf(zonedDateTime.toLocalDateTime());
            res.setDateCreation(timestamp);

            dao.create(res);
            statusResul = Response.Status.CREATED;
            mensaje.put("mensaje", "Reserva creada correctamente.");
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
    public Response update(Reservations res) {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            ReservationsJpaController dao = new ReservationsJpaController(emf);

            // Buscar la reserva actual
            Reservations existing = dao.findReservations(res.getId());

            if (existing == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No se encontró la reserva a actualizar.");
                response = Response.status(statusResul).entity(mensaje).build();

            } else {
                res.setDateCreation(existing.getDateCreation());

                boolean ocupado = false;
                List<Reservations> reservationsFound = dao.findReservationsEntities();
                //2(reserva a actualizar) -->1(reserva grabada)
                System.out.println("initHour en request: " + res.getInitHour());
                System.out.println("finalHour en request: " + res.getFinalHour());

                LocalDate fechaReservaNueva = res.getDateReservation().toInstant()
                        .atOffset(ZoneOffset.UTC)
                        .toLocalDate();

                LocalDate fechaReservaExistente = reservationsFound.get(0).getDateReservation().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                
                 if (res.getInitHour() != null) {
                        res.setInitHour(new Date(res.getInitHour().getTime() - 3600_000));
                    }
                    if (res.getFinalHour() != null) {
                        res.setFinalHour(new Date(res.getFinalHour().getTime() - 3600_000));
                    }
                    System.out.println("formateado initHour en request: " + res.getInitHour());
                    System.out.println("formateado finalHour en request: " + res.getFinalHour());
                System.out.println("Es distinto el id de res: " + res.getId() + " es igual al del guardado?: " + reservationsFound.get(0).getId() + " : " + !res.getId().equals(reservationsFound.get(0).getId()));
                System.out.println("Es igual el idUser de res: " + res.getIdUser().getId() + "  al del guardado?: " + reservationsFound.get(0).getIdUser().getId() + " : " + res.getIdUser().getId().equals(reservationsFound.get(0).getIdUser().getId()));
                System.out.println("Es distinto el idUser de res: " + res.getIdUser().getId() + "  al del guardado?: " + reservationsFound.get(0).getIdUser().getId() + " : " + !res.getIdUser().getId().equals(reservationsFound.get(0).getIdUser().getId()));
                System.out.println("Es igual el idVehicle de res: " + res.getIdVehicle().getId() + "  al del guardado?: " + reservationsFound.get(0).getIdVehicle().getId() + " : " + res.getIdVehicle().getId().equals(reservationsFound.get(0).getIdVehicle().getId()));
                System.out.println("Es igual el DateReservation de res: " + fechaReservaNueva + "  al del guardado?: " + fechaReservaExistente + " : " + fechaReservaNueva.equals(fechaReservaExistente));
                System.out.println("Es previo la InitHour de res: " + res.getInitHour() + " de el guardado?: " + reservationsFound.get(0).getInitHour() + " : " + res.getInitHour().before(reservationsFound.get(0).getFinalHour()));
                System.out.println("Es posterior la FinalHourt de res: " + res.getFinalHour() + " de el guardado?: " + reservationsFound.get(0).getFinalHour() + " : " + res.getFinalHour().after(reservationsFound.get(0).getInitHour()));

                ocupado = reservationsFound.stream().filter(x -> !x.getId().equals(res.getId())
                        && //Si es otra reserva 
                        (x.getIdUser().getId().equals(res.getIdUser().getId()) || !x.getIdUser().getId().equals(res.getIdUser().getId()))
                        && //Si es el mismo
                        x.getIdVehicle().getId().equals(res.getIdVehicle().getId())
                        && //Si es el mismo vehiculo
                        x.getDateReservation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(fechaReservaNueva)
                        && //Si es el mismo dia
                        res.getInitHour().before(x.getFinalHour())
                        && //Si hay alguna reserva con hora initi al antes de la hora final de otra reserva
                        res.getFinalHour().after(x.getInitHour())).count() > 0; //Si hay alguna reserva con hora final despues de la hora inicial de otra reserva 

                System.out.println("esta ocupado?: " + ocupado);
                if (ocupado) {
                    statusResul = Response.Status.FOUND;
                    mensaje.put("mensaje", "La hora cambiada coincide con otra reserva");
                    response = Response.status(statusResul).entity(mensaje).build();
                } else {

//                    if (res.getInitHour() != null) {
//                        res.setInitHour(new Date(res.getInitHour().getTime() + 3600_000));
//                    }
//                    if (res.getFinalHour() != null) {
//                        res.setFinalHour(new Date(res.getFinalHour().getTime() + 3600_000));
//                    }
//                    System.out.println("initHour en request: " + res.getInitHour());
//                    System.out.println("finalHour en request: " + res.getFinalHour());
                    dao.edit(res);  // Método generado por NetBeans al usar JPA Controller
                    statusResul = Response.Status.OK;
                    mensaje.put("mensaje", "Reserva actualizada correctamente.");
                    response = Response.status(statusResul).entity(mensaje).build();
                }
            }

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición.");
            mensaje.put("error", ex.getMessage());
            mensaje.put("errorLocal", ex.getLocalizedMessage());
            response = Response.status(statusResul).entity(mensaje).build();
        } finally {
            if (emf != null) {
                emf.close();
            }
        }

        return response;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(Reservations res) {
        EntityManagerFactory emf = null;
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            ReservationsJpaController dao = new ReservationsJpaController(emf);

            Reservations existing = dao.findReservations(res.getId());

            if (existing == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe una reserva con ese vehiculo");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(res.getId());
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Reserva borrada correctamente");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición.");
            mensaje.put("error", ex.getMessage());
            mensaje.put("errorLocal", ex.getLocalizedMessage());
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
//ocupado = reservationsFound.stream().filter(x -> !x.getId().equals(res.getId())
//                        && //Si es otra reserva 
//                        (x.getIdUser().getId().equals(res.getIdUser().getId()) || !x.getIdUser().getId().equals(res.getIdUser().getId()))
//                        && //Si es el mismo
//                        x.getIdVehicle().getId().equals(res.getIdVehicle().getId())
//                        && //Si es el mismo vehiculo
//                        x.getDateReservation().equals(res.getDateReservation())
//                        && //Si es el mismo dia
//                        res.getInitHour().before(x.getFinalHour())
//                        && //Si hay alguna reserva con hora initi al antes de la hora final de otra reserva
//                        res.getFinalHour().after(x.getInitHour())).count() > 0; //Si hay alguna reserva con hora final despues de la hora inicial de otra reserva 
