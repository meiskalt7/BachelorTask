package org.meiskalt7.crud;

import org.meiskalt7.entity.Category;

import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryService extends Service<Category> {

    private static CategoryService categoryService;

    private CategoryService() {
    }

    public static CategoryService getInstance() {
        if (categoryService == null) {
            categoryService = new CategoryService();
        }
        return categoryService;
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
