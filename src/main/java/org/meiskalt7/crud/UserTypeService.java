package org.meiskalt7.crud;

import org.meiskalt7.entity.UserType;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserTypeService implements GenericDao<UserType> {

    private static UserTypeService userTypeService;

    @PersistenceContext
    private EntityManager em = EntityManagerUtil.getEntityManager();

    private UserTypeService() {
    }

    public static synchronized UserTypeService getInstance() {
        if (userTypeService == null) {
            userTypeService = new UserTypeService();
        }
        return userTypeService;
    }

    @Override
    public void add(UserType userType) {
        em.getTransaction().begin();
        em.persist(userType);
        em.getTransaction().commit();
    }

    @Override
    public void update(UserType userType) {
        em.getTransaction().begin();
        em.merge(userType);
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        UserType userType = get(id);
        em.getTransaction().begin();
        em.remove(userType);
        em.getTransaction().commit();
    }

    @Override
    public UserType get(int id) {
        return em.find(UserType.class, id);
    }

    @Override
    public List<UserType> getAll() {
        return (List<UserType>) em.createQuery("SELECT u FROM UserType u").getResultList();
    }

    @Override
    public void deleteAll() {
        for (UserType userType : getAll()) {
            delete(userType.getId());
        }
    }
}
