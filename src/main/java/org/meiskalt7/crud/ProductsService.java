package org.meiskalt7.crud;

import org.meiskalt7.entity.Products;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Eiskalt on 12.10.2015.
 */
public class ProductsService {

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    public Products add(Products products) {
        em.getTransaction().begin();
        Products productsFromDB = em.merge(products);
        em.getTransaction().commit();
        return productsFromDB;
    }

    public void delete(int id) {
        em.getTransaction().begin();
        em.remove(get(id));
    }

    public Products get(int id) {
        return em.find(Products.class, id);
    }

    public List<Products> getByCat(int id) {
        //return (Integer)em.createQuery("SELECT id FROM Categories WHERE name = '" + category + "'").getResultList().get(0);
        return em.createQuery("SELECT p FROM Products p WHERE cat_id = " + id + "").getResultList();
    }

    public void update(Products products) {
        em.getTransaction().begin();
        em.merge(products);
        em.getTransaction().commit();
    }

    public List<Products> getAll() {
        TypedQuery<Products> namedQuery = em.createNamedQuery("Products.getAll", Products.class);
        return namedQuery.getResultList();
    }
}
