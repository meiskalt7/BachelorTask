package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "usertype")
public class UserType {

    @Id
    @Column
    @GeneratedValue
    private int id;

    @Column
    private String type;

    @OneToMany(mappedBy = "userType")
    private List<Employee> user;

    public UserType() {
    }

    public UserType(String type, List<Employee> user) {
        this.type = type;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Employee> getUser() {
        return user;
    }

    public void setUser(List<Employee> user) {
        this.user = user;
    }
}
