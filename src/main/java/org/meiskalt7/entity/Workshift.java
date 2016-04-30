package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "workshifts")
public class Workshift {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private Date datetime;

    @ManyToMany(mappedBy = "workshifts")
    private List<Employee> employees = new ArrayList<Employee>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
