package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OrderItemId;
    private Integer quantity;
//    @ManyToMany
//    @JoinColumn(name = "order_id")
//    private Set<Order> orderSet;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItem(Long orderItemId, Integer quantity, Set<Order> orderSet, Product product) {
        OrderItemId = orderItemId;
        this.quantity = quantity;
//        this.orderSet = orderSet;
        this.product = product;
    }

    public OrderItem() {
    }

    public Long getOrderItemId() {
        return OrderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        OrderItemId = orderItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

//    public Set<Order> getOrderSet() {
//        return orderSet;
//    }
//
//    public void setOrderSet(Set<Order> orderSet) {
//        this.orderSet = orderSet;
//    }
//
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
//
//    @Override
//    public String toString() {
//        return "OrderItem{" +
//                "quantity=" + quantity +
//                ", orderSet=" + orderSet +
//                ", product=" + product +
//                '}';
//    }
}
