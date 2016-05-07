package org.meiskalt7.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@javax.persistence.Table(name = "reservations")
public class Reservation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_table")
    private int id_table;

    @Column(name = "name")
    private String name;

    @Column
    private String phone;

    @Column
    private Timestamp datetime;

    @ManyToOne
    @JoinColumn(name = "id_table", nullable = false, insertable = false, updatable = false)
    private Table table = new Table();

    public Reservation() {
    }

    public Reservation(String name, String phone, Timestamp datetime, Table table) {
        this.name = name;
        this.phone = phone;
        this.datetime = datetime;
        this.table = table;
        this.id_table = table.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_table() {
        return id_table;
    }

    public void setId_table(int id_table) {
        this.id_table = id_table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
