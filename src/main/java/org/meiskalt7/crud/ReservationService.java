package org.meiskalt7.crud;

import org.meiskalt7.entity.Reservation;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ReservationService implements GenericDao<Reservation> {

    private static ReservationService reservationService;

    @PersistenceContext
    public EntityManager em = EntityManagerUtil.getEntityManager();

    public ReservationService() {

    }

    public static synchronized ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void add(Reservation reservation) {
        em.getTransaction().begin();
        em.persist(reservation);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Reservation reservation = get(id);
        em.getTransaction().begin();
        em.remove(reservation);
        em.getTransaction().commit();
    }

    public void update(Reservation reservation) {
        em.getTransaction().begin();
        em.merge(reservation);
        em.getTransaction().commit();
    }

    public Reservation get(int id) {
        return em.find(Reservation.class, id);
    }

    public List<Reservation> getAll() {
        return (List<Reservation>) em.createQuery("SELECT r FROM Reservation r").getResultList();
    }

    public void deleteAll() {
        for (Reservation cat : getAll()) {
            delete(cat.getId());
        }
    }

    public int getId(String category) {
        return (Integer) em.createQuery("SELECT id FROM Category WHERE name = :category").setParameter("category", category).getResultList().get(0);
    }
}
