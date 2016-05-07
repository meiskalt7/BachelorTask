package org.meiskalt7.servlets;

import org.meiskalt7.crud.ReservationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ReservationsPage")
public class ReservationsPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReservationService reservationService = ReservationService.getInstance();

        req.setAttribute("reservationList", reservationService.getAll());

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/reservationsPage.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
