package org.meiskalt7.crud;

import org.meiskalt7.entity.Ingridient;

import java.util.List;

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
}
