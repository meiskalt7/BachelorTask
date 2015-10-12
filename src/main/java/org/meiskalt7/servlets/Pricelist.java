package org.meiskalt7.servlets;

import org.meiskalt7.crud.CategoriesService;
import org.meiskalt7.crud.ProductsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Eiskalt on 12.10.2015.
 */
@WebServlet
public class Pricelist extends HttpServlet {

    private CategoriesService categoriesService;
    private ProductsService productsService;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String field = request.getParameter("category");
        String name = request.getParameter("name");
        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo");

        System.out.println(field + " " + " " + name + " " + priceFrom + " " + priceTo);
    }
}
