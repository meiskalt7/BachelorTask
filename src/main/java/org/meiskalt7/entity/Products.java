package org.meiskalt7.entity;

import javax.persistence.*;

@Entity
@Table(name = "PROD")
@NamedQuery(name = "Products.getAll", query = "SELECT p from Products p")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "CAT_ID")
    private int cat_id;

    @Column(name = "NAME", length = 255)
    private String name;

    @Column(name = "PRICE")
    private double number;

    public Products(int id, int cat_id, String name, double number) {
        this.id = id;
        this.cat_id = cat_id;
        this.name = name;
        this.number = number;
    }

    public Products() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String toString() {
        return "Products{" + "id=" + id + ", name=" + name + ", cat_id=" + cat_id + ", price=" + number + '}';
    }
}
