package org.meiskalt7.crud;

import org.meiskalt7.entity.User;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserService implements GenericDao<User> {

    private static UserService userService;
    @PersistenceContext
    public EntityManager em = EntityManagerUtil.getEntityManager();

    private UserService() {

    }

    public static synchronized UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    @Override
    public void add(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void update(User user) {
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        User user = get(id);
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) em.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    public void deleteAll() {
        for (User user : getAll()) {
            delete(user.getId());
        }
    }
}
