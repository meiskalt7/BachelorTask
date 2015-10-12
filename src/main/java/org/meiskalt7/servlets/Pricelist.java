package org.meiskalt7.servlets;

import org.meiskalt7.crud.CategoriesService;
import org.meiskalt7.crud.ProductsService;
import org.meiskalt7.entity.Categories;

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
        //���� ������ �� �� ������� ������
        CategoriesService categoriesService = new CategoriesService();
        ProductsService productsService = new ProductsService();

        String category = toNormalCase(request.getParameter("category"));
        String name = toNormalCase(request.getParameter("name"));
        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo");

        //���� ��������� ���� ���������, �� ���� �� ��������� ��������(id) ��� ������ ������� (catid)
        if (!category.isEmpty() & name.isEmpty() & priceFrom.isEmpty() & priceTo.isEmpty()) {
            request.setAttribute("category", category);
            request.setAttribute("productsList", productsService.getByCat(categoriesService.getId(category)));
            RequestDispatcher rd = getServletContext()
                    .getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }

        if (!category.isEmpty() & !name.isEmpty() & priceFrom.isEmpty() & priceTo.isEmpty()) {
            System.out.println(categoriesService.getId(category));
        }

        if (category.isEmpty() & name.isEmpty() & priceFrom.isEmpty() & priceTo.isEmpty()) {
            for (Categories c : categoriesService.getAll()) {
                System.out.println(c);
            }
        }
    }

    private String toNormalCase(String str) {
        if (!str.isEmpty())
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
        return str;
    }
}
