package org.meiskalt7.crud;

import org.meiskalt7.entity.Composition;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CompositionService extends Service<Composition> {

    private static CompositionService compositionService;

    private CompositionService() {
    }

    public static CompositionService getInstance() {
        if (compositionService == null) {
            compositionService = new CompositionService();
        }
        return compositionService;
    }

    public Composition get(int id) {
        return em.find(Composition.class, id);
    }

    public List<Composition> getAll() {
        return (List<Composition>) em.createQuery("SELECT e FROM Composition e").getResultList();
    }

    public void deleteAll() {
        for (Composition emp : getAll()) {
            delete(emp.getId());
        }
    }

    @Override
    public void create(HttpServletRequest request) {

    }

    @Override
    public void update(HttpServletRequest request, int id) {

    }
}
