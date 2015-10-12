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

    //@Resource(name = "jdbc/XE")
    /*public void init() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("CLASS NOT FOUND!!!");
            e.printStackTrace();
        }
    }*/



    {
        System.out.println("START!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Если выношу то не находит ресурс
        CategoriesService categoriesService = new CategoriesService();
        ProductsService productsService = new ProductsService();

        String category = request.getParameter("category");
        String name = request.getParameter("name");
        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo");

        //Если заполнено поле категория, то ищем по категории айдишник(id) для второй таблицы (catid)
        if (!category.isEmpty() & name.isEmpty() & priceFrom.isEmpty() & priceTo.isEmpty()) {
            /*System.out.println(categoriesService.getId(category));
            for (Products p : productsService.getByCat(categoriesService.getId(category))) {
                System.out.println(p);
            }*/

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
}
