package org.meiskalt7.crud;

import org.meiskalt7.entity.Orderlist;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class OrderlistService implements GenericDao<Orderlist> {

    private static OrderlistService orderlistService;

    @PersistenceContext
    private final EntityManager em = EntityManagerUtil.getEntityManager();

    private OrderlistService() {

    }

    public static synchronized OrderlistService getInstance() {
        if (orderlistService == null) {
            orderlistService = new OrderlistService();
        }
        return orderlistService;
    }

    public void add(Orderlist orderlist) {
        em.getTransaction().begin();
        em.persist(orderlist);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Orderlist orderlist = get(id);
        em.getTransaction().begin();
        em.remove(orderlist);
        em.getTransaction().commit();
    }

    public void update(Orderlist orderlist) {
        em.getTransaction().begin();
        em.merge(orderlist);
        em.getTransaction().commit();
    }

    public Orderlist get(int id) {
        return em.find(Orderlist.class, id);
    }

    public List<Orderlist> getAll() {
        return (List<Orderlist>) em.createQuery("SELECT o FROM Orderlist o").getResultList();
    }

    public void deleteAll() {
        for (Orderlist orderlist : getAll()) {
            delete(orderlist.getId());
        }
    }
}
