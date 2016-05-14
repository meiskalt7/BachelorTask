package org.meiskalt7.servlets;

import org.meiskalt7.crud.EmployeeService;
import org.meiskalt7.crud.OrderService;
import org.meiskalt7.entity.Employee;
import org.meiskalt7.entity.Order;

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

        OrderService orderService = OrderService.getInstance();

        if (req.getSession().getAttribute("userId") != null) {

            if (req.getParameter("button") != null) {

                String button[] = req.getParameter("button").split(" ");

                Operation operation = Operation.valueOf(button[0]);

                Entity entity = Entity.valueOf(button[1]);

                switch (operation) {
                    case ADD:
                        break;
                    case UPDATE:
                        if (Entity.ORDER.equals(entity)) {
                            int id = Integer.parseInt(req.getParameter("id"));
                            Order order = orderService.get(id);
                            order.setEnded(true);
                            orderService.update(order);
                        }
                        break;
                    case DELETE:
                        if (Entity.ORDER.equals(entity)) {
                            int id = Integer.parseInt(req.getParameter("id"));
                            orderService.delete(id);
                        }
                        break;
                }
            }

            int userId = Integer.parseInt(req.getSession().getAttribute("userId").toString());
            EmployeeService employeeService = EmployeeService.getInstance();
            Employee employee = employeeService.get(userId);
            req.setAttribute("orderList", employee.getOrderList());
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
