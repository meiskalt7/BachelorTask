package org.meiskalt7.servlets;

import org.meiskalt7.crud.*;
import org.meiskalt7.entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "ManagerPage")
public class ManagerPage extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IngridientService ingridientService = IngridientService.getInstance();
        WorkshiftService workshiftService = WorkshiftService.getInstance();
        final EmployeeService employeeService = EmployeeService.getInstance();
        TimeRangeService timeRangeService = TimeRangeService.getInstance();
        final TableService tableService = TableService.getInstance();

        if (req.getParameter("button") != null) {
            String button[] = req.getParameter("button").split(" ");

            Operation operation = Operation.valueOf(button[0]);

            switch (operation) {
                case add:

                    if ("ingridient".equals(button[1])) {
                        String name = req.getParameter("name");
                        int quantity = Integer.parseInt(req.getParameter("quantity"));
                        ingridientService.add(new Ingridient(name, quantity));
                    } else if ("workshift".equals(button[1])) {
                        final String[] employeeId = req.getParameterValues("employee");
                        List<Employee> employees = new ArrayList<Employee>() {{
                            for (int i = 0; i < employeeId.length; i++) {
                                add(employeeService.get(Integer.parseInt(employeeId[i])));
                            }
                        }};
                        Date date = Date.valueOf(req.getParameter("date"));
                        Integer timerangeId = Integer.parseInt(req.getParameter("timerange"));
                        TimeRange timeRange = timeRangeService.get(timerangeId);
                        Workshift workshift = new Workshift(date, timeRange, employees);
                        workshiftService.add(workshift);
                        for (Employee emp : employees) {
                            emp.getWorkshifts().add(workshift);
                            employeeService.update(emp);
                        }
                    } else if ("tables_employees".equals(button[1])) {
                        Employee employee = employeeService.get(Integer.parseInt(req.getParameter("employee")));
                        final String tablesId[] = req.getParameterValues("tables");
                        List<Table> tables = new ArrayList<Table>() {{
                            for (int i = 0; i < tablesId.length; i++) {
                                add(tableService.get(Integer.parseInt(tablesId[i])));
                            }
                        }};
                        employee.getTables().addAll(tables);
                        employeeService.update(employee);
                    } else if ("table".equals(button[1])) {
                        int number = Integer.parseInt(req.getParameter("number"));
                        tableService.add(new Table(number, req.getParameter("table")));
                    }

                    break;
                case update:
                    break;
                case delete:

                    int id = Integer.parseInt(req.getParameter("id"));

                    if ("table".equals(button[1])) {
                        tableService.delete(id);
                    } else if ("ingridient".equals(button[1])) {
                        ingridientService.delete(id);
                    } else if ("employee".equals(button[1])) {
                        if (req.getParameter("tableId") != null) {
                            String[] tableId = req.getParameterValues("tableId");
                            Employee employee = employeeService.get(id);
                            for (String tid : tableId) {
                                Iterator<Table> i = employee.getTables().iterator();
                                while (i.hasNext()) {
                                    Table table = i.next();
                                    if (table.getId() == Integer.parseInt(tid)) {
                                        i.remove();
                                    }
                                }
                            }
                            employeeService.update(employee);
                        }
                    } else if ("workshift".equals(button[1])) {
                        workshiftService.delete(id);
                    }
                    break;
            }
        }

        req.setAttribute("ingridList", ingridientService.getAll());
        req.setAttribute("workshiftList", workshiftService.getAll());
        req.setAttribute("employeeList", employeeService.getAll());
        req.setAttribute("timerangeList", timeRangeService.getAll());
        req.setAttribute("tableList", tableService.getAll());

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/managerPage.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
