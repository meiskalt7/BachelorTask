package org.meiskalt7.servlets;

import org.meiskalt7.crud.ProductsService;

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
        ProductsService productsService = new ProductsService();

        String category = request.getParameter("category").toLowerCase();
        String name = request.getParameter("name").toLowerCase();
        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo");

        request.setAttribute("productsList", productsService.getHQL(category, name, parseDouble(priceFrom), parseDouble(priceTo)));
        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }

    double parseDouble(String str) {
        if (str != null && str.length() > 0) return Double.parseDouble(str);
        return 0;
    }
}
