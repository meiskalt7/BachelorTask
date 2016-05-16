package org.meiskalt7.servlets;

import org.meiskalt7.crud.ReservationService;
import org.meiskalt7.crud.TableService;
import org.meiskalt7.entity.Reservation;
import org.meiskalt7.entity.Table;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "ReservationsPage")
public class ReservationsPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReservationService reservationService = ReservationService.getInstance();
        TableService tableService = TableService.getInstance();

        req.setAttribute("reservationList", reservationService.getAll());

        if (req.getParameter("button") != null) {
            String button[] = req.getParameter("button").split(" ");

            Operation operation = Operation.valueOf(button[0]);
            Entity entity = Entity.valueOf(button[1]);

            switch (operation) {

                case ADD:
                    switch (entity) {
                        case RESERVATION:
                            String name = req.getParameter("name");
                            String phone = req.getParameter("phone");
                            Timestamp time = Timestamp.valueOf(req.getParameter("time").replace("T", " ") + ":00");
                            int tableId = Integer.parseInt(req.getParameter("tableId"));

                            Table table = tableService.get(tableId);

                            Reservation reservation = new Reservation(name, phone, time, table);
                            reservationService.add(reservation);

                            req.setAttribute("resultMessage", "Столик забронирован");
                            break;
                    }
                case DELETE:
                    switch (entity) {
                        case RESERVATION:
                            int id = Integer.parseInt(req.getParameter("id"));
                            reservationService.delete(id);
                            break;
                    }
                    break;
            }
        }

        req.setAttribute("tableList", tableService.getAll());

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/waiter/reservationsPage.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
