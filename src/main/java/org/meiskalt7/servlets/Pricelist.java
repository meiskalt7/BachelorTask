package org.meiskalt7.servlets;

import org.meiskalt7.crud.CategoryService;
import org.meiskalt7.crud.OrderService;
import org.meiskalt7.crud.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Pricelist")
public class Pricelist extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = ProductService.getInstance();
        CategoryService categoryService = CategoryService.getInstance();
        OrderService orderService = OrderService.getInstance();

        if (request.getParameterMap().size() != 0) {

            String button[] = (request.getParameter("button") != null) ? request.getParameter("button").split(" ") : null;

            if (button != null) {

                if ("add".equals(button[0])) {

                    if ("cart".equals(button[1])) {
                    /*int quantity = Integer.parseInt(request.getParameter("quantity"));
                    int productId = Integer.parseInt(request.getParameter("productId"));

                    Orderlist orderlist = new Orderlist();
                    orderlist.setQuantity(quantity);
                    orderlist.setProduct(productService.get(productId));
                    orderService.add(O);*/
                    }
                }
            }

            Integer categoryId = (!"all".equals(request.getParameter("categoryId"))) ? Integer.parseInt(request.getParameter("categoryId")) : null;
            String name = request.getParameter("name").toLowerCase();
            String priceFrom = request.getParameter("priceFrom");
            String priceTo = request.getParameter("priceTo");

            //if (name.length() > 0 || priceFrom.length() > 0 || priceTo.length() > 0) {
            request.setAttribute("productsList", productService.getHQL(categoryId, name, parseDouble(priceFrom, request), parseDouble(priceTo, request)));
            //} else request.setAttribute("errorMessage", "Error: Please fill one or more fields. ");
        } //else request.setAttribute("productsList", productService.getHQL("", "", 0.0, 0.0)); //load productsList

        request.setAttribute("categoryList", categoryService.getAll());

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

