package org.meiskalt7.servlets;

import org.meiskalt7.crud.CategoryService;
import org.meiskalt7.crud.EmployeeService;
import org.meiskalt7.crud.ProductService;
import org.meiskalt7.crud.TimeRangeService;
import org.meiskalt7.entity.Category;
import org.meiskalt7.entity.Employee;
import org.meiskalt7.entity.Product;
import org.meiskalt7.entity.TimeRange;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;

@WebServlet(name = "AdminPage")
public class AdminPage extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService productService = ProductService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        EmployeeService employeeService = EmployeeService.getInstance();
        TimeRangeService timeRangeService = TimeRangeService.getInstance();

        if (request.getParameter("surname") != null) {
            String surname = request.getParameter("surname");
            String name = request.getParameter("name");
            String patronymic = request.getParameter("patronymic");
            String wage = request.getParameter("wage");
            employeeService.add(new Employee(surname, name, patronymic, parseDouble(wage, request)));
        } else if (request.getParameter("name") != null) {
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            Category category = categoryService.get(categoryId);
            String name = request.getParameter("name");
            String price = request.getParameter("price");
            productService.add(new Product(category, name, parseDouble(price, request)));
            //request.setAttribute("productsList", productService.getHQL(category, name, parseDouble(price, request), 0.0));
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
        }

        request.setAttribute("productsList", productService.getHQL("", "", 0.0, 0.0)); //load productsList
        request.setAttribute("categoryList", categoryService.getAll());
        request.setAttribute("employeeList", employeeService.getAll());
        request.setAttribute("timerangeList", timeRangeService.getAll());

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/adminPage.jsp");
        rd.forward(request, response);
    }

    double parseDouble(String str, HttpServletRequest request) {
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

