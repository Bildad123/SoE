package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;
    private Integer quantity;

    @ManyToMany
    @JoinTable(name = "order_item_order",
            joinColumns = {@JoinColumn(name = "order_item_id", referencedColumnName = "order_item_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "order_id")})
    private Set<Order> orders = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
