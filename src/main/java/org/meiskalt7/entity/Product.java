package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "prod")
@NamedQuery(name = "Products.getAll", query = "SELECT p from Product p")
public class Product implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToMany(mappedBy = "product")
    /*@JoinTable(
            name = "prod_composition",
            joinColumns = @JoinColumn(name = "prod_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingrid_id", referencedColumnName = "id")
    )*/
    private List<Composition> ingridients = new ArrayList<>();

    public Product(int id, int cat_id, String name, double price) {
        this.id = id;
        this.cat_id = cat_id;
        this.name = name;
        this.price = price;
    }

    public Product(Category category, String name, double price) {
        setCategory(category);
        this.cat_id = category.getId();
        this.name = name;
        this.price = price;
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

    public List<Composition> getIngridients() {
        return ingridients;
    }

    public void setIngridients(List<Composition> ingridients) {
        this.ingridients = ingridients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return cat_id == product.cat_id &&
                Objects.equals(name, product.name) &&
                Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cat_id, name, category);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", cat_id=").append(cat_id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
