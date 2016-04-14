package org.meiskalt7.entity;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "reservations")
public class Reservation {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "id_table")
    private int id_table;

    @Column(name = "name")
    private String name;

    @Column
    private String phone;

    @Column
    private String datetime;

    @ManyToOne
    @JoinColumn(name = "id_table", nullable = false, insertable = false, updatable = false)
    private Table table = new Table();

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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
