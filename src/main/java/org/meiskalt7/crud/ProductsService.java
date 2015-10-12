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
        return em.createQuery("SELECT p FROM Products p WHERE cat_id = " + id + "").getResultList();
    }

    public List<Products> getByName(String name) {
        return em.createQuery("SELECT p FROM Products p WHERE name = '" + name + "'").getResultList();
    }

    public List<Products> getByPriceFrom(double price) {
        return em.createQuery("SELECT p FROM Products p WHERE price > " + price + "").getResultList();
    }

    public int getCatByName(String name) {
        return (Integer) em.createQuery("SELECT cat_id FROM Products p WHERE name = '" + name + "'").getResultList().get(0);
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

    public List<Products> getTest(String name) {
        TypedQuery<Products> query = em.createQuery("SELECT p FROM Products p WHERE p.name = :name", Products.class);
        return query.setParameter("name", name).getResultList();
    }

    public List<Products> getHQL(String category, String name, Double priceFrom, Double priceTo) {
        List<Products> products = (List<Products>)em.createQuery("from Products");
        TypedQuery<Products> query = em.createQuery("SELECT p FROM Products as p INNER JOIN p.categories " +
                "WHERE (p.categories.name = :category OR :category = null) AND (p.name = :name OR :name = null) " +
                "AND (p.price > :priceFrom OR :priceFrom = null) AND (p.price < :priceTo OR :priceTo = null)", Products.class);
        return query
                .setParameter("category", category)
                .setParameter("name", name)
                .setParameter("priceFrom", priceFrom)
                .setParameter("priceTo", priceTo)
                .getResultList();
    }
}
