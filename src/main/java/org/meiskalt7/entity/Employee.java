package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employees")
@NamedQuery(name = "Employees.getAll", query = "SELECT c from Employee c")
public class Employee implements Serializable {

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
    private int usertype_id;

    @Column
    private String username;
    @Column
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "works",
            joinColumns = @JoinColumn(name = "employees_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "workshift_id", referencedColumnName = "id")
    )
    private List<Workshift> workshifts = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "tables_employees",
            joinColumns = @JoinColumn(name = "employees_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "table_id", referencedColumnName = "id")
    )
    private List<org.meiskalt7.entity.Table> tables = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "usertype_id", nullable = false, insertable = false, updatable = false)
    private UserType userType = new UserType();

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL) //CascadeType -
    private List<Order> orderList = new ArrayList<>();

    public Employee() {
    }

    public Employee(String surname, String name, String patronymic, Double wage, String username, String password, UserType userType) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.wage = wage;
        this.username = username;
        this.password = password;
        this.userType = userType;
        usertype_id = userType.getId();
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

    public int getUsertype_id() {
        return usertype_id;
    }

    public void setUsertype_id(int usertype_id) {
        this.usertype_id = usertype_id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(surname, employee.surname) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(patronymic, employee.patronymic) &&
                Objects.equals(username, employee.username) &&
                Objects.equals(password, employee.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, patronymic, username, password);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", patronymic='").append(patronymic).append('\'');
        sb.append(", wage=").append(wage);
        sb.append(", usertype_id=").append(usertype_id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
