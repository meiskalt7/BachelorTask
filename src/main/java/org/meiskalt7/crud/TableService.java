package org.meiskalt7.crud;

import org.meiskalt7.entity.Table;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class TableService {

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    public TableService() {

    }

    public Table add(Table category) {
        em.getTransaction().begin();
        Table categoryFromDB = em.merge(category);
        em.getTransaction().commit();
        return categoryFromDB;
    }

    public void delete(int id) {
        Table category = get(id);
        em.getTransaction().begin();
        em.remove(category);
        em.getTransaction().commit();
    }

    public void update(Table category) {
        em.getTransaction().begin();
        em.merge(category);
        em.getTransaction().commit();
    }

    public Table get(int id) {
        return em.find(Table.class, id);
    }

    public List<Table> getAll() {
        TypedQuery<Table> namedQuery = em.createNamedQuery("Categories.getAll", Table.class);
        return namedQuery.getResultList();
    }

    public void deleteAll() {
        for (Table cat : getAll()) {
            delete(cat.getId());
        }
    }

    public int getId(String category) {
        return (Integer) em.createQuery("SELECT id FROM Category WHERE name = :category").setParameter("category", category).getResultList().get(0);
    }
}
