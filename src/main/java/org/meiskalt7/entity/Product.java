package org.meiskalt7.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prod")
@NamedQuery(name = "Products.getAll", query = "SELECT p from Product p")
public class Product {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CAT_ID")
    @NotNull
    private int cat_id;

    @Column(name = "NAME", length = 255)
    @NotNull
    private String name;

    @Column(name = "PRICE")
    @NotNull
    private double price;

    @ManyToOne
    @JoinColumn(name = "CAT_ID", nullable = false, insertable = false, updatable = false)
    private Category category = new Category();
    @ManyToMany
    @JoinTable(
            name = "prod_composition",
            joinColumns = @JoinColumn(name = "prod_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingrid_id", referencedColumnName = "id")
    )
    private List<Ingridient> ingridients = new ArrayList<Ingridient>();

    public Product(int id, int cat_id, String name, double price) {
        this.id = id;
        this.cat_id = cat_id;
        this.name = name;
        this.price = price;
    }

    public Product(Category category, String name, double price, List<Ingridient> ingridients) {
        setCategory(category);
        this.cat_id = category.getId();
        this.name = name;
        this.price = price;
        this.ingridients = ingridients;
    }

/*    @OneToOne(fetch=FetchType.LAZY, mappedBy = "product")
    private Orderlist orderlist = new Orderlist();*/

    public Product() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public List<Ingridient> getIngridients() {
        return ingridients;
    }

    public void setIngridients(List<Ingridient> ingridients) {
        this.ingridients = ingridients;
    }

/*    public Orderlist getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(Orderlist orderlist) {
        this.orderlist = orderlist;
    }*/

    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", cat_id=" + cat_id + ", price=" + price + '}';
    }
}
