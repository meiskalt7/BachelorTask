package org.meiskalt7.crud;

import org.meiskalt7.entity.Table;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TableService {

    private static TableService tableService;

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    private TableService() {

    }

    public static synchronized TableService getInstance() {
        if (tableService == null) {
            tableService = new TableService();
        }
        return tableService;
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
        return em.createQuery("SELECT i FROM Table i").getResultList();
    }

    public void deleteAll() {
        for (Table cat : getAll()) {
            delete(cat.getId());
        }
    }
}
