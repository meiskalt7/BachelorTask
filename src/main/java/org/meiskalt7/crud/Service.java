package org.meiskalt7.crud;

import org.meiskalt7.servlets.Entity;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;

abstract public class Service<T> implements GenericDao<T> {

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

    public static Service getService(Entity entity) {
        switch (entity) {
            case CATEGORY:
                return CategoryService.getInstance();
            case EMPLOYEE:
                return EmployeeService.getInstance();
            case INGRIDIENT:
                return IngridientService.getInstance();
            case ORDER:
                return OrderService.getInstance();
            case PRODUCT:
                return ProductService.getInstance();
            case RESERVATION:
                return ReservationService.getInstance();
            case TABLE:
                return TableService.getInstance();
            case TIMERANGE:
                return TimeRangeService.getInstance();
            case USERTYPE:
                return UserTypeService.getInstance();
            case WORKSHIFT:
                return WorkshiftService.getInstance();
            default:
                return null;
        }
    }
}
