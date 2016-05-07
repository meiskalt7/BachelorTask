package org.meiskalt7.crud;

import org.meiskalt7.entity.TimeRange;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TimeRangeService implements GenericDao<TimeRange> {

    private static TimeRangeService timeRangeService;
    @PersistenceContext
    private EntityManager em = EntityManagerUtil.getEntityManager();

    private TimeRangeService() {

    }

    public static synchronized TimeRangeService getInstance() {
        if (timeRangeService == null) {
            timeRangeService = new TimeRangeService();
        }
        return timeRangeService;
    }

    public void add(TimeRange timeRange) {
        em.getTransaction().begin();
        em.persist(timeRange);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        TimeRange employee = get(id);
        em.getTransaction().begin();
        em.remove(employee);
        em.getTransaction().commit();
    }

    public void update(TimeRange timeRange) {
        em.getTransaction().begin();
        em.merge(timeRange);
        em.getTransaction().commit();
    }

    public TimeRange get(int id) {
        return em.find(TimeRange.class, id);
    }

    public List<TimeRange> getAll() {
        //TypedQuery<Employee> namedQuery = em.createNamedQuery("Employees.getAll", Employee.class);
        return (List<TimeRange>) em.createQuery("SELECT t FROM TimeRange t").getResultList();
    }

    public void deleteAll() {
        for (TimeRange emp : getAll()) {
            delete(emp.getId());
        }
    }
}
