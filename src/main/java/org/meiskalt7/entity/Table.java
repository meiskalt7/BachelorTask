package org.meiskalt7.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@javax.persistence.Table(name = "tables")
public class Table {

    @Id
    @Column
    private int id;

    @Column
    private int id_empl;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<Reservation>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_empl() {
        return id_empl;
    }

    public void setId_empl(int id_empl) {
        this.id_empl = id_empl;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
