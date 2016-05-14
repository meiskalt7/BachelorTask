package org.meiskalt7.crud;

import org.meiskalt7.entity.Category;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryService implements GenericDao<Category> {

    private static CategoryService categoryService;
    @PersistenceContext
    private final EntityManager em = EntityManagerUtil.getEntityManager();

    private CategoryService() {

    }

    public static CategoryService getInstance() {
        if (categoryService == null) {
            categoryService = new CategoryService();
        }
        return categoryService;
    }

    public void add(Category category) {
        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Category category = get(id);
        em.getTransaction().begin();
        em.remove(category);
        em.getTransaction().commit();
    }

    public void update(Category category) {
        em.getTransaction().begin();
        em.merge(category);
        em.getTransaction().commit();
    }

    public Category get(int id) {
        return em.find(Category.class, id);
    }

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
}
