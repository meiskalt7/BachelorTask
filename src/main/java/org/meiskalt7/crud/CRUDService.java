package org.meiskalt7.crud;

import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class CRUDService<T> implements GenericDao<T> {

    private static CRUDService crudService;

    public EntityManager em = EntityManagerUtil.getEntityManager();

    public CRUDService() {
    }

    public static CRUDService getInstance() {
        if (crudService == null) {
            crudService = new CRUDService();
        }
        return crudService;
    }

    public void add(T t) {

    }

    public void update(T t) {

    }

    public void delete(int id) {

    }

    public T get(int id) {
        return null;
    }

    public List<T> getAll() {
        return null;
    }

    public void deleteAll() {

    }
}
