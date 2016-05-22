package org.meiskalt7.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "timeranges")
public class TimeRange implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private Time start;
    @Column
    private Time finish;

    @OneToMany(mappedBy = "timeRange", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workshift> workshiftList = new ArrayList<>();

    public TimeRange() {
    }

    public TimeRange(Time start, Time finish) {
        this.start = start;
        this.finish = finish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getFinish() {
        return finish;
    }

    public void setFinish(Time finish) {
        this.finish = finish;
    }

    public List<Workshift> getWorkshiftList() {
        return workshiftList;
    }

    public void setWorkshiftList(List<Workshift> workshiftList) {
        this.workshiftList = workshiftList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeRange timeRange = (TimeRange) o;
        return Objects.equals(start, timeRange.start) &&
                Objects.equals(finish, timeRange.finish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, finish);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeRange{");
        sb.append("id=").append(id);
        sb.append(", start=").append(start);
        sb.append(", finish=").append(finish);
        sb.append('}');
        return sb.toString();
    }
}
