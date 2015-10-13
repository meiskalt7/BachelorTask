package org.meiskalt7.crud;

import org.meiskalt7.entity.Category;
import org.meiskalt7.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * @author Eiskalt on 12.10.2015.
 */
public class CategoryService extends Service<Category>{

    public CategoryService() {
        super(Category.class);
    }

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    public List<Category> getAll() {
        TypedQuery<Category> namedQuery = em.createNamedQuery("Categories.getAll", Category.class);
        return namedQuery.getResultList();
    }

    public void deleteAll() {
        for (Category cat : getAll()) {
            delete(cat.getId());
        }
    }

    public int getId(String category) {
        return (Integer) em.createQuery("SELECT id FROM Category WHERE name = :category").setParameter("category", category).getResultList().get(0);
    }

    public List<Category> getSome(List<Product> products) {
        List<Category> categories = getAll();
        List<Category> category1 = new ArrayList<Category>();
        for (Product p : products) category1.add(categories.get(p.getCat_id()));
        return category1;
    }
}
