/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easycooper.dataservices;

import easycooper.dataservices.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Lucas Teran
 */
public class VehicleJpaController implements Serializable {

    public VehicleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehicle vehicle) {
        if (vehicle.getReservationsCollection() == null) {
            vehicle.setReservationsCollection(new ArrayList<Reservations>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Reservations> attachedReservationsCollection = new ArrayList<Reservations>();
            for (Reservations reservationsCollectionReservationsToAttach : vehicle.getReservationsCollection()) {
                reservationsCollectionReservationsToAttach = em.getReference(reservationsCollectionReservationsToAttach.getClass(), reservationsCollectionReservationsToAttach.getId());
                attachedReservationsCollection.add(reservationsCollectionReservationsToAttach);
            }
            vehicle.setReservationsCollection(attachedReservationsCollection);
            em.persist(vehicle);
            for (Reservations reservationsCollectionReservations : vehicle.getReservationsCollection()) {
                Vehicle oldIdVehicleOfReservationsCollectionReservations = reservationsCollectionReservations.getIdVehicle();
                reservationsCollectionReservations.setIdVehicle(vehicle);
                reservationsCollectionReservations = em.merge(reservationsCollectionReservations);
                if (oldIdVehicleOfReservationsCollectionReservations != null) {
                    oldIdVehicleOfReservationsCollectionReservations.getReservationsCollection().remove(reservationsCollectionReservations);
                    oldIdVehicleOfReservationsCollectionReservations = em.merge(oldIdVehicleOfReservationsCollectionReservations);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vehicle vehicle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehicle persistentVehicle = em.find(Vehicle.class, vehicle.getId());
            Collection<Reservations> reservationsCollectionOld = persistentVehicle.getReservationsCollection();
            Collection<Reservations> reservationsCollectionNew = vehicle.getReservationsCollection();
            Collection<Reservations> attachedReservationsCollectionNew = new ArrayList<Reservations>();
            for (Reservations reservationsCollectionNewReservationsToAttach : reservationsCollectionNew) {
                reservationsCollectionNewReservationsToAttach = em.getReference(reservationsCollectionNewReservationsToAttach.getClass(), reservationsCollectionNewReservationsToAttach.getId());
                attachedReservationsCollectionNew.add(reservationsCollectionNewReservationsToAttach);
            }
            reservationsCollectionNew = attachedReservationsCollectionNew;
            vehicle.setReservationsCollection(reservationsCollectionNew);
            vehicle = em.merge(vehicle);
            for (Reservations reservationsCollectionOldReservations : reservationsCollectionOld) {
                if (!reservationsCollectionNew.contains(reservationsCollectionOldReservations)) {
                    reservationsCollectionOldReservations.setIdVehicle(null);
                    reservationsCollectionOldReservations = em.merge(reservationsCollectionOldReservations);
                }
            }
            for (Reservations reservationsCollectionNewReservations : reservationsCollectionNew) {
                if (!reservationsCollectionOld.contains(reservationsCollectionNewReservations)) {
                    Vehicle oldIdVehicleOfReservationsCollectionNewReservations = reservationsCollectionNewReservations.getIdVehicle();
                    reservationsCollectionNewReservations.setIdVehicle(vehicle);
                    reservationsCollectionNewReservations = em.merge(reservationsCollectionNewReservations);
                    if (oldIdVehicleOfReservationsCollectionNewReservations != null && !oldIdVehicleOfReservationsCollectionNewReservations.equals(vehicle)) {
                        oldIdVehicleOfReservationsCollectionNewReservations.getReservationsCollection().remove(reservationsCollectionNewReservations);
                        oldIdVehicleOfReservationsCollectionNewReservations = em.merge(oldIdVehicleOfReservationsCollectionNewReservations);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vehicle.getId();
                if (findVehicle(id) == null) {
                    throw new NonexistentEntityException("The vehicle with id " + id + " no longer exists.");
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
            Vehicle vehicle;
            try {
                vehicle = em.getReference(Vehicle.class, id);
                vehicle.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehicle with id " + id + " no longer exists.", enfe);
            }
            Collection<Reservations> reservationsCollection = vehicle.getReservationsCollection();
            for (Reservations reservationsCollectionReservations : reservationsCollection) {
                reservationsCollectionReservations.setIdVehicle(null);
                reservationsCollectionReservations = em.merge(reservationsCollectionReservations);
            }
            em.remove(vehicle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehicle> findVehicleEntities() {
        return findVehicleEntities(true, -1, -1);
    }

    public List<Vehicle> findVehicleEntities(int maxResults, int firstResult) {
        return findVehicleEntities(false, maxResults, firstResult);
    }

    private List<Vehicle> findVehicleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vehicle.class));
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

    public Vehicle findVehicle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehicle.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehicleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vehicle> rt = cq.from(Vehicle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
