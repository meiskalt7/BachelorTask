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
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdminPage")
public class AdminPage extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //TODO логирование изменений пользователями
        //TODO добавить ингридиент(удаление третьей и т.д. ячеек)
        ProductService productService = ProductService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        EmployeeService employeeService = EmployeeService.getInstance();
        TimeRangeService timeRangeService = TimeRangeService.getInstance();
        CompositionService compositionService = CompositionService.getInstance();
        UserTypeService userTypeService = UserTypeService.getInstance();
        final IngridientService ingridientService = IngridientService.getInstance();

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
                    }
                    break;
                case UPDATE:

                    switch (entity) {
                        case TIMERANGE:
                            updateTimeRange(request, timeRangeService);
                            break;
                        case EMPLOYEE:
                            updateEmployee(request, employeeService, userTypeService);
                            break;
                        case CATEGORY:
                            updateCategory(request, categoryService);
                            break;
                    }

                    break;
                case DELETE:
                    switch (entity) {
                        case PRODUCT:
                            deleteProduct(request, productService);
                            break;
                        case CATEGORY:
                            deleteCategory(request, categoryService);
                            break;
                        case EMPLOYEE:
                            deleteEmployee(request, employeeService);
                            break;
                        case TIMERANGE:
                            deleteTimeRange(request, timeRangeService);
                            break;
                    }
                    break;
            }
        }

        request.setAttribute("productsList", productService.getAll()); //load productsList
        request.setAttribute("categoryList", categoryService.getAll());
        request.setAttribute("employeeList", employeeService.getAll());
        request.setAttribute("timerangeList", timeRangeService.getAll());
        request.setAttribute("ingridList", ingridientService.getAll());
        request.setAttribute("userTypeList", userTypeService.getAll());

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/admin/adminPage.jsp");
        rd.forward(request, response);
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

    private void deleteTimeRange(HttpServletRequest request, TimeRangeService timeRangeService) {
        int timerangeId = Integer.parseInt(request.getParameter("timerangeId"));
        timeRangeService.delete(timerangeId);
    }

    private void deleteEmployee(HttpServletRequest request, EmployeeService employeeService) {
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        employeeService.delete(employeeId);
    }

    private void deleteCategory(HttpServletRequest request, CategoryService categoryService) {
        categoryService.delete(Integer.parseInt(request.getParameter("categoryId")));
    }

    private void deleteProduct(HttpServletRequest request, ProductService productService) {
        productService.delete(Integer.parseInt(request.getParameter("productId")));
    }

    private void createCategory(HttpServletRequest request, CategoryService categoryService) {
        String categoryName = request.getParameter("categoryName");
        categoryService.add(new Category(categoryName));
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

    private void createTimeRange(HttpServletRequest request, TimeRangeService timeRangeService) {
        Time start = Time.valueOf(request.getParameter("start") + ":00");
        Time finish = Time.valueOf(request.getParameter("finish") + ":00");
        timeRangeService.add(new TimeRange(start, finish));
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

