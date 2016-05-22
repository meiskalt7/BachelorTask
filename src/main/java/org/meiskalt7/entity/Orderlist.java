package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "orderlists")
public class Orderlist implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int order_id;

    @Column
    private int prod_id;

    @Column
    private int quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id", nullable = false, insertable = false, updatable = false)
    private Product product = new Product();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    private Order order = new Order();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        prod_id = product.getId();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        order_id = order.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orderlist orderlist = (Orderlist) o;
        return order_id == orderlist.order_id &&
                prod_id == orderlist.prod_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, prod_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Orderlist{");
        sb.append("id=").append(id);
        sb.append(", order_id=").append(order_id);
        sb.append(", prod_id=").append(prod_id);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}
