package org.meiskalt7.servlets;

import org.meiskalt7.crud.OrderService;
import org.meiskalt7.entity.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StatisticPage")
public class StatisticPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        OrderService orderService = OrderService.getInstance();

        List<Order> endedOrders = new ArrayList<>();
        for (Order order : orderService.getAll()) {
            if (order.isEnded()) {
                endedOrders.add(order);
            }
        }

        req.setAttribute("orderList", endedOrders);

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/statisticPage.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
