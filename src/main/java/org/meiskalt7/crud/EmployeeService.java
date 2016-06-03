package org.meiskalt7.crud;

import org.meiskalt7.entity.Employee;
import org.meiskalt7.entity.UserType;
import org.meiskalt7.servlets.Entity;

import javax.servlet.http.HttpServletRequest;
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

    @Override
    public void create(HttpServletRequest request) {
        Service employeeService = Service.getService(Entity.EMPLOYEE);
        Service userTypeService = Service.getService(Entity.USERTYPE);

        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String patronymic = request.getParameter("patronymic");
        String wage = request.getParameter("wage");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
        UserType userType = (UserType) userTypeService.get(userTypeId);
        Employee employee = new Employee(surname, name, patronymic, parseDouble(wage, request), username, password, userType);
        employeeService.add(employee);
    }

    @Override
    public void update(HttpServletRequest request, int id) {
        EmployeeService employeeService = (EmployeeService) Service.getService(Entity.EMPLOYEE);
        UserTypeService userTypeService = (UserTypeService) Service.getService(Entity.USERTYPE);

        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String patronymic = request.getParameter("patronymic");
        String wage = request.getParameter("wage");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Employee employee = employeeService.get(id);


        if (surname != null && !surname.equals(employee.getSurname())) {
            employee.setSurname(surname);
        }

        if (name != null && !name.equals(employee.getName())) {
            employee.setName(name);
        }

        if (patronymic != null && !patronymic.equals(employee.getPatronymic())) {
            employee.setPatronymic(patronymic);
        }

        if (wage != null && !parseDouble(wage, request).equals(employee.getWage())) {
            employee.setWage(parseDouble(wage, request));
        }

        if (username != null && !username.equals(employee.getUsername())) {
            employee.setUsername(username);
        }

        if (password != null && !password.equals(employee.getPassword())) {
            employee.setPassword(password);
        }

        int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
        UserType userType = userTypeService.get(userTypeId);
        if (userType != null && !userType.equals(employee.getUserType())) {
            employee.setUserType(userType);
        }

        employeeService.update(employee);
    }
}
