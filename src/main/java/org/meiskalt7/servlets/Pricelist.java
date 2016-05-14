package org.meiskalt7.servlets;

import org.meiskalt7.crud.*;
import org.meiskalt7.entity.Employee;
import org.meiskalt7.entity.Order;
import org.meiskalt7.entity.Orderlist;
import org.meiskalt7.entity.Table;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "Pricelist")
public class Pricelist extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = ProductService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        OrderService orderService = OrderService.getInstance();
        OrderlistService orderlistService = OrderlistService.getInstance();
        EmployeeService employeeService = EmployeeService.getInstance();
        TableService tableService = TableService.getInstance();

        if (request.getParameterMap().size() != 0) {

            String button[] = (request.getParameter("button") != null) ? request.getParameter("button").split(" ") : null;

            if (request.getParameter("userId").length() > 0) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                request.setAttribute("waiterTableList", employeeService.get(userId).getTables());
                request.setAttribute("orderList", employeeService.get(userId).getOrderList());

                if (button != null) {

                    Operation operation = Operation.valueOf(button[0]);

                    switch (operation) {
                        case ADD:
                            if ("cart".equals(button[1])) {
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
                                //order.getOrderlists().add(orderlist);

                            }
                            break;
                        case UPDATE:
                            break;
                        case DELETE:
                            break;
                    }
                }
            }

            if (request.getParameter("categoryId") != null) {
                Integer categoryId = (!"all".equals(request.getParameter("categoryId"))) ? Integer.parseInt(request.getParameter("categoryId")) : null;
                String name = request.getParameter("name").toLowerCase();
                String priceFrom = request.getParameter("priceFrom");
                String priceTo = request.getParameter("priceTo");

                request.setAttribute("productsList", productService.getHQL(categoryId, name, parseDouble(priceFrom, request), parseDouble(priceTo, request)));
            }
        }

        request.setAttribute("categoryList", categoryService.getAll());


        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
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

