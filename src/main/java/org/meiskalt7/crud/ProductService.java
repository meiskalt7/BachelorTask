package org.meiskalt7.crud;

import org.meiskalt7.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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

    public void update(Product product) {
        em.getTransaction().begin();
        em.merge(product);
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
            if (category.length() == 0) category = null;
            if (name.length() == 0) name = null;
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product as p INNER JOIN p.category WHERE (lower(p.category.name) LIKE :category OR :category IS NULL) AND (lower(p.name) LIKE :name OR :name IS NULL) AND (p.price >= :priceFrom OR :priceFrom = 0) AND (p.price <= :priceTo OR :priceTo = 0)", Product.class);

            List<Product> list = query
                    .setParameter("category", category)
                    .setParameter("name", name)
                    .setParameter("priceFrom", priceFrom)
                    .setParameter("priceTo", priceTo)
                    .getResultList();
            return list;
        } else return null;
    }

    public static void main(String[] args) {
        ProductService ps = new ProductService();
        List<Product> hql = ps.getHQL(null, null, .0, .0);
        System.out.println("  !  ");
    }
}
