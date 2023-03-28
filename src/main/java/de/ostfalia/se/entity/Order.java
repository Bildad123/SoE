package de.ostfalia.se.entity;

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

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer = new Customer();

    @Column(name = "total_price")
    private Double totalPrice;


    @OneToMany(cascade =
            CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Set<OrderItem> orderItems = new HashSet<>();


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


    public void calculateTotalPrice(){
        Iterator<OrderItem> iterator = this.orderItems.iterator();
        double totalPrice = 0;
        if(iterator != null){
            while (iterator.hasNext()){
                OrderItem oi = iterator.next();
                totalPrice += oi.getQuantity()  * oi.getProduct().getPrice();
            }
            this.totalPrice = totalPrice;
        }
    }


    //Getters and Setters

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

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
