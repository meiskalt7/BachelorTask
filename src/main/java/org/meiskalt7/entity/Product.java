package org.meiskalt7.entity;

import javax.persistence.*;

@Entity
@Table(name = "prod")
@NamedQuery(name = "Products.getAll", query = "SELECT p from Product p")
public class Product {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "CAT_ID")
    private int cat_id;

    @Column(name = "NAME", length = 255)
    private String name;

    @Column(name = "PRICE")
    private double price;

    @ManyToOne
    @JoinColumn(name = "CAT_ID", nullable = false, insertable = false, updatable = false)
    private Category category = new Category();

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product(int id, int cat_id, String name, double price) {
        this.id = id;
        this.cat_id = cat_id;
        this.name = name;
        this.price = price;
    }

    public Product(int cat_id, String name, double price) {
        this.cat_id = cat_id;
        this.name = name;
        this.price = price;
    }

    public Product() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double number) {
        this.price = number;
    }

    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", cat_id=" + cat_id + ", price=" + price + '}';
    }
}
