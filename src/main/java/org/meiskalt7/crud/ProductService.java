package org.meiskalt7.crud;

import org.meiskalt7.entity.Category;
import org.meiskalt7.entity.Composition;
import org.meiskalt7.entity.Ingridient;
import org.meiskalt7.entity.Product;
import org.meiskalt7.servlets.Entity;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ProductService extends Service<Product> {

    private static ProductService productService;

    private ProductService() {
    }

    public static synchronized ProductService getInstance() {
        if (productService == null) {
            productService = new ProductService();
        }
        return productService;
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

    @Override
    public void create(HttpServletRequest request) {
        Service productService = Service.getService(Entity.PRODUCT);
        Service categoryService = Service.getService(Entity.CATEGORY);
        final Service ingridientService = Service.getService(Entity.INGRIDIENT);
        CompositionService compositionService = CompositionService.getInstance();

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        Category category = (Category) categoryService.get(categoryId);
        String name = request.getParameter("name");
        String price = request.getParameter("price");

        Product product = new Product(category, name, parseDouble(price, request));
        productService.add(product);
        final String ingridientsId[] = request.getParameterValues("ingridientsId");
        List<Ingridient> ingridients = new ArrayList<Ingridient>() {{
            for (String anIngridientsId : ingridientsId) {
                add((Ingridient) ingridientService.get(Integer.parseInt(anIngridientsId)));
            }
        }};
        String required[] = request.getParameterValues("quantity");
        int i = 0;
        for (Ingridient ingridient : ingridients) {
            Composition composition = new Composition();
            composition.setProduct(product);
            composition.setIngridient(ingridient);
            composition.setRequired(Integer.parseInt(required[i]));
            compositionService.add(composition);
            product.getIngridients().add(composition);
            i++;
        }
    }

    @Override
    public void update(HttpServletRequest request, int id) {
    }

    public List<Product> getHQL(Integer category, String name, String priceFrom, String priceTo, HttpServletRequest request) {

        if (parseDouble(priceFrom, request) >= 0 && parseDouble(priceTo, request) >= 0) {
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
