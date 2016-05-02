package org.meiskalt7.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    static {
        try {
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("test");
        } catch (Exception e) {
            //логировать все исключения
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }
}
