package org.meiskalt7.crud;

import org.meiskalt7.entity.Workshift;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

public class WorkshiftService {

    public WorkshiftService() {

    }

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();


    public Workshift add(Workshift category) {
        em.getTransaction().begin();
        Workshift categoryFromDB = em.merge(category);
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
