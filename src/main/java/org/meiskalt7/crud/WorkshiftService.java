package org.meiskalt7.crud;

import org.meiskalt7.entity.Workshift;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

public class WorkshiftService {

    private static WorkshiftService workshiftService;
    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    private WorkshiftService() {

    }

    public static synchronized WorkshiftService getInstance() {
        if (workshiftService == null) {
            workshiftService = new WorkshiftService();
        }
        return workshiftService;
    }

    public Workshift add(Workshift workshift) {
        em.getTransaction().begin();
        Workshift categoryFromDB = em.merge(workshift);
        em.getTransaction().commit();
        return categoryFromDB;
    }

    public void delete(int id) {
        Workshift category = get(id);
        em.getTransaction().begin();
        em.remove(category);
        em.getTransaction().commit();
    }

    public void update(Workshift workshift) {
        em.getTransaction().begin();
        em.merge(workshift);
        em.getTransaction().commit();
    }

    public Workshift get(int id) {
        return em.find(Workshift.class, id);
    }

    public List<Workshift> getAll() {
        //TypedQuery<Workshift> namedQuery = em.createNamedQuery("Categories.getAll", Workshift.class);
        return em.createQuery("SELECT w FROM Workshift w").getResultList();
    }

    public void deleteAll() {
        for (Workshift cat : getAll()) {
            delete(cat.getId());
        }
    }

    public int getId(String category) {
        return (Integer) em.createQuery("SELECT id FROM Category WHERE name = :category").setParameter("category", category).getResultList().get(0);
    }
}
