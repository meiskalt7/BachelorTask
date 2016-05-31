package org.meiskalt7.crud;

import org.meiskalt7.entity.Employee;

import java.util.List;

public class EmployeeService extends Service<Employee> {

    private static EmployeeService employeeService;

    private EmployeeService() {
    }

    public static EmployeeService getInstance() {
        if (employeeService == null) {
            employeeService = new EmployeeService();
        }
        return employeeService;
    }

    public void refresh(Employee employee) {
        em.getTransaction().begin();
        em.refresh(employee);
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
