package org.meiskalt7.crud;

import org.meiskalt7.entity.Product;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductService implements GenericDao<Product> {

    private static ProductService productService;
    @PersistenceContext
    public EntityManager em = EntityManagerUtil.getEntityManager();

    private ProductService() {
    }

    public static synchronized ProductService getInstance() {
        if (productService == null) {
            productService = new ProductService();
        }
        return productService;
    }

    public static void main(String[] args) {
        ProductService ps = new ProductService();
        List<Product> hql = ps.getHQL(null, null, .0, .0);
        System.out.println("  !  ");
    }

    public void add(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
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

    public List<Product> getHQL(Integer category, String name, Double priceFrom, Double priceTo) {
        if (priceFrom >= 0 & priceTo >= 0) {
            if (name.length() == 0) name = null;
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product as p INNER JOIN p.category WHERE (p.category.id = :category OR :category IS NULL) AND (lower(p.name) LIKE :name OR :name IS NULL) AND (p.price >= :priceFrom OR :priceFrom = 0) AND (p.price <= :priceTo OR :priceTo = 0)", Product.class);

            return query
                    .setParameter("category", category)
                    .setParameter("name", name)
                    .setParameter("priceFrom", priceFrom)
                    .setParameter("priceTo", priceTo)
                    .getResultList();
        } else return null;
    }
}
