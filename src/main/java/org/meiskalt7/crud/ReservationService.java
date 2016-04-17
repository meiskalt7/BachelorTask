package org.meiskalt7.crud;

import org.meiskalt7.entity.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ReservationService {

    public ReservationService() {

    }

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();


    public Reservation add(Reservation category) {
        em.getTransaction().begin();
        Reservation categoryFromDB = em.merge(category);
        em.getTransaction().commit();
        return categoryFromDB;
    }

    public void delete(int id) {
        Reservation category = get(id);
        em.getTransaction().begin();
        em.remove(category);
        em.getTransaction().commit();
    }

    public void update(Reservation category) {
        em.getTransaction().begin();
        em.merge(category);
        em.getTransaction().commit();
    }

    public Reservation get(int id) {
        return em.find(Reservation.class, id);
    }

    public List<Reservation> getAll() {
        TypedQuery<Reservation> namedQuery = em.createNamedQuery("Categories.getAll", Reservation.class);
        return namedQuery.getResultList();
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
