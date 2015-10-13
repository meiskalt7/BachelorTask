package org.meiskalt7.servlets;

import org.meiskalt7.crud.CategoriesService;
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
        //Если выношу то не находит ресурс
        CategoriesService categoriesService = new CategoriesService();
        ProductsService productsService = new ProductsService();

        String category = toNormalCase(request.getParameter("category"));
        String name = toNormalCase(request.getParameter("name"));
        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo");

        request.setAttribute("categoriesList", categoriesService.getHQL(category, name, parseDouble(priceFrom), parseDouble(priceTo)));
        request.setAttribute("productsList", productsService.getHQL(category, name, parseDouble(priceFrom), parseDouble(priceTo)));
        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/index.jsp");
        rd.forward(request, response);


        //CATEGORY
        /*if (!category.isEmpty() & name.isEmpty() & priceFrom.isEmpty() & priceTo.isEmpty()) {
            request.setAttribute("category", category);
            request.setAttribute("productsList", productsService.getByCat(categoriesService.getId(category)));
            RequestDispatcher rd = getServletContext()
                    .getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
        //NAME
        if (category.isEmpty() & !name.isEmpty() & priceFrom.isEmpty() & priceTo.isEmpty()) {
            request.setAttribute("category", categoriesService.get((productsService.getCatByName(name))).getName());
            request.setAttribute("productsList", productsService.getByName(name));
            RequestDispatcher rd = getServletContext()
                    .getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
        //PRICE FROM
        if (category.isEmpty() & name.isEmpty() & !priceFrom.isEmpty() & priceTo.isEmpty()) {
            request.setAttribute("category", categoriesService.getSome(productsService.getByPriceFrom(Double.parseDouble(priceFrom))));
            request.setAttribute("productsList", productsService.getByPriceFrom(Double.parseDouble(priceFrom)));
            RequestDispatcher rd = getServletContext()
                    .getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }*/
    }

    private String toNormalCase(String str) {
        if (!str.isEmpty())
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        return str;
    }

    double parseDouble(String str) {
        if (str != null && str.length() > 0) return Double.parseDouble(str);
        return 0;
    }
}
