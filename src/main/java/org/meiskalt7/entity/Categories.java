package org.meiskalt7.entity;

import javax.persistence.*;

@Entity
@Table(name = "CAT")
@NamedQuery(name = "Categories.getAll", query = "SELECT c from Categories c")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME", length = 255)
    private String name;

    public Categories(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Categories() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Categories{" + "id=" + id + ", name=" + name + '}';
    }
}
