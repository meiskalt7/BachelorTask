package org.meiskalt7.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@javax.persistence.Table(name = "tables")
public class Table {

    @Id
    @Column
    /*@SequenceGenerator(name = "tables_seq_gen", sequenceName = "tables_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tables_seq_gen")*/
    //@GeneratedValue(strategy = GenerationType.TABLE) нарушение уникальности ключа
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String type;
    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<Reservation>();
    @ManyToMany(mappedBy = "tables")
    private List<Employee> employees = new ArrayList<Employee>();

    public Table() {
    }

    public Table(String type) {
        this.type = type;
    }

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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
