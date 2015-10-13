package org.meiskalt7.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CAT")
@NamedQuery(name = "Categories.getAll", query = "SELECT c from Categories c")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME", length = 255)
    private String name;

    @OneToMany(mappedBy="category", fetch = FetchType.LAZY)
    private List<Products> products = new ArrayList<Products>();

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

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
