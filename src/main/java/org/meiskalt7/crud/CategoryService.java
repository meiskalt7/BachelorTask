package org.meiskalt7.crud;

import org.meiskalt7.entity.Category;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
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

    @Override
    public void create(HttpServletRequest request) {
        String categoryName = request.getParameter("categoryName");
        add(new Category(categoryName));
    }

    @Override
    public void update(HttpServletRequest request, int id) {
        Category category = get(id);
        String categoryName = request.getParameter("categoryName");
        if (categoryName != null && !categoryName.equals(category.getName())) {
            category.setName(categoryName);
            update(category);
        }
    }

    public int getId(String category) {
        return (Integer) em.createQuery("SELECT id FROM Category WHERE name = :category").setParameter("category", category).getResultList().get(0);
    }
}
