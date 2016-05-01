package org.meiskalt7.servlets;

import org.meiskalt7.crud.*;
import org.meiskalt7.entity.Ingridient;
import org.meiskalt7.entity.Table;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ManagerPage")
public class ManagerPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IngridientService ingridientService = IngridientService.getIngridientService();
        WorkshiftService workshiftService = new WorkshiftService();
        EmployeeService employeeService = EmployeeService.getInstance();
        TimeRangeService timeRangeService = TimeRangeService.getInstance();
        TableService tableService = TableService.getInstance();

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
