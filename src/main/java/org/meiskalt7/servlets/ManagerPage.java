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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "ManagerPage")
public class ManagerPage extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        IngridientService ingridientService = IngridientService.getInstance();
        WorkshiftService workshiftService = WorkshiftService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        final EmployeeService employeeService = EmployeeService.getInstance();
        TimeRangeService timeRangeService = TimeRangeService.getInstance();
        final TableService tableService = TableService.getInstance();

        if (request.getParameter("button") != null) {
            String button[] = request.getParameter("button").split(" ");

            Operation operation = Operation.valueOf(button[0]);
            Entity entity = Entity.valueOf(button[1]);

            switch (operation) {
                case ADD:
                    switch (entity) {
                        case INGRIDIENT:
                            createIngridient(request, ingridientService);
                            break;
                        case WORKSHIFT:
                            createWorkshift(request, workshiftService, employeeService, timeRangeService);
                            break;
                        case TABLES_EMPLOYEES:
                            assignTables(request, employeeService, tableService);
                            break;
                        case TABLE:
                            createTable(request, tableService);
                    }
                    break;
                case UPDATE: {
                    int id = Integer.parseInt(request.getParameter("id"));
                    switch (entity) {
                        case INGRIDIENT:
                            updateIngridient(request, ingridientService, id);
                            break;
                    }
                    break;
                }
                case DELETE:
                    int id = Integer.parseInt(request.getParameter("id"));
                    switch (entity) {
                        case TABLE:
                            tableService.delete(id);
                            break;
                        case INGRIDIENT:
                            ingridientService.delete(id);
                            break;
                        case EMPLOYEE:
                            deassignTables(request, employeeService, id);
                            break;
                        case WORKSHIFT:
                            workshiftService.delete(id);
                            break;
                    }
                    break;
            }
        }

        request.setAttribute("ingridList", ingridientService.getAll());
        request.setAttribute("workshiftList", workshiftService.getAll());
        request.setAttribute("employeeList", employeeService.getAll());
        request.setAttribute("timerangeList", timeRangeService.getAll());
        request.setAttribute("tableList", tableService.getAll());
        request.setAttribute("categoryList", categoryService.getAll());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        request.setAttribute("today", simpleDateFormat.format(new java.util.Date()));

        RequestDispatcher rd;
        if (request.getServletPath().equals("/hall")) {
            rd = getServletContext()
                    .getRequestDispatcher("/manager/hallPage.jsp");
        } else if (request.getServletPath().equals("/products")) {
            rd = getServletContext()
                    .getRequestDispatcher("/manager/productsPage.jsp");
        } else if (request.getServletPath().equals("/stock")) {
            rd = getServletContext()
                    .getRequestDispatcher("/manager/stockPage.jsp");
        } else {
            rd = getServletContext()
                    .getRequestDispatcher("/manager/workshiftPage.jsp");
        }
        rd.forward(request, response);
    }

    private void deassignTables(HttpServletRequest req, EmployeeService employeeService, int id) {
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
    }

    private void assignTables(HttpServletRequest req, EmployeeService employeeService, final TableService tableService) {
        Employee employee = employeeService.get(Integer.parseInt(req.getParameter("employee")));
        final String tablesId[] = req.getParameterValues("tables");
        List<Table> tables = new ArrayList<Table>() {{
            for (String aTablesId : tablesId) {
                add(tableService.get(Integer.parseInt(aTablesId)));
            }
        }};
        employee.getTables().addAll(tables);
        employeeService.update(employee);
    }

    private void createTable(HttpServletRequest req, TableService tableService) {
        int number = Integer.parseInt(req.getParameter("number"));
        tableService.add(new Table(number, req.getParameter("table")));
    }

    private void createWorkshift(HttpServletRequest req, WorkshiftService workshiftService, final EmployeeService employeeService, TimeRangeService timeRangeService) {
        final String[] employeeId = req.getParameterValues("employee");
        List<Employee> employees = new ArrayList<Employee>() {{
            for (String anEmployeeId : employeeId) {
                add(employeeService.get(Integer.parseInt(anEmployeeId)));
            }
        }};
        Date date = Date.valueOf(req.getParameter("date"));
        int timerangeId = Integer.parseInt(req.getParameter("timerange"));
        TimeRange timeRange = timeRangeService.get(timerangeId);
        Workshift workshift = new Workshift(date, timeRange, employees);
        workshiftService.add(workshift);
        for (Employee emp : employees) {
            emp.getWorkshifts().add(workshift);
            employeeService.update(emp);
        }
    }

    private void createIngridient(HttpServletRequest req, IngridientService ingridientService) {
        String name = req.getParameter("name");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        double price = Double.parseDouble(req.getParameter("price"));
        ingridientService.add(new Ingridient(name, quantity, price));
    }

    private void updateIngridient(HttpServletRequest req, IngridientService ingridientService, int id) {

        Ingridient ingridient = ingridientService.get(id);

        String name = req.getParameter("name");


        if (name != null && !name.equals(ingridient.getName())) {
            ingridient.setName(name);
        }

        if (req.getParameter("quantity") != null) {
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            if (quantity != ingridient.getQuantity()) {
                ingridient.setQuantity(quantity);
            }
        }

        if (req.getParameter("price") != null) {
            double price = Double.parseDouble(req.getParameter("price"));
            if (price != ingridient.getPrice()) {
                ingridient.setPrice(price);
            }
        }

        ingridientService.update(ingridient);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
