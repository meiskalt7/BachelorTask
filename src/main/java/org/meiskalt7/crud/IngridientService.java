package org.meiskalt7.crud;

import org.meiskalt7.entity.Ingridient;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class IngridientService implements GenericDao<Ingridient> {

    private static IngridientService ingridientService;
    @PersistenceContext
    private final EntityManager em = EntityManagerUtil.getEntityManager();

    private IngridientService() {
    }

    public static synchronized IngridientService getInstance() {
        if (ingridientService == null) {
            ingridientService = new IngridientService();
        }
        return ingridientService;
    }

    public void add(Ingridient ingridient) {
        em.getTransaction().begin();
        em.persist(ingridient);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Ingridient ingridient = get(id);
        em.getTransaction().begin();
        em.remove(ingridient);
        em.getTransaction().commit();
    }

    public void update(Ingridient ingridient) {
        em.getTransaction().begin();
        em.merge(ingridient);
        em.getTransaction().commit();
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
