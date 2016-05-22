package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "prod_composition")
public class Composition implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int required;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "prod_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ingrid_id")
    private Ingridient ingridient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Ingridient getIngridient() {
        return ingridient;
    }

    public void setIngridient(Ingridient ingridient) {
        this.ingridient = ingridient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Composition that = (Composition) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(ingridient, that.ingridient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, ingridient);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Composition{");
        sb.append("id=").append(id);
        sb.append(", required=").append(required);
        sb.append(", product=").append(product);
        sb.append(", ingridient=").append(ingridient);
        sb.append('}');
        return sb.toString();
    }
}
