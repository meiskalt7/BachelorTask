package org.meiskalt7.crud;

import org.meiskalt7.entity.Reservation;

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
}
