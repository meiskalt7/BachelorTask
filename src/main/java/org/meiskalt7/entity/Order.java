package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq_gen")
    @SequenceGenerator(name = "orders_seq_gen", sequenceName = "orders_id_seq")
    private int id;

    @Column
    private int id_empl;

    @Column
    private Date datetime;

    @Column
    private int id_table;

    @OneToMany(mappedBy = "order")
    private List<Orderlist> orderlists = new ArrayList<Orderlist>();

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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
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
}
