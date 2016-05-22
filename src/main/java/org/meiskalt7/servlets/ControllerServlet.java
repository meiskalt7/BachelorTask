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
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "ControllerServlet")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //TODO логирование изменений пользователями
        //TODO добавить ингридиент(удаление третьей и т.д. ячеек)
        IngridientService ingridientService = IngridientService.getInstance();
        WorkshiftService workshiftService = WorkshiftService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        final EmployeeService employeeService = EmployeeService.getInstance();
        TimeRangeService timeRangeService = TimeRangeService.getInstance();
        final TableService tableService = TableService.getInstance();
        ProductService productService = ProductService.getInstance();
        CompositionService compositionService = CompositionService.getInstance();
        UserTypeService userTypeService = UserTypeService.getInstance();
        OrderService orderService = OrderService.getInstance();
        OrderlistService orderlistService = OrderlistService.getInstance();
        ReservationService reservationService = ReservationService.getInstance();

        if (request.getParameter("button") != null) {
            String button[] = request.getParameter("button").split(" ");

            Operation operation = Operation.valueOf(button[0]);
            Entity entity = Entity.valueOf(button[1]);

            switch (operation) {
                case ADD:
                    switch (entity) {
                        case PRODUCT:
                            createProduct(request, productService, categoryService, compositionService, ingridientService);
                            break;
                        case CATEGORY:
                            createCategory(request, categoryService);
                            break;
                        case EMPLOYEE:
                            createEmployee(request, employeeService, userTypeService);
                            break;
                        case TIMERANGE:
                            createTimeRange(request, timeRangeService);
                            break;
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
                            break;
                        case CART:
                            int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                            createOrUpdateOrder(request, productService, orderService, orderlistService, employeeService, tableService, userId);
                            //order.getOrderlists().add(orderlist);
                            break;
                        case RESERVATION:
                            createReservation(request, tableService, reservationService);
                            break;
                    }
                    break;
                case UPDATE: {
                    int id = Integer.parseInt(request.getParameter("id"));
                    switch (entity) {
                        case INGRIDIENT:
                            updateIngridient(request, ingridientService, id);
                            break;
                        case TIMERANGE:
                            updateTimeRange(request, timeRangeService);
                            break;
                        case EMPLOYEE:
                            updateEmployee(request, employeeService, userTypeService);
                            break;
                        case CATEGORY:
                            updateCategory(request, categoryService);
                            break;
                        case ORDER:
                            Order order = orderService.get(id);
                            order.setEnded(true);
                            orderService.update(order);
                            break;
                        case TABLE:
                            updateTable(request, tableService, id);
                            break;
                        case RESERVATION:
                            updateReservation(request, reservationService, tableService, id);
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
                        case TABLES_EMPLOYEES:
                            deassignTables(request, employeeService, id);
                            break;
                        case WORKSHIFT:
                            workshiftService.delete(id);
                            break;
                        case PRODUCT:
                            productService.delete(id);
                            break;
                        case CATEGORY:
                            categoryService.delete(id);
                            break;
                        case EMPLOYEE:
                            employeeService.delete(id);
                            break;
                        case TIMERANGE:
                            timeRangeService.delete(id);
                            break;
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

        if (request.getSession().getAttribute("userId") != null) {
            int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
            Employee employee = employeeService.get(userId);
            request.setAttribute("orderList", employee.getOrderList());
            request.setAttribute("waiterTableList", employee.getTables());
        }

        if (request.getParameter("categoryId") != null) {
            Integer categoryId = (!"all".equals(request.getParameter("categoryId"))) ? Integer.parseInt(request.getParameter("categoryId")) : null;
            String name = request.getParameter("name").toLowerCase();
            String priceFrom = request.getParameter("priceFrom");
            String priceTo = request.getParameter("priceTo");

            request.setAttribute("productsList", productService.getHQL(categoryId, name, parseDouble(priceFrom, request), parseDouble(priceTo, request)));
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        request.setAttribute("today", simpleDateFormat.format(new java.util.Date()));

        RequestDispatcher rd;
        ServletPath servletPath = ServletPath.getByPath(request.getServletPath());//request.getServletPath()

        switch (servletPath) {
            case ADMIN:
                request.setAttribute("ingridList", ingridientService.getAll());
                request.setAttribute("employeeList", employeeService.getAll());
                request.setAttribute("userTypeList", userTypeService.getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/admin/adminPage.jsp");
                break;
            case HALL:
                request.setAttribute("employeeList", employeeService.getAll());
                request.setAttribute("tableList", tableService.getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/manager/hallPage.jsp");
                break;
            case ORDERS:
                rd = getServletContext()
                        .getRequestDispatcher("/waiter/ordersPage.jsp");
                break;
            case PRICELIST:
                request.setAttribute("categoryList", categoryService.getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/waiter/pricelistPage.jsp");
                break;
            case PRODUCTS:
                request.setAttribute("ingridList", ingridientService.getAll());
                request.setAttribute("productsList", productService.getAll());
                request.setAttribute("categoryList", categoryService.getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/manager/productsPage.jsp");
                break;
            case RESERVATIONS:
                request.setAttribute("reservationList", reservationService.getAll());
                request.setAttribute("tableList", tableService.getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/waiter/reservationsPage.jsp");
                break;
            case STOCK:
                request.setAttribute("ingridList", ingridientService.getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/manager/stockPage.jsp");
                break;
            case WORKSHIFT:
                request.setAttribute("workshiftList", workshiftService.getAll());
                request.setAttribute("employeeList", employeeService.getAll());
                request.setAttribute("timerangeList", timeRangeService.getAll());
                rd = getServletContext()
                        .getRequestDispatcher("/manager/workshiftPage.jsp");
                break;
            default:
                rd = null;
                break;
        }

        rd.forward(request, response);
    }

    private void createReservation(HttpServletRequest request, TableService tableService, ReservationService reservationService) {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        Timestamp time = Timestamp.valueOf(request.getParameter("time").replace("T", " ") + ":00");
        int tableId = Integer.parseInt(request.getParameter("tableId"));

        Table table = tableService.get(tableId);

        Reservation reservation = new Reservation(name, phone, time, table);
        reservationService.add(reservation);

        request.setAttribute("resultMessage", "Столик забронирован");
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

    private void updateTable(HttpServletRequest request, TableService tableService, int id) {
        Table table = tableService.get(id);

        int number = Integer.parseInt(request.getParameter("number"));
        String type = request.getParameter("table");

        table.setNumber(number);
        table.setType(type);

        tableService.update(table);
    }

    private void updateReservation(HttpServletRequest request, ReservationService reservationService, TableService tableService, int id) {
        Reservation reservation = reservationService.get(id);

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        Timestamp time = Timestamp.valueOf(request.getParameter("time").replace("T", " ") + ":00");

        reservation.setName(name);
        reservation.setPhone(phone);
        reservation.setDatetime(time);

        int tableId = Integer.parseInt(request.getParameter("tableId"));
        Table table = tableService.get(tableId);
        reservation.setTable(table);
        reservation.setId_table(tableId);

        reservationService.update(reservation);
    }

    private void createProduct(HttpServletRequest request, ProductService productService, CategoryService categoryService, CompositionService compositionService, final IngridientService ingridientService) {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        Category category = categoryService.get(categoryId);
        String name = request.getParameter("name");
        String price = request.getParameter("price");

        Product product = new Product(category, name, parseDouble(price, request));
        productService.add(product);
        final String ingridientsId[] = request.getParameterValues("ingridientsId");
        List<Ingridient> ingridients = new ArrayList<Ingridient>() {{
            for (String anIngridientsId : ingridientsId) {
                add(ingridientService.get(Integer.parseInt(anIngridientsId)));
            }
        }};
        String required[] = request.getParameterValues("quantity");
        int i = 0;
        for (Ingridient ingridient : ingridients) {
            Composition composition = new Composition();
            composition.setProduct(product);
            composition.setIngridient(ingridient);
            composition.setRequired(Integer.parseInt(required[i]));
            compositionService.add(composition);
            product.getIngridients().add(composition);
            i++;
        }
    }

    private void createEmployee(HttpServletRequest request, EmployeeService employeeService, UserTypeService userTypeService) {
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String patronymic = request.getParameter("patronymic");
        String wage = request.getParameter("wage");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
        UserType userType = userTypeService.get(userTypeId);
        Employee employee = new Employee(surname, name, patronymic, parseDouble(wage, request), username, password, userType);
        employeeService.add(employee);
    }

    private void createTimeRange(HttpServletRequest request, TimeRangeService timeRangeService) {
        Time start = Time.valueOf(request.getParameter("start") + ":00");
        Time finish = Time.valueOf(request.getParameter("finish") + ":00");
        timeRangeService.add(new TimeRange(start, finish));
    }

    private void createCategory(HttpServletRequest request, CategoryService categoryService) {
        String categoryName = request.getParameter("categoryName");
        categoryService.add(new Category(categoryName));
    }

    private void createOrUpdateOrder(HttpServletRequest request, ProductService productService, OrderService orderService, OrderlistService orderlistService, EmployeeService employeeService, TableService tableService, int userId) {

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

        int i = 0;
        String productId[] = request.getParameterValues("productId");
        String quantity[] = request.getParameterValues("quantity");

        while (i < productId.length) {
            if (productId[i] != "" && quantity[i] != "") {
                Orderlist orderlist = new Orderlist();
                orderlist.setQuantity(Integer.parseInt(quantity[i]));
                orderlist.setProduct(productService.get(Integer.parseInt(productId[i])));
                orderlist.setOrder(order);
                orderlistService.add(orderlist);
            }
            i++;
        }
    }

    private void updateCategory(HttpServletRequest request, CategoryService categoryService) {
        int id = Integer.parseInt(request.getParameter("id"));
        Category category = categoryService.get(id);
        String categoryName = request.getParameter("categoryName");
        if (categoryName != null && !categoryName.equals(category.getName())) {
            category.setName(categoryName);
            categoryService.update(category);
        }
    }

    private void updateTimeRange(HttpServletRequest request, TimeRangeService timeRangeService) {
        int id = Integer.parseInt(request.getParameter("id"));
        TimeRange timeRange = timeRangeService.get(id);
        Time start = Time.valueOf(request.getParameter("start") + ":00");
        Time finish = Time.valueOf(request.getParameter("finish") + ":00");
        timeRange.setStart(start);
        timeRange.setFinish(finish);
        timeRangeService.update(timeRange);
    }

    private void updateEmployee(HttpServletRequest request, EmployeeService employeeService, UserTypeService userTypeService) {
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String patronymic = request.getParameter("patronymic");
        String wage = request.getParameter("wage");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        int id = Integer.parseInt(request.getParameter("id"));
        Employee employee = employeeService.get(id);


        if (surname != null && !surname.equals(employee.getSurname())) {
            employee.setSurname(surname);
        }

        if (name != null && !name.equals(employee.getName())) {
            employee.setName(name);
        }

        if (patronymic != null && !name.equals(employee.getPatronymic())) {
            employee.setPatronymic(patronymic);
        }

        if (wage != null && !wage.equals(employee.getWage())) {
            employee.setWage(parseDouble(wage, request));
        }

        if (username != null && !username.equals(employee.getUsername())) {
            employee.setUsername(username);
        }

        if (password != null && !password.equals(employee.getPassword())) {
            employee.setPassword(password);
        }

        int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
        UserType userType = userTypeService.get(userTypeId);
        if (userType != null && !userType.equals(employee.getUserType())) {
            employee.setUserType(userType);
        }

        employeeService.update(employee);
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
