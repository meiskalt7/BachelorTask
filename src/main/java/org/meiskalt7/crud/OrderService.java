package org.meiskalt7.crud;

import org.meiskalt7.entity.Order;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderService implements GenericDao<Order> {

    private static OrderService orderService;

    @PersistenceContext
    private EntityManager em = EntityManagerUtil.getEntityManager();

    public OrderService() {

    }

    public static synchronized OrderService getInstance() {
        if (orderService == null) {
            orderService = new OrderService();
        }
        return orderService;
    }

    public void add(Order order) {
        em.getTransaction().begin();
        em.persist(order);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Order order = get(id);
        em.getTransaction().begin();
        em.remove(order);
        em.getTransaction().commit();
    }

    public void update(Order order) {
        em.getTransaction().begin();
        em.merge(order);
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
