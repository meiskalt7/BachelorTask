package org.meiskalt7.crud;

import org.meiskalt7.entity.Category;
import org.meiskalt7.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Eiskalt on 12.10.2015.
 */
public class ProductService {

    public ProductService() {

    }

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();


    public Product add(Product product) {
        em.getTransaction().begin();
        Product productFromDB = em.merge(product);
        em.getTransaction().commit();
        return productFromDB;
    }

    public void delete(int id) {
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public void update(Product serviceType) {
        em.getTransaction().begin();
        em.merge(serviceType);
        em.getTransaction().commit();
    }

    public Product get(int id) {
        return em.find(Product.class, id);
    }

    public List<Product> getAll() {
        TypedQuery<Product> namedQuery = em.createNamedQuery("Products.getAll", Product.class);
        return namedQuery.getResultList();
    }

    public void deleteAll() {
        for (Product prod : getAll()) {
            delete(prod.getId());
        }
    }

    public int getId(String product) {
        return (Integer) em.createQuery("SELECT id FROM Category WHERE name = :product").setParameter("product", product).getResultList().get(0);
    }

    public List<Product> getHQL(String category, String name, Double priceFrom, Double priceTo) {
        if (priceFrom >= 0 & priceTo >= 0) {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product as p INNER JOIN p.category " +
                    "WHERE (lower(p.category.name) LIKE :category OR :category = null) AND (lower(p.name) = :name OR :name = null) " +
                    "AND (p.price >= :priceFrom OR :priceFrom = 0) AND (p.price <= :priceTo OR :priceTo = 0)", Product.class);

            return query
                    .setParameter("category", "%" + category + "%")
                    .setParameter("name", name)
                    .setParameter("priceFrom", priceFrom)
                    .setParameter("priceTo", priceTo)
                    .getResultList();
        } else return null;
    }
}
