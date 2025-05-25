/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easycooper;

import easycooper.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author Lucas Teran
 */
public class ReservationsJpaController implements Serializable {

    public ReservationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reservations reservations) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Userec idUser = reservations.getIdUser();
            if (idUser != null) {
                idUser = em.getReference(idUser.getClass(), idUser.getId());
                reservations.setIdUser(idUser);
            }
            Vehicle idVehicle = reservations.getIdVehicle();
            if (idVehicle != null) {
                idVehicle = em.getReference(idVehicle.getClass(), idVehicle.getId());
                reservations.setIdVehicle(idVehicle);
            }
            em.persist(reservations);
            if (idUser != null) {
                idUser.getReservationsCollection().add(reservations);
                idUser = em.merge(idUser);
            }
            if (idVehicle != null) {
                idVehicle.getReservationsCollection().add(reservations);
                idVehicle = em.merge(idVehicle);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reservations reservations) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservations persistentReservations = em.find(Reservations.class, reservations.getId());
            Userec idUserOld = persistentReservations.getIdUser();
            Userec idUserNew = reservations.getIdUser();
            Vehicle idVehicleOld = persistentReservations.getIdVehicle();
            Vehicle idVehicleNew = reservations.getIdVehicle();
            if (idUserNew != null) {
                idUserNew = em.getReference(idUserNew.getClass(), idUserNew.getId());
                reservations.setIdUser(idUserNew);
            }
            if (idVehicleNew != null) {
                idVehicleNew = em.getReference(idVehicleNew.getClass(), idVehicleNew.getId());
                reservations.setIdVehicle(idVehicleNew);
            }
            reservations = em.merge(reservations);
            if (idUserOld != null && !idUserOld.equals(idUserNew)) {
                idUserOld.getReservationsCollection().remove(reservations);
                idUserOld = em.merge(idUserOld);
            }
            if (idUserNew != null && !idUserNew.equals(idUserOld)) {
                idUserNew.getReservationsCollection().add(reservations);
                idUserNew = em.merge(idUserNew);
            }
            if (idVehicleOld != null && !idVehicleOld.equals(idVehicleNew)) {
                idVehicleOld.getReservationsCollection().remove(reservations);
                idVehicleOld = em.merge(idVehicleOld);
            }
            if (idVehicleNew != null && !idVehicleNew.equals(idVehicleOld)) {
                idVehicleNew.getReservationsCollection().add(reservations);
                idVehicleNew = em.merge(idVehicleNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservations.getId();
                if (findReservations(id) == null) {
                    throw new NonexistentEntityException("The reservations with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservations reservations;
            try {
                reservations = em.getReference(Reservations.class, id);
                reservations.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservations with id " + id + " no longer exists.", enfe);
            }
            Userec idUser = reservations.getIdUser();
            if (idUser != null) {
                idUser.getReservationsCollection().remove(reservations);
                idUser = em.merge(idUser);
            }
            Vehicle idVehicle = reservations.getIdVehicle();
            if (idVehicle != null) {
                idVehicle.getReservationsCollection().remove(reservations);
                idVehicle = em.merge(idVehicle);
            }
            em.remove(reservations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reservations> findReservationsEntities() {
        return findReservationsEntities(true, -1, -1);
    }

    public List<Reservations> findReservationsEntities(int maxResults, int firstResult) {
        return findReservationsEntities(false, maxResults, firstResult);
    }

    private List<Reservations> findReservationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reservations.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Reservations findReservations(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservations.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reservations> rt = cq.from(Reservations.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
