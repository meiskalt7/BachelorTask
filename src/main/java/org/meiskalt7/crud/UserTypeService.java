package org.meiskalt7.crud;

import org.meiskalt7.entity.UserType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserTypeService extends Service<UserType> {

    private static UserTypeService userTypeService;

    private UserTypeService() {
    }

    public static synchronized UserTypeService getInstance() {
        if (userTypeService == null) {
            userTypeService = new UserTypeService();
        }
        return userTypeService;
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

    @Override
    public void create(HttpServletRequest request) {

    }

    @Override
    public void update(HttpServletRequest request, int id) {

    }
}
