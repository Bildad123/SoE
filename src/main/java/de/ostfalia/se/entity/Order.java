package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "order_date")
    private Date orderDate;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
//    @ManyToMany
//    @JoinColumn(name = "product_id")
//    private Set<OrderItem> orderItemSet;

    public Order(Long orderId, Date orderDate, Customer customer, Set<OrderItem> orderItemSet) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
//        this.orderItemSet = orderItemSet;
    }

    public Order() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

//    public Set<OrderItem> getOrderItemSet() {
//        return orderItemSet;
//    }
//
//    public void setOrderItemSet(Set<OrderItem> orderItemSet) {
//        this.orderItemSet = orderItemSet;
//    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                '}';
    }
}
