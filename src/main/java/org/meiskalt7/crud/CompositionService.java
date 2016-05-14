package org.meiskalt7.crud;

import org.meiskalt7.entity.Composition;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CompositionService implements GenericDao<Composition> {

    private static CompositionService compositionService;

    @PersistenceContext
    private final EntityManager em = EntityManagerUtil.getEntityManager();

    private CompositionService() {
    }

    public static CompositionService getInstance() {
        if (compositionService == null) {
            compositionService = new CompositionService();
        }
        return compositionService;
    }

    public void add(Composition composition) {
        em.getTransaction().begin();
        em.merge(composition);
        em.getTransaction().commit();
    }

    public void update(Composition composition) {
        em.getTransaction().begin();
        em.merge(composition);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Composition composition = get(id);
        em.getTransaction().begin();
        em.remove(composition);
        em.getTransaction().commit();
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
}
