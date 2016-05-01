package org.meiskalt7.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@javax.persistence.Table(name = "timeranges")
public class TimeRange {

    public TimeRange() {
    }

    public TimeRange(Time start, Time finish) {
        this.start = start;
        this.finish = finish;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timeranges_seq_gen")
    @SequenceGenerator(name = "timeranges_seq_gen", sequenceName = "timeranges_id_seq")
    private int id;

    @Column
    private Time start;

    @Column
    private Time finish;

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
}
