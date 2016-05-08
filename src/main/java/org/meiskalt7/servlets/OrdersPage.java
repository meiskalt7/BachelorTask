package org.meiskalt7.servlets;

import org.meiskalt7.crud.EmployeeService;
import org.meiskalt7.crud.OrderService;
import org.meiskalt7.entity.Employee;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrdersPage")
public class OrdersPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EmployeeService employeeService = EmployeeService.getInstance();
        OrderService orderService = OrderService.getInstance();

        if (req.getSession().getAttribute("userId") != null) {

            if (req.getParameter("button") != null) {

                String button[] = req.getParameter("button").split(" ");

                Operation operation = Operation.valueOf(button[0]);

                switch (operation) {
                    case add:
                        break;
                    case update:
                        break;
                    case delete:
                        if ("order".equals(button[1])) {
                            int id = Integer.parseInt(req.getParameter("id"));
                            orderService.delete(id);
                        }
                        break;
                }


                int userId = Integer.parseInt(req.getSession().getAttribute("userId").toString());
                Employee employee = employeeService.get(userId);
                req.setAttribute("orderList", employee.getOrderList());
            }
        }

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/ordersPage.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
