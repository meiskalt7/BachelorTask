package org.meiskalt7.servlets;

import org.meiskalt7.crud.CategoryService;
import org.meiskalt7.crud.ProductService;
import org.meiskalt7.entity.Category;
import org.meiskalt7.entity.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Eiskalt on 12.10.2015.
 */
@WebServlet(name = "Pricelist")
public class AdminPage extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();
        CategoryService categoryService = new CategoryService();
        if (request.getParameterMap().size() == 0) {
            //if first load
            request.setAttribute("productsList", productService.getHQL("", "", 0.0, 0.0)); //load productsList
            ////load categoryList
            request.setAttribute("categoryList", categoryService.getAll());
        } else if (request.getParameterMap().size() == 3) {
            //Если послано добавление продукта, то
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            String name = request.getParameter("name");
            String price = request.getParameter("price");
            productService.add(new Product(categoryId, name, parseDouble(price, request)));
            //request.setAttribute("productsList", productService.getHQL(category, name, parseDouble(price, request), 0.0));
        } else if (request.getParameterMap().size() == 1) {
            //Если послано добавление категории
            String categoryName = request.getParameter("categoryName");
            categoryService.add(new Category(categoryName));
        } else if (true) {
            //Действие на удаление товара
        } else if (true) {
            //Действие на удаление категории
        }

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

