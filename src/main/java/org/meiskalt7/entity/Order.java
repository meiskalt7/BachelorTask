package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int id_empl;

    @Column
    private Timestamp datetime;

    @Column
    private int id_table;

    @Column
    private boolean ended;

    @OneToMany(mappedBy = "order")
    private List<Orderlist> orderlists = new ArrayList<Orderlist>();

    @ManyToOne
    @JoinColumn(name = "id_empl", nullable = false, insertable = false, updatable = false)
    private Employee employee = new Employee();

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_table", nullable = false, insertable = false, updatable = false)
    private org.meiskalt7.entity.Table table = new org.meiskalt7.entity.Table();

    public Order() {
    }

    public Order(Timestamp datetime, Employee employee, org.meiskalt7.entity.Table table) {
        this.datetime = datetime;
        this.employee = employee;
        this.table = table;
        this.id_empl = employee.getId();
        this.id_table = table.getId();
        this.ended = false;
    }

    public Order(Timestamp datetime, int id_table, int id_empl) {

    }

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

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public int getId_table() {
        return id_table;
    }

    public void setId_table(int id_table) {
        this.id_table = id_table;
    }

    public List<Orderlist> getOrderlists() {
        return orderlists;
    }

    public void setOrderlists(List<Orderlist> orderlists) {
        this.orderlists = orderlists;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public org.meiskalt7.entity.Table getTable() {
        return table;
    }

    public void setTable(org.meiskalt7.entity.Table table) {
        this.table = table;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }
}
