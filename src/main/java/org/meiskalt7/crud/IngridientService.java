package org.meiskalt7.crud;

import org.meiskalt7.entity.Ingridient;
import org.meiskalt7.servlets.Entity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

public class IngridientService extends Service<Ingridient> {

    private static IngridientService ingridientService;

    private IngridientService() {
    }

    public static synchronized IngridientService getInstance() {
        if (ingridientService == null) {
            ingridientService = new IngridientService();
        }
        return ingridientService;
    }

    public Ingridient get(int id) {
        return em.find(Ingridient.class, id);
    }

    public List<Ingridient> getAll() {
        return (List<Ingridient>) em.createQuery("SELECT i FROM Ingridient i").getResultList();
    }

    public void deleteAll() {
        for (Ingridient ing : getAll()) {
            delete(ing.getId());
        }
    }

    @Override
    public void create(HttpServletRequest request) {
        Service ingridientService = Service.getService(Entity.INGRIDIENT);

        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));
        ingridientService.add(new Ingridient(name, quantity, price));
    }

    @Override
    public void update(HttpServletRequest request, int id) {
        Service ingridientService = Service.getService(Entity.INGRIDIENT);

        Ingridient ingridient = (Ingridient) ingridientService.get(id);

        String name = request.getParameter("name");

        if (!Objects.equals(name, ingridient.getName())) {
            ingridient.setName(name);
        }

        if (request.getParameter("quantity") != null) {
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity != ingridient.getQuantity()) {
                ingridient.setQuantity(quantity);
            }
        }

        if (request.getParameter("price") != null) {
            double price = Double.parseDouble(request.getParameter("price"));
            if (price != ingridient.getPrice()) {
                ingridient.setPrice(price);
            }
        }

        ingridientService.update(ingridient);
    }
}
