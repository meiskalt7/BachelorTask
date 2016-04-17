package org.meiskalt7.crud;

import org.meiskalt7.entity.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeService {

    public EmployeeService() {
    }

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    public Employee add(Employee employee) {
        em.getTransaction().begin();
        Employee employeeFromDB = em.merge(employee);
        em.getTransaction().commit();
        return employeeFromDB;
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
        TypedQuery<Employee> namedQuery = em.createNamedQuery("Employees.getAll", Employee.class);
        return namedQuery.getResultList();
    }

    public void deleteAll() {
        for (Employee emp : getAll()) {
            delete(emp.getId());
        }
    }

    public int getId(String name) {
        return (Integer) em.createQuery("SELECT id FROM Category WHERE name = :name").setParameter("name", name).getResultList().get(0);
    }
}
