package org.meiskalt7.crud;

import org.meiskalt7.entity.Category;
import org.meiskalt7.entity.Table;
import org.meiskalt7.servlets.Entity;

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
        Service categoryService = Service.getService(Entity.CATEGORY);

        String categoryName = request.getParameter("categoryName");
        categoryService.add(new Category(categoryName));
    }

    @Override
    public void update(HttpServletRequest request, int id) {
        Service tableService = Service.getService(Entity.TABLE);

        Table table = (Table) tableService.get(id);

        int number = Integer.parseInt(request.getParameter("number"));
        String type = request.getParameter("table");

        table.setNumber(number);
        table.setType(type);

        tableService.update(table);
    }

    public int getId(String category) {
        return (Integer) em.createQuery("SELECT id FROM Category WHERE name = :category").setParameter("category", category).getResultList().get(0);
    }
}
