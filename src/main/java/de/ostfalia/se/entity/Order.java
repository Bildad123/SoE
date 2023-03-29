package de.ostfalia.se.entity;

import jakarta.ejb.Local;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "order_status")
    private Integer oderStatus;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "shippedDate")
    private LocalDate shippedDate;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer = new Customer();

    @Column(name = "total_price")
    private Double totalPrice;



    /**
     * Constructor of the class Order
     * @param customer
     */
    public Order(Customer customer){
        this.customer = customer;
        this.orderDate = LocalDate.now();
    }

    public Order() {

    }


    //Getters and Setters

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
