package org.meiskalt7.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@javax.persistence.Table(name = "tables")
public class Table {

    public Table() {
    }

    public Table(String type) {
        this.type = type;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tables_seq_gen")
    @SequenceGenerator(name = "tables_seq_gen", sequenceName = "tables_id_seq")
    private int id;

    @Column
    private String type;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<Reservation>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
