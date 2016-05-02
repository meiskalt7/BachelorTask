package org.meiskalt7.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingridients")
public class Ingridient {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "NAME", length = 255)
    @NotNull
    private String name;
    @Column
    @NotNull
    private int quantity;
    @ManyToMany(mappedBy = "ingridients")
    private List<Product> products = new ArrayList<Product>();

    public Ingridient() {
    }

    public Ingridient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
