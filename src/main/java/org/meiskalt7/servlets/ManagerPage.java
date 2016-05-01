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

        if (req.getParameter("quantity") != null) {
            String name = req.getParameter("name");
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            ingridientService.add(new Ingridient(name, quantity));
        } else if (req.getParameter("time") != null) {

        } else if (req.getParameter("ingridId") != null) {
            int ingridId = Integer.parseInt(req.getParameter("ingridId"));
            ingridientService.delete(ingridId);
        } else if (req.getParameter("workshiftId") != null) {
            int workshiftId = Integer.parseInt(req.getParameter("workshiftId"));
            workshiftService.delete(workshiftId);
        } else if (req.getParameter("table") != null) {
            tableService.add(new Table(req.getParameter("table")));
        } else if (req.getParameter("tables") != null && req.getParameter("employee") != null) {
            Employee employee = employeeService.get(Integer.parseInt(req.getParameter("employee")));
            final String tablesId[] = req.getParameterValues("tables");
            List<Table> tables = new ArrayList<Table>() {{
                for (int i = 0; i < tablesId.length; i++) {
                    add(tableService.get(Integer.parseInt(tablesId[i])));
                }
            }};
            employee.setTables(tables);
            employeeService.update(employee);
        } else if (req.getParameter("timerange") != null) {
            final String[] employeeId = req.getParameterValues("employee");
            List<Employee> employees = new ArrayList<Employee>() {{
                for (int i = 0; i < employeeId.length; i++) {
                    add(employeeService.get(Integer.parseInt(employeeId[i])));
                }
            }};
            Date date = Date.valueOf(req.getParameter("date"));
            Integer timerangeId = Integer.parseInt(req.getParameter("timerange"));
            TimeRange timeRange = timeRangeService.get(timerangeId);
            workshiftService.add(new Workshift(date, timeRange, employees));
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
