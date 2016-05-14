package org.meiskalt7.crud;

import org.meiskalt7.entity.Employee;
import org.meiskalt7.util.EntityManagerUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EmployeeService implements GenericDao<Employee> {

    private static EmployeeService employeeService;
    @PersistenceContext
    private final EntityManager em = EntityManagerUtil.getEntityManager();

    private EmployeeService() {
    }

    public static EmployeeService getInstance() {
        if (employeeService == null) {
            employeeService = new EmployeeService();
        }
        return employeeService;
    }

    public void add(Employee employee) {
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Employee employee = get(id);
        em.getTransaction().begin();
        em.remove(employee);
        em.getTransaction().commit();
    }

    public void update(Employee employee) {
        em.getTransaction().begin();
        em.merge(employee);
        em.getTransaction().commit();
    }

    public Employee get(int id) {
        return em.find(Employee.class, id);
    }

    public List<Employee> getAll() {
        return (List<Employee>) em.createQuery("SELECT e FROM Employee e").getResultList();
    }

    public void deleteAll() {
        for (Employee emp : getAll()) {
            delete(emp.getId());
        }
    }
}
