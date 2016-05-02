package org.meiskalt7.entity;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "orderlists")
public class Orderlist {

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

    @ManyToOne
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
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
