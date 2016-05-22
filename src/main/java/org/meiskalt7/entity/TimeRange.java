package org.meiskalt7.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@javax.persistence.Table(name = "timeranges")
public class TimeRange {

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
}
