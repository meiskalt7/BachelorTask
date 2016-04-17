package org.meiskalt7.crud;

import org.meiskalt7.entity.Orderlist;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderlistService {

    public OrderlistService() {

    }

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();


    public Orderlist add(Orderlist category) {
        em.getTransaction().begin();
        Orderlist categoryFromDB = em.merge(category);
        em.getTransaction().commit();
        return categoryFromDB;
    }

    public void delete(int id) {
        Orderlist category = get(id);
        em.getTransaction().begin();
        em.remove(category);
        em.getTransaction().commit();
    }

    public void update(Orderlist category) {
        em.getTransaction().begin();
        em.merge(category);
        em.getTransaction().commit();
    }

    public Orderlist get(int id) {
        return em.find(Orderlist.class, id);
    }

    public List<Orderlist> getAll() {
        TypedQuery<Orderlist> namedQuery = em.createNamedQuery("Categories.getAll", Orderlist.class);
        return namedQuery.getResultList();
    }

    public void deleteAll() {
        for (Orderlist orderlist : getAll()) {
            delete(orderlist.getId());
        }
    }

    public int getId(String category) {
        return (Integer) em.createQuery("SELECT id FROM Category WHERE name = :category").setParameter("category", category).getResultList().get(0);
    }
}
