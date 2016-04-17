package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@NamedQuery(name = "Employees.getAll", query = "SELECT c from Employee c")
public class Employee {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "wage")
    private String wage;

    @ManyToMany
    @JoinTable(
            name = "works",
            joinColumns = @JoinColumn(name = "employees_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "workshift_id", referencedColumnName = "id")
    )
    private List<Workshift> workshifts = new ArrayList<Workshift>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public List<Workshift> getWorkshifts() {
        return workshifts;
    }

    public void setWorkshifts(List<Workshift> workshifts) {
        this.workshifts = workshifts;
    }
}
