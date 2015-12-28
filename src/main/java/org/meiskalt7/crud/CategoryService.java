package org.meiskalt7.crud;

import org.meiskalt7.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryService {

    public CategoryService() {

    }

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();


    public Category add(Category category) {
        em.getTransaction().begin();
        Category categoryFromDB = em.merge(category);
        em.getTransaction().commit();
        return categoryFromDB;
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
