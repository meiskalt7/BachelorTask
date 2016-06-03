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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "ControllerServlet")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService productService = ProductService.getInstance();
        OrderlistService orderlistService = OrderlistService.getInstance();

        if (request.getParameter("button") != null) {
            String button[] = request.getParameter("button").split(" ");

            Operation operation = Operation.valueOf(button[0]);
            Entity entity = Entity.valueOf(button[1]);

            switch (operation) {
                case ADD:
                    switch (entity) {
                        case TABLES_EMPLOYEES:
                            assignTables(request);
                            break;
                        case CART:
                            int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                            createOrUpdateOrder(request, orderlistService, userId);
                            break;
                        default:
                            Service.getService(entity).create(request);
                            break;
                    }
                    break;
                case UPDATE: {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Service.getService(entity).update(request, id);
                    break;
                }
                case DELETE:
                    int id = Integer.parseInt(request.getParameter("id"));
                    switch (entity) {
                        case TABLES_EMPLOYEES:
                            deassignTables(request, id);
                            break;
                        default:
                            Service.getService(entity).delete(id);
                            break;
                    }
                    break;
                case READ:
                    if (request.getParameter("categoryId") != null) {
                        Integer categoryId = (!"all".equals(request.getParameter("categoryId"))) ? Integer.parseInt(request.getParameter("categoryId")) : null;
                        String name = request.getParameter("name").toLowerCase();
                        String priceFrom = request.getParameter("priceFrom");
                        String priceTo = request.getParameter("priceTo");

                        request.setAttribute("productsList", productService.getHQL(categoryId, name, priceFrom, priceTo, request));
                    }
                    break;
            }
        }

        if (request.getSession().getAttribute("userId") != null) {

            EmployeeService employeeService = (EmployeeService) Service.getService(Entity.EMPLOYEE);

            int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
            Employee employee = employeeService.get(userId);
            employeeService.refresh(employee);
            request.setAttribute("orderList", employee.getOrderList());
            request.setAttribute("waiterTableList", employee.getTables());
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        request.setAttribute("today", simpleDateFormat.format(new java.util.Date()));

        RequestDispatcher rd;
        ServletPath servletPath = ServletPath.getByPath(request.getServletPath());//request.getServletPath()

        switch (servletPath) {
            case ADMIN:
                request.setAttribute("ingridList", Service.getService(Entity.INGRIDIENT).getAll());
                request.setAttribute("employeeList", Service.getService(Entity.EMPLOYEE).getAll());
                request.setAttribute("userTypeList", Service.getService(Entity.USERTYPE).getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/admin/adminPage.jsp");
                break;
            case HALL:
                request.setAttribute("employeeList", Service.getService(Entity.EMPLOYEE).getAll());
                request.setAttribute("tableList", Service.getService(Entity.TABLE).getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/manager/hallPage.jsp");
                break;
            case ORDERS:
                rd = getServletContext()
                        .getRequestDispatcher("/waiter/ordersPage.jsp");
                break;
            case PRICELIST:
                request.setAttribute("categoryList", Service.getService(Entity.CATEGORY).getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/waiter/pricelistPage.jsp");
                break;
            case PRODUCTS:
                request.setAttribute("ingridList", Service.getService(Entity.INGRIDIENT).getAll());
                request.setAttribute("productsList", Service.getService(Entity.PRODUCT).getAll());
                request.setAttribute("categoryList", Service.getService(Entity.CATEGORY).getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/manager/productsPage.jsp");
                break;
            case RESERVATIONS:
                request.setAttribute("reservationList", Service.getService(Entity.RESERVATION).getAll());
                request.setAttribute("tableList", Service.getService(Entity.TABLE).getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/waiter/reservationsPage.jsp");
                break;
            case STOCK:
                request.setAttribute("ingridList", Service.getService(Entity.INGRIDIENT).getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/manager/stockPage.jsp");
                break;
            case WORKSHIFT:
                request.setAttribute("workshiftList", Service.getService(Entity.WORKSHIFT).getAll());
                request.setAttribute("employeeList", Service.getService(Entity.EMPLOYEE).getAll());
                request.setAttribute("timerangeList", Service.getService(Entity.TIMERANGE).getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/manager/workshiftPage.jsp");
                break;
            default:
                rd = null;
                break;
        }

        rd.forward(request, response);
    }

    private void deassignTables(HttpServletRequest req, int id) {

        EmployeeService employeeService = (EmployeeService) Service.getService(Entity.EMPLOYEE);

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

    private void assignTables(HttpServletRequest req) {

        Service employeeService = Service.getService(Entity.EMPLOYEE);
        final Service tableService = Service.getService(Entity.TABLE);

        Employee employee = (Employee) employeeService.get(Integer.parseInt(req.getParameter("employee")));
        final String tablesId[] = req.getParameterValues("tables");
        List<Table> tables = new ArrayList<Table>() {{
            for (String aTablesId : tablesId) {
                add((Table) tableService.get(Integer.parseInt(aTablesId)));
            }
        }};
        employee.getTables().addAll(tables);
        employeeService.update(employee);
    }

    private void createOrUpdateOrder(HttpServletRequest request, OrderlistService orderlistService, int userId) {

        ProductService productService = (ProductService) Service.getService(Entity.PRODUCT);
        OrderService orderService = (OrderService) Service.getService(Entity.ORDER);
        EmployeeService employeeService = (EmployeeService) Service.getService(Entity.EMPLOYEE);
        TableService tableService = (TableService) Service.getService(Entity.TABLE);

        int tableId = Integer.parseInt(request.getParameter("tableId"));

        Order order;
        if ("new".equals(request.getParameter("orderId"))) {
            Employee employee = employeeService.get(userId);
            Table table = tableService.get(tableId);
            Timestamp datetime = new Timestamp(System.currentTimeMillis());
            order = new Order(datetime, employee, table);
            orderService.add(order);
            employeeService.refresh(employee);
        } else {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            order = orderService.get(orderId);
        }

        int i = 0;
        String productId[] = request.getParameterValues("productId");
        String quantity[] = request.getParameterValues("quantity");

        while (i < productId.length) {
            if (!"".equals(productId[i]) && !"".equals(quantity[i])) {
                Orderlist orderlist = new Orderlist();
                orderlist.setQuantity(Integer.parseInt(quantity[i]));
                orderlist.setProduct(productService.get(Integer.parseInt(productId[i])));
                orderlist.setOrder(order);
                orderlistService.add(orderlist);
            }
            i++;
        }
        orderService.refresh(order);
    }
}
