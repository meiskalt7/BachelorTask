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
import java.util.Objects;

@WebServlet(name = "ControllerServlet")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final EmployeeService employeeService = EmployeeService.getInstance();
        final TableService tableService = TableService.getInstance();
        ProductService productService = ProductService.getInstance();
        CompositionService compositionService = CompositionService.getInstance();
        OrderlistService orderlistService = OrderlistService.getInstance();

        if (request.getParameter("button") != null) {
            String button[] = request.getParameter("button").split(" ");

            Operation operation = Operation.valueOf(button[0]);
            Entity entity = Entity.valueOf(button[1]);

            switch (operation) {
                case ADD:
                    switch (entity) {
                        case PRODUCT:
                            createProduct(request, compositionService);
                            break;
                        case CATEGORY:
                            createCategory(request);
                            break;
                        case EMPLOYEE:
                            createEmployee(request);
                            break;
                        case TIMERANGE:
                            createTimeRange(request);
                            break;
                        case INGRIDIENT:
                            createIngridient(request);
                            break;
                        case WORKSHIFT:
                            createWorkshift(request);
                            break;
                        case TABLES_EMPLOYEES:
                            assignTables(request);
                            break;
                        case TABLE:
                            createTable(request);
                            break;
                        case CART:
                            int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                            createOrUpdateOrder(request, orderlistService, userId);
                            break;
                        case RESERVATION:
                            createReservation(request);
                            break;
                    }
                    break;
                case UPDATE: {
                    int id = Integer.parseInt(request.getParameter("id"));
                    switch (entity) {
                        case INGRIDIENT:
                            updateIngridient(request, Service.getService(entity), id);
                            break;
                        case TIMERANGE:
                            updateTimeRange(request);
                            break;
                        case EMPLOYEE:
                            updateEmployee(request);
                            break;
                        case CATEGORY:
                            updateCategory(request);
                            break;
                        case ORDER:
                            updateOrder(id);
                            break;
                        case TABLE:
                            updateTable(request, id);
                            break;
                        case RESERVATION:
                            updateReservation(request, Service.getService(entity), tableService, id);
                            break;
                    }
                    break;
                }
                case DELETE:
                    int id = Integer.parseInt(request.getParameter("id"));
                    switch (entity) {
                        case TABLES_EMPLOYEES:
                            deassignTables(request, id);
                            break;
                        case CATEGORY:
                        case EMPLOYEE:
                        case INGRIDIENT:
                        case ORDER:
                        case PRODUCT:
                        case RESERVATION:
                        case TABLE:
                        case TIMERANGE:
                        case WORKSHIFT:
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

                        request.setAttribute("productsList", productService.getHQL(categoryId, name, parseDouble(priceFrom, request), parseDouble(priceTo, request)));
                    }
                    break;
            }
        }

        if (request.getSession().getAttribute("userId") != null) {
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

    private void updateOrder(int id) {

        OrderService orderService = (OrderService) Service.getService(Entity.ORDER);

        Order order = orderService.get(id);
        order.setEnded(true);
        orderService.update(order);
    }

    private void createReservation(HttpServletRequest request) {

        TableService tableService = (TableService) Service.getService(Entity.TABLE);
        ReservationService reservationService = (ReservationService) Service.getService(Entity.RESERVATION);

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        Timestamp time = Timestamp.valueOf(request.getParameter("time").replace("T", " ") + ":00");
        int tableId = Integer.parseInt(request.getParameter("tableId"));

        Table table = tableService.get(tableId);

        Reservation reservation = new Reservation(name, phone, time, table);
        reservationService.add(reservation);

        request.setAttribute("resultMessage", "Столик забронирован");
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

    private void createTable(HttpServletRequest req) {

        Service tableService = Service.getService(Entity.TABLE);

        int number = Integer.parseInt(req.getParameter("number"));
        tableService.add(new Table(number, req.getParameter("table")));
    }

    private void createWorkshift(HttpServletRequest req) {

        Service workshiftService = Service.getService(Entity.WORKSHIFT);
        final Service employeeService = Service.getService(Entity.EMPLOYEE);
        Service timeRangeService = Service.getService(Entity.TIMERANGE);

        final String[] employeeId = req.getParameterValues("employee");
        List<Employee> employees = new ArrayList<Employee>() {{
            for (String anEmployeeId : employeeId) {
                add((Employee) employeeService.get(Integer.parseInt(anEmployeeId)));
            }
        }};
        Date date = Date.valueOf(req.getParameter("date"));
        int timerangeId = Integer.parseInt(req.getParameter("timerange"));
        TimeRange timeRange = (TimeRange) timeRangeService.get(timerangeId);
        Workshift workshift = new Workshift(date, timeRange, employees);
        workshiftService.add(workshift);
        for (Employee emp : employees) {
            emp.getWorkshifts().add(workshift);
            employeeService.update(emp);
        }
    }

    private void createIngridient(HttpServletRequest req) {

        Service ingridientService = Service.getService(Entity.INGRIDIENT);

        String name = req.getParameter("name");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        double price = Double.parseDouble(req.getParameter("price"));
        ingridientService.add(new Ingridient(name, quantity, price));
    }

    private void updateIngridient(HttpServletRequest req, Service ingridientService, int id) {

        Ingridient ingridient = (Ingridient) ingridientService.get(id);

        String name = req.getParameter("name");

        if (!Objects.equals(name, ingridient.getName())) {
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

    private void updateTable(HttpServletRequest request, int id) {

        Service tableService = Service.getService(Entity.TABLE);

        Table table = (Table) tableService.get(id);

        int number = Integer.parseInt(request.getParameter("number"));
        String type = request.getParameter("table");

        table.setNumber(number);
        table.setType(type);

        tableService.update(table);
    }

    private void updateReservation(HttpServletRequest request, Service reservationService, TableService tableService, int id) {
        Reservation reservation = (Reservation) reservationService.get(id);

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

    private void createProduct(HttpServletRequest request, CompositionService compositionService) {

        Service productService = Service.getService(Entity.PRODUCT);
        Service categoryService = Service.getService(Entity.CATEGORY);
        final Service ingridientService = Service.getService(Entity.INGRIDIENT);

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        Category category = (Category) categoryService.get(categoryId);
        String name = request.getParameter("name");
        String price = request.getParameter("price");

        Product product = new Product(category, name, parseDouble(price, request));
        productService.add(product);
        final String ingridientsId[] = request.getParameterValues("ingridientsId");
        List<Ingridient> ingridients = new ArrayList<Ingridient>() {{
            for (String anIngridientsId : ingridientsId) {
                add((Ingridient) ingridientService.get(Integer.parseInt(anIngridientsId)));
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

    private void createEmployee(HttpServletRequest request) {

        Service employeeService = Service.getService(Entity.EMPLOYEE);
        Service userTypeService = Service.getService(Entity.USERTYPE);

        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String patronymic = request.getParameter("patronymic");
        String wage = request.getParameter("wage");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
        UserType userType = (UserType) userTypeService.get(userTypeId);
        Employee employee = new Employee(surname, name, patronymic, parseDouble(wage, request), username, password, userType);
        employeeService.add(employee);
    }

    private void createTimeRange(HttpServletRequest request) {

        Service timeRangeService = Service.getService(Entity.TIMERANGE);

        Time start = Time.valueOf(request.getParameter("start") + ":00");
        Time finish = Time.valueOf(request.getParameter("finish") + ":00");
        timeRangeService.add(new TimeRange(start, finish));
    }

    private void createCategory(HttpServletRequest request) {

        Service categoryService = Service.getService(Entity.CATEGORY);

        String categoryName = request.getParameter("categoryName");
        categoryService.add(new Category(categoryName));
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

    private void updateCategory(HttpServletRequest request) {

        Service categoryService = Service.getService(Entity.CATEGORY);

        int id = Integer.parseInt(request.getParameter("id"));
        Category category = (Category) categoryService.get(id);
        String categoryName = request.getParameter("categoryName");
        if (categoryName != null && !categoryName.equals(category.getName())) {
            category.setName(categoryName);
            categoryService.update(category);
        }
    }

    private void updateTimeRange(HttpServletRequest request) {

        Service timeRangeService = Service.getService(Entity.TIMERANGE);

        int id = Integer.parseInt(request.getParameter("id"));
        TimeRange timeRange = (TimeRange) timeRangeService.get(id);
        Time start = Time.valueOf(request.getParameter("start") + ":00");
        Time finish = Time.valueOf(request.getParameter("finish") + ":00");
        timeRange.setStart(start);
        timeRange.setFinish(finish);
        timeRangeService.update(timeRange);
    }

    private void updateEmployee(HttpServletRequest request) {

        EmployeeService employeeService = (EmployeeService) Service.getService(Entity.EMPLOYEE);
        UserTypeService userTypeService = (UserTypeService) Service.getService(Entity.USERTYPE);

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

        if (patronymic != null && !patronymic.equals(employee.getPatronymic())) {
            employee.setPatronymic(patronymic);
        }

        if (wage != null && !parseDouble(wage, request).equals(employee.getWage())) {
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

    private Double parseDouble(String str, HttpServletRequest request) {
        if (str != null && str.length() > 0)
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Error: incorrect value, required number.");
                return -1.0;
            }
        return 0.0;
    }
}
