package org.meiskalt7.crud;

import org.meiskalt7.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Eiskalt on 12.10.2015.
 */
public class CategoryService extends Service<Category> {

    public CategoryService() {
        super(Category.class);
    }

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    public List<Category> getAll() {
        TypedQuery<Category> namedQuery = em.createNamedQuery("Categories.getAll", Category.class);
        return namedQuery.getResultList();
    }
}
