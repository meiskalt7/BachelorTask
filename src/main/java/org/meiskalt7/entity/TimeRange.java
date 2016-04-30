package org.meiskalt7.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.Table(name = "timeranges")
public class TimeRange {

    @Id
    @Column
    private int id;

    @Column
    private String start;

    @Column
    private String finish;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }
}
