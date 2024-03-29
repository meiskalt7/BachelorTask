package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Orderlist> orderlists = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_empl", nullable = false, insertable = false, updatable = false)
    private Employee employee = new Employee();

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_table", nullable = false, insertable = false, updatable = false)
    private org.meiskalt7.entity.Table table = new org.meiskalt7.entity.Table();

    public Order() {
    }

    public Order(Timestamp datetime, Employee employee, org.meiskalt7.entity.Table table) {
        this.datetime = datetime == null ? null : new Timestamp(datetime.getTime());
        this.employee = employee;
        this.table = table;
        this.id_empl = employee.getId();
        this.id_table = table.getId();
        this.ended = false;
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
        this.datetime = datetime == null ? null : new Timestamp(datetime.getTime());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id_empl == order.id_empl &&
                id_table == order.id_table &&
                Objects.equals(datetime, order.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_empl, datetime, id_table);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", id_empl=").append(id_empl);
        sb.append(", datetime=").append(datetime);
        sb.append(", id_table=").append(id_table);
        sb.append(", ended=").append(ended);
        sb.append('}');
        return sb.toString();
    }
}
