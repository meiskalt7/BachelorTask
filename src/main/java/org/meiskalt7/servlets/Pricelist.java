package org.meiskalt7.servlets;

import org.meiskalt7.crud.ProductService;

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
public class Pricelist extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();
        if (request.getParameterMap().size() != 0) {
            String category = request.getParameter("category").toLowerCase();
            String name = request.getParameter("name").toLowerCase();
            String priceFrom = request.getParameter("priceFrom");
            String priceTo = request.getParameter("priceTo");

            if (category.length() > 0 || name.length() > 0 || priceFrom.length() > 0 || priceTo.length() > 0) {
                request.setAttribute("productsList", productService.getHQL(category, name, parseDouble(priceFrom, request), parseDouble(priceTo, request)));
            } else request.setAttribute("errorMessage", "Error: Please fill one or more fields. ");
        } //else request.setAttribute("productsList", productService.getHQL("", "", 0.0, 0.0)); //load productsList
        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/index.jsp");
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

