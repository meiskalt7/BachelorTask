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
        if (request.getParameter("name") != null) {
            //Если послано добавление продукта, то(/!добавить запись айдишника, выбрать что из первых двух параметров реально нужно)
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            Category category = categoryService.get(categoryId);
            String name = request.getParameter("name");
            String price = request.getParameter("price");
            productService.add(new Product(category, name, parseDouble(price, request)));
            //request.setAttribute("productsList", productService.getHQL(category, name, parseDouble(price, request), 0.0));
        } else if (request.getParameter("categoryName") != null) {
            //Если послано добавление категории(/! добавить запись айдишника)
            String categoryName = request.getParameter("categoryName");
            categoryService.add(new Category(categoryName));
        } else if (request.getParameter("productId") != null) {
            //Действие на удаление товара
            productService.delete(Integer.parseInt(request.getParameter("productId")));
        } else if (request.getParameter("categoryId") != null) {
            //Действие на удаление категории
            categoryService.delete(Integer.parseInt(request.getParameter("categoryId")));
        }

        request.setAttribute("productsList", productService.getHQL("", "", 0.0, 0.0)); //load productsList
        request.setAttribute("categoryList", categoryService.getAll());

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

