package org.meiskalt7.crud;

import org.meiskalt7.entity.Workshift;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class WorkshiftService implements GenericDao<Workshift> {

    private static WorkshiftService workshiftService;
    @PersistenceContext
    public EntityManager em = EntityManagerUtil.getEntityManager();

    private WorkshiftService() {

    }

    public static synchronized WorkshiftService getInstance() {
        if (workshiftService == null) {
            workshiftService = new WorkshiftService();
        }
        return workshiftService;
    }

    public void add(Workshift workshift) {
        em.getTransaction().begin();
        em.merge(workshift);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Workshift workshift = get(id);
        em.getTransaction().begin();
        em.remove(workshift);
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
        return (List<Workshift>) em.createQuery("SELECT w FROM Workshift w").getResultList();
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