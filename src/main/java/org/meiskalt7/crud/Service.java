package org.meiskalt7.crud;

import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;

abstract class Service<T> implements GenericDao<T> {

    protected final EntityManager em;

    protected Service() {
        this.em = EntityManagerUtil.getEntityManager();
    }

    @Override
    public void add(T t) {
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    @Override
    public void update(T t) {
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        T t = get(id);
        em.getTransaction().begin();
        em.remove(t);
        em.getTransaction().commit();
    }
}
