package org.meiskalt7.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "reservations")
public class Reservation implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id_table == that.id_table &&
                Objects.equals(name, that.name) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(datetime, that.datetime) &&
                Objects.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_table, name, phone, datetime, table);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Reservation{");
        sb.append("id=").append(id);
        sb.append(", id_table=").append(id_table);
        sb.append(", name='").append(name).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", datetime=").append(datetime);
        sb.append('}');
        return sb.toString();
    }
}
