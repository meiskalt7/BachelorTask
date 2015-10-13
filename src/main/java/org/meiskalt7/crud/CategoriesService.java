package org.meiskalt7.crud;

import org.meiskalt7.entity.Categories;
import org.meiskalt7.entity.Products;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * @author Eiskalt on 12.10.2015.
 */
public class CategoriesService {

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    public Categories add(Categories categories) {
        em.getTransaction().begin();
        Categories categoriesFromDB = em.merge(categories);
        em.getTransaction().commit();
        return categoriesFromDB;
    }

    public void delete(int id) {
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public Categories get(int id) {
        return em.find(Categories.class, id);
    }

    public void update(Categories categories) {
        em.getTransaction().begin();
        em.merge(categories);
        em.getTransaction().commit();
    }

    public List<Categories> getAll() {
        TypedQuery<Categories> namedQuery = em.createNamedQuery("Categories.getAll", Categories.class);
        return namedQuery.getResultList();
    }

    public void deleteAll() {
        for (Categories cat : getAll()) {
            delete(cat.getId());
        }
    }

    public int getId(String category) {
        return (Integer) em.createQuery("SELECT id FROM Categories WHERE name = :category").setParameter("category", category).getResultList().get(0);
    }

    public List<Categories> getSome(List<Products> products) {
        List<Categories> categories = getAll();
        List<Categories> categories1 = new ArrayList<Categories>();
        for(Products p: products) categories1.add(categories.get(p.getCat_id()));
        return categories1;
    }

    public List<Categories> getHQL(String category, String name, Double priceFrom, Double priceTo) {
        TypedQuery<Categories> query = em.createQuery("SELECT c FROM Categories c WHERE id IN ( SELECT cat_id FROM Products p " +
                "WHERE (p.category.name = :category OR :category = null) AND (p.name = :name OR :name = null) " +
                "AND (p.price > :priceFrom OR :priceFrom = 0) AND (p.price < :priceTo OR :priceTo = 0))", Categories.class);
        return query
                .setParameter("category", category)
                .setParameter("name", name)
                .setParameter("priceFrom", priceFrom)
                .setParameter("priceTo", priceTo)
                .getResultList();
    }
}