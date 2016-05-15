package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workshifts")
public class Workshift {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private Date date;
    @Column
    private int timerange_id;

    @ManyToMany(mappedBy = "workshifts") //TODO нужен каскад для удаления только смен, без работников
    private List<Employee> employees = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.MERGE) //мб понадобится еще на обновление
    @JoinColumn(name = "timerange_id", nullable = false, insertable = false, updatable = false)
    private TimeRange timeRange = new TimeRange();

    public Workshift() {
    }

    public Workshift(Date date, TimeRange timeRange, List<Employee> employees) {
        this.date = date;
        this.timeRange = timeRange;
        this.employees = employees;
        this.timerange_id = timeRange.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date datetime) {
        this.date = datetime;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public int getTimerange_id() {
        return timerange_id;
    }

    public void setTimerange_id(int timerange_id) {
        this.timerange_id = timerange_id;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }
}
