package org.meiskalt7.crud;

import org.meiskalt7.entity.Product;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Eiskalt on 12.10.2015.
 */
public class ProductService extends Service<Product>{

    public ProductService() {
        super(Product.class);
    }

    public List<Product> getAll() {
        TypedQuery<Product> namedQuery = em.createNamedQuery("Products.getAll", Product.class);
        return namedQuery.getResultList();
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
