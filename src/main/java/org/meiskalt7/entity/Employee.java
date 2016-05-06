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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "surname")
    private String surname;
    @Column(name = "name")
    private String name;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "wage")
    private Double wage;
    @Column
    private int user_id;
    @ManyToMany
    @JoinTable(
            name = "works",
            joinColumns = @JoinColumn(name = "employees_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "workshift_id", referencedColumnName = "id")
    )
    private List<Workshift> workshifts = new ArrayList<Workshift>();
    @ManyToMany
    @JoinTable(
            name = "tables_employees",
            joinColumns = @JoinColumn(name = "employees_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "table_id", referencedColumnName = "id")
    )
    private List<org.meiskalt7.entity.Table> tables = new ArrayList<org.meiskalt7.entity.Table>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user = new User();

    public Employee() {
    }

    public Employee(String surname, String name, String patronymic, Double wage) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.wage = wage;
    }

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

    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    public List<Workshift> getWorkshifts() {
        return workshifts;
    }

    public void setWorkshifts(List<Workshift> workshifts) {
        this.workshifts = workshifts;
    }

    public List<org.meiskalt7.entity.Table> getTables() {
        return tables;
    }

    public void setTables(List<org.meiskalt7.entity.Table> tables) {
        this.tables = tables;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
