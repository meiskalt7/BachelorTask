package org.meiskalt7.crud;

import org.meiskalt7.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderService {

    public OrderService() {

    }

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();


    public Order add(Order category) {
        em.getTransaction().begin();
        Order categoryFromDB = em.merge(category);
        em.getTransaction().commit();
        return categoryFromDB;
    }

    public void delete(int id) {
        Order category = get(id);
        em.getTransaction().begin();
        em.remove(category);
        em.getTransaction().commit();
    }

    public void update(Order category) {
        em.getTransaction().begin();
        em.merge(category);
        em.getTransaction().commit();
    }

    public Order get(int id) {
        return em.find(Order.class, id);
    }

    public List<Order> getAll() {
        TypedQuery<Order> namedQuery = em.createNamedQuery("Categories.getAll", Order.class);
        return namedQuery.getResultList();
    }

    public void deleteAll() {
        for (Order cat : getAll()) {
            delete(cat.getId());
        }
    }

    public int getId(String category) {
        return (Integer) em.createQuery("SELECT id FROM Category WHERE name = :category").setParameter("category", category).getResultList().get(0);
    }
}
