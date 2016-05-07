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

        ProductService productService = ProductService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        EmployeeService employeeService = EmployeeService.getInstance();
        TimeRangeService timeRangeService = TimeRangeService.getInstance();
        CompositionService compositionService = CompositionService.getInstance();
        UserTypeService userTypeService = UserTypeService.getInstance();

        final IngridientService ingridientService = IngridientService.getInstance();

        if (request.getParameter("surname") != null) {
            String surname = request.getParameter("surname");
            String name = request.getParameter("name");
            String patronymic = request.getParameter("patronymic");
            String wage = request.getParameter("wage");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
            UserType userType = userTypeService.get(userTypeId);
            Employee employee = new Employee(surname, name, patronymic, parseDouble(wage, request), username, password, userType);
            //userType.getUser().add(employee);
            employeeService.add(employee);
        } else if (request.getParameter("name") != null) {
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            Category category = categoryService.get(categoryId);
            String name = request.getParameter("name");
            String price = request.getParameter("price");

            Product product = new Product(category, name, parseDouble(price, request));
            productService.add(product);
            final String ingridientsId[] = request.getParameterValues("ingridientsId");
            List<Ingridient> ingridients = new ArrayList<Ingridient>() {{
                for (int i = 0; i < ingridientsId.length; i++) {
                    add(ingridientService.get(Integer.parseInt(ingridientsId[i])));
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
        } else if (request.getParameter("categoryName") != null) {
            String categoryName = request.getParameter("categoryName");
            categoryService.add(new Category(categoryName));
        } else if (request.getParameter("productId") != null) {
            productService.delete(Integer.parseInt(request.getParameter("productId")));
        } else if (request.getParameter("categoryId") != null) {
            categoryService.delete(Integer.parseInt(request.getParameter("categoryId")));
        } else if (request.getParameter("employeeId") != null) {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            employeeService.delete(employeeId);
        } else if (request.getParameter("start") != null) {
            Time start = Time.valueOf(request.getParameter("start") + ":00");
            Time finish = Time.valueOf(request.getParameter("finish") + ":00");
            timeRangeService.add(new TimeRange(start, finish));
        } else if (request.getParameter("timerangeId") != null) {
            int timerangeId = Integer.parseInt(request.getParameter("timerangeId"));
            timeRangeService.delete(timerangeId);
        }

        request.setAttribute("productsList", productService.getAll()); //load productsList
        request.setAttribute("categoryList", categoryService.getAll());
        request.setAttribute("employeeList", employeeService.getAll());
        request.setAttribute("timerangeList", timeRangeService.getAll());
        request.setAttribute("ingridList", ingridientService.getAll());
        request.setAttribute("userTypeList", userTypeService.getAll());

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/adminPage.jsp");
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

