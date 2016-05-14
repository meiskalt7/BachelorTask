package org.meiskalt7.crud;

import org.meiskalt7.entity.Table;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class TableService implements GenericDao<Table> {

    private static TableService tableService;
    private final EntityManager em = EntityManagerUtil.getEntityManager();

    private TableService() {

    }

    public static synchronized TableService getInstance() {
        if (tableService == null) {
            tableService = new TableService();
        }
        return tableService;
    }

    public void add(Table table) {
        em.getTransaction().begin();
        em.persist(table);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Table table = get(id);
        em.getTransaction().begin();
        em.remove(table);
        em.getTransaction().commit();
    }

    public void update(Table table) {
        em.getTransaction().begin();
        em.merge(table);
        em.getTransaction().commit();
    }

    public Table get(int id) {
        return em.find(Table.class, id);
    }

    public List<Table> getAll() {
        return (List<Table>) em.createQuery("SELECT i FROM Table i").getResultList();
    }

    public void deleteAll() {
        for (Table cat : getAll()) {
            delete(cat.getId());
        }
    }
}
