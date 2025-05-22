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
public class UserecJpaController implements Serializable {

    public UserecJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Userec userec) {
        if (userec.getReservationsCollection() == null) {
            userec.setReservationsCollection(new ArrayList<Reservations>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Reservations> attachedReservationsCollection = new ArrayList<Reservations>();
            for (Reservations reservationsCollectionReservationsToAttach : userec.getReservationsCollection()) {
                reservationsCollectionReservationsToAttach = em.getReference(reservationsCollectionReservationsToAttach.getClass(), reservationsCollectionReservationsToAttach.getId());
                attachedReservationsCollection.add(reservationsCollectionReservationsToAttach);
            }
            userec.setReservationsCollection(attachedReservationsCollection);
            em.persist(userec);
            for (Reservations reservationsCollectionReservations : userec.getReservationsCollection()) {
                Userec oldIdUserOfReservationsCollectionReservations = reservationsCollectionReservations.getIdUser();
                reservationsCollectionReservations.setIdUser(userec);
                reservationsCollectionReservations = em.merge(reservationsCollectionReservations);
                if (oldIdUserOfReservationsCollectionReservations != null) {
                    oldIdUserOfReservationsCollectionReservations.getReservationsCollection().remove(reservationsCollectionReservations);
                    oldIdUserOfReservationsCollectionReservations = em.merge(oldIdUserOfReservationsCollectionReservations);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Userec userec) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Userec persistentUserec = em.find(Userec.class, userec.getId());
            Collection<Reservations> reservationsCollectionOld = persistentUserec.getReservationsCollection();
            Collection<Reservations> reservationsCollectionNew = userec.getReservationsCollection();
            Collection<Reservations> attachedReservationsCollectionNew = new ArrayList<Reservations>();
            for (Reservations reservationsCollectionNewReservationsToAttach : reservationsCollectionNew) {
                reservationsCollectionNewReservationsToAttach = em.getReference(reservationsCollectionNewReservationsToAttach.getClass(), reservationsCollectionNewReservationsToAttach.getId());
                attachedReservationsCollectionNew.add(reservationsCollectionNewReservationsToAttach);
            }
            reservationsCollectionNew = attachedReservationsCollectionNew;
            userec.setReservationsCollection(reservationsCollectionNew);
            userec = em.merge(userec);
            for (Reservations reservationsCollectionOldReservations : reservationsCollectionOld) {
                if (!reservationsCollectionNew.contains(reservationsCollectionOldReservations)) {
                    reservationsCollectionOldReservations.setIdUser(null);
                    reservationsCollectionOldReservations = em.merge(reservationsCollectionOldReservations);
                }
            }
            for (Reservations reservationsCollectionNewReservations : reservationsCollectionNew) {
                if (!reservationsCollectionOld.contains(reservationsCollectionNewReservations)) {
                    Userec oldIdUserOfReservationsCollectionNewReservations = reservationsCollectionNewReservations.getIdUser();
                    reservationsCollectionNewReservations.setIdUser(userec);
                    reservationsCollectionNewReservations = em.merge(reservationsCollectionNewReservations);
                    if (oldIdUserOfReservationsCollectionNewReservations != null && !oldIdUserOfReservationsCollectionNewReservations.equals(userec)) {
                        oldIdUserOfReservationsCollectionNewReservations.getReservationsCollection().remove(reservationsCollectionNewReservations);
                        oldIdUserOfReservationsCollectionNewReservations = em.merge(oldIdUserOfReservationsCollectionNewReservations);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userec.getId();
                if (findUserec(id) == null) {
                    throw new NonexistentEntityException("The userec with id " + id + " no longer exists.");
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
            Userec userec;
            try {
                userec = em.getReference(Userec.class, id);
                userec.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userec with id " + id + " no longer exists.", enfe);
            }
            Collection<Reservations> reservationsCollection = userec.getReservationsCollection();
            for (Reservations reservationsCollectionReservations : reservationsCollection) {
                reservationsCollectionReservations.setIdUser(null);
                reservationsCollectionReservations = em.merge(reservationsCollectionReservations);
            }
            em.remove(userec);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Userec> findUserecEntities() {
        return findUserecEntities(true, -1, -1);
    }

    public List<Userec> findUserecEntities(int maxResults, int firstResult) {
        return findUserecEntities(false, maxResults, firstResult);
    }

    private List<Userec> findUserecEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Userec.class));
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

    public Userec findUserec(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Userec.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserecCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Userec> rt = cq.from(Userec.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
