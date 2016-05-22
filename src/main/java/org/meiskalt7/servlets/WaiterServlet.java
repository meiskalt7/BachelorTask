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
import java.sql.Timestamp;

@WebServlet(name = "WaiterServlet")
public class WaiterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService productService = ProductService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        OrderService orderService = OrderService.getInstance();
        OrderlistService orderlistService = OrderlistService.getInstance();
        EmployeeService employeeService = EmployeeService.getInstance();
        TableService tableService = TableService.getInstance();
        ReservationService reservationService = ReservationService.getInstance();

        if (request.getParameterMap().size() != 0) {

            String button[] = (request.getParameter("button") != null) ? request.getParameter("button").split(" ") : null;

            if (request.getParameter("userId").length() > 0) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                request.setAttribute("waiterTableList", employeeService.get(userId).getTables());
                request.setAttribute("orderList", employeeService.get(userId).getOrderList());

                if (button != null) {

                    Operation operation = Operation.valueOf(button[0]);
                    Entity entity = Entity.valueOf(button[1]);

                    switch (operation) {
                        case ADD:
                            switch (entity) {
                                case CART:
                                    createOrUpdateOrder(request, productService, orderService, orderlistService, employeeService, tableService, userId);
                                    //order.getOrderlists().add(orderlist);
                                    break;
                                case RESERVATION:
                                    String name = request.getParameter("name");
                                    String phone = request.getParameter("phone");
                                    Timestamp time = Timestamp.valueOf(request.getParameter("time").replace("T", " ") + ":00");
                                    int tableId = Integer.parseInt(request.getParameter("tableId"));

                                    Table table = tableService.get(tableId);

                                    Reservation reservation = new Reservation(name, phone, time, table);
                                    reservationService.add(reservation);

                                    request.setAttribute("resultMessage", "Столик забронирован");
                                    break;
                            }
                            break;
                        case UPDATE:
                            if (Entity.ORDER.equals(entity)) {
                                int id = Integer.parseInt(request.getParameter("id"));
                                Order order = orderService.get(id);
                                order.setEnded(true);
                                orderService.update(order);
                            }
                            break;
                        case DELETE:
                            int id = Integer.parseInt(request.getParameter("id"));
                            switch (entity) {
                                case RESERVATION:
                                    reservationService.delete(id);
                                    break;
                                case ORDER:
                                    orderService.delete(id);
                                    break;
                            }
                            break;
                    }
                }
            }

            if (request.getSession().getAttribute("userId") != null) {
                int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                Employee employee = employeeService.get(userId);
                request.setAttribute("orderList", employee.getOrderList());
            }

            if (request.getParameter("categoryId") != null) {
                Integer categoryId = (!"all".equals(request.getParameter("categoryId"))) ? Integer.parseInt(request.getParameter("categoryId")) : null;
                String name = request.getParameter("name").toLowerCase();
                String priceFrom = request.getParameter("priceFrom");
                String priceTo = request.getParameter("priceTo");

                request.setAttribute("productsList", productService.getHQL(categoryId, name, parseDouble(priceFrom, request), parseDouble(priceTo, request)));
            }
        }

        RequestDispatcher rd;
        if (request.getServletPath().equals("/reservations")) {
            request.setAttribute("reservationList", reservationService.getAll());
            request.setAttribute("tableList", tableService.getAll());
            rd = getServletContext()
                    .getRequestDispatcher("/waiter/reservationsPage.jsp");
        } else if (request.getServletPath().equals("/orders")) {
            rd = getServletContext()
                    .getRequestDispatcher("/waiter/ordersPage.jsp");
        } else {
            request.setAttribute("categoryList", categoryService.getAll());
            rd = getServletContext()
                    .getRequestDispatcher("/waiter/pricelistPage.jsp");
        }
        rd.forward(request, response);
    }

    private void createOrUpdateOrder(HttpServletRequest request, ProductService productService, OrderService orderService, OrderlistService orderlistService, EmployeeService employeeService, TableService tableService, int userId) {
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        int tableId = Integer.parseInt(request.getParameter("tableId"));

        Order order;
        if ("new".equals(request.getParameter("orderId"))) {
            Employee employee = employeeService.get(userId);
            Table table = tableService.get(tableId);
            Timestamp datetime = new Timestamp(System.currentTimeMillis());
            order = new Order(datetime, employee, table);
            orderService.add(order);
        } else {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            order = orderService.get(orderId);
        }

        Orderlist orderlist = new Orderlist();
        orderlist.setQuantity(quantity);
        orderlist.setProduct(productService.get(productId));
        orderlist.setOrder(order);
        orderlistService.add(orderlist);
    }

    private double parseDouble(String str, HttpServletRequest request) {
        if (str != null && str.length() > 0)
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Error: incorrect value, required number.");
                return -1;
            }
        return 0.0;
    }
}
