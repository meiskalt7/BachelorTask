package org.meiskalt7.crud;

import org.meiskalt7.entity.TimeRange;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TimeRangeService {

    public TimeRangeService() {

    }

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    public TimeRange add(TimeRange employee) {
        em.getTransaction().begin();
        TimeRange employeeFromDB = em.merge(employee);
        em.getTransaction().commit();
        return employeeFromDB;
    }

    public void delete(int id) {
        TimeRange employee = get(id);
        em.getTransaction().begin();
        em.remove(employee);
        em.getTransaction().commit();
    }

    public void update(TimeRange employee) {
        em.getTransaction().begin();
        em.merge(employee);
        em.getTransaction().commit();
    }

    public TimeRange get(int id) {
        return em.find(TimeRange.class, id);
    }

    public List<TimeRange> getAll() {
        //TypedQuery<Employee> namedQuery = em.createNamedQuery("Employees.getAll", Employee.class);
        return em.createQuery("SELECT t FROM TimeRange t").getResultList();
    }

    public void deleteAll() {
        for (TimeRange emp : getAll()) {
            delete(emp.getId());
        }
    }
}
