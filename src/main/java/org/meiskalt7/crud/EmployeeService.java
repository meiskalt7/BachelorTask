package org.meiskalt7.crud;

import org.meiskalt7.entity.Employee;
import org.meiskalt7.entity.Table;
import org.meiskalt7.entity.UserType;
import org.meiskalt7.servlets.Entity;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
        add(employee);
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


        if (!Objects.equals(surname, employee.getSurname())) {
            employee.setSurname(surname);
        }

        if (Objects.equals(name, employee.getName())) {
            employee.setName(name);
        }

        if (Objects.equals(patronymic, employee.getPatronymic())) {
            employee.setPatronymic(patronymic);
        }

        if (Objects.equals(wage, employee.getWage())) {
            employee.setWage(parseDouble(wage, request));
        }

        if (Objects.equals(username, employee.getUsername())) {
            employee.setUsername(username);
        }

        if (Objects.equals(password, employee.getPassword())) {
            employee.setPassword(password);
        }

        int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
        UserType userType = userTypeService.get(userTypeId);
        if (userType != null && !userType.equals(employee.getUserType())) {
            employee.setUserType(userType);
        }

        employeeService.update(employee);
    }

    public void assignTables(HttpServletRequest request) {
        final Service tableService = Service.getService(Entity.TABLE);

        Employee employee = get(Integer.parseInt(request.getParameter("employee")));
        final String tablesId[] = request.getParameterValues("tables");
        List<Table> tables = new ArrayList<Table>() {{
            for (String aTablesId : tablesId) {
                add((Table) tableService.get(Integer.parseInt(aTablesId)));
            }
        }};
        employee.getTables().addAll(tables);
        update(employee);
    }

    public void deassignTables(HttpServletRequest request, int id) {

        if (request.getParameter("tableId") != null) {
            String[] tableId = request.getParameterValues("tableId");
            Employee employee = get(id);
            for (String tid : tableId) {
                Iterator<Table> i = employee.getTables().iterator();
                while (i.hasNext()) {
                    Table table = i.next();
                    if (table.getId() == Integer.parseInt(tid)) {
                        i.remove();
                    }
                }
            }
            update(employee);
        }
    }
}
