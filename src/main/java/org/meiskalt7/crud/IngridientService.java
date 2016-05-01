package org.meiskalt7.crud;

import org.meiskalt7.entity.Ingridient;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

public class IngridientService {

    private static IngridientService ingridientService;
    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    private IngridientService() {
    }

    public static synchronized IngridientService getInstance() {
        if (ingridientService == null) {
            ingridientService = new IngridientService();
        }
        return ingridientService;
    }

    public Ingridient add(Ingridient ingridient) {
        em.getTransaction().begin();
        Ingridient ingridientFromDB = em.merge(ingridient);
        em.getTransaction().commit();
        return ingridientFromDB;
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
        return em.createQuery("SELECT i FROM Ingridient i").getResultList();
    }

    public void deleteAll() {
        for (Ingridient ing : getAll()) {
            delete(ing.getId());
        }
    }

    public int getId(String category) {
        return (Integer) em.createQuery("SELECT id FROM Category WHERE name = :category").setParameter("category", category).getResultList().get(0);
    }
}
