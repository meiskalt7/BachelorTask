package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "workshifts")
public class Workshift implements Serializable {

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
    @ManyToOne(cascade = CascadeType.MERGE)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workshift workshift = (Workshift) o;
        return timerange_id == workshift.timerange_id &&
                Objects.equals(date, workshift.date) &&
                Objects.equals(timeRange, workshift.timeRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, timerange_id, timeRange);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Workshift{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", timerange_id=").append(timerange_id);
        sb.append(", timeRange=").append(timeRange);
        sb.append('}');
        return sb.toString();
    }
}
