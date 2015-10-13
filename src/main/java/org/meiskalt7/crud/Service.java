package org.meiskalt7.crud;


/**
 * @author Eiskalt on 12.10.2015.
 */
/*class Service<T> {
    final Class<T> typeParameterClass;

    public Service(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    @PersistenceContext
    EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    public T add(T serviceType) {
        em.getTransaction().begin();
        T categoriesFromDB = em.merge(serviceType);
        em.getTransaction().commit();
        return categoriesFromDB;
    }

    public void delete(int id) {
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public void update(T serviceType) {
        em.getTransaction().begin();
        em.merge(serviceType);
        em.getTransaction().commit();
    }

    public T get(int id) {
        return em.find(typeParameterClass, id);
    }
}*/
