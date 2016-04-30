package org.meiskalt7.servlets;

import org.meiskalt7.crud.EmployeeService;
import org.meiskalt7.crud.IngridientService;
import org.meiskalt7.crud.WorkshiftService;
import org.meiskalt7.entity.Ingridient;

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

        IngridientService ingridientService = new IngridientService();
        WorkshiftService workshiftService = new WorkshiftService();
        EmployeeService employeeService = new EmployeeService();

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
        }

        req.setAttribute("ingridList", ingridientService.getAll());
        req.setAttribute("workshiftList", workshiftService.getAll());
        req.setAttribute("employeeList", employeeService.getAll());

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/managerPage.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
