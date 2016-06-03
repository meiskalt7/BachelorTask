package org.meiskalt7.crud;

import org.meiskalt7.entity.Reservation;
import org.meiskalt7.entity.Table;
import org.meiskalt7.servlets.Entity;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

public class ReservationService extends Service<Reservation> {

    private static ReservationService reservationService;

    private ReservationService() {
    }

    public static synchronized ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public Reservation get(int id) {
        return em.find(Reservation.class, id);
    }

    public List<Reservation> getAll() {
        return (List<Reservation>) em.createQuery("SELECT r FROM Reservation r").getResultList();
    }

    public void deleteAll() {
        for (Reservation reservation : getAll()) {
            delete(reservation.getId());
        }
    }

    @Override
    public void create(HttpServletRequest request) {
        TableService tableService = (TableService) Service.getService(Entity.TABLE);

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        Timestamp time = Timestamp.valueOf(request.getParameter("time").replace("T", " ") + ":00");
        int tableId = Integer.parseInt(request.getParameter("tableId"));

        Table table = tableService.get(tableId);

        Reservation reservation = new Reservation(name, phone, time, table);
        add(reservation);

        request.setAttribute("resultMessage", "Столик забронирован");
    }

    @Override
    public void update(HttpServletRequest request, int id) {
        ReservationService reservationService = (ReservationService) Service.getService(Entity.RESERVATION);
        TableService tableService = (TableService) Service.getService(Entity.TABLE);

        Reservation reservation = reservationService.get(id);

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        Timestamp time = Timestamp.valueOf(request.getParameter("time").replace("T", " ") + ":00");

        reservation.setName(name);
        reservation.setPhone(phone);
        reservation.setDatetime(time);

        int tableId = Integer.parseInt(request.getParameter("tableId"));
        Table table = tableService.get(tableId);
        reservation.setTable(table);
        reservation.setId_table(tableId);

        reservationService.update(reservation);
    }


}
