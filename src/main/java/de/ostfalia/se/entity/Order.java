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
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "order_status")
    private Integer oderStatus;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "shipped_date")
    private LocalDate shippedDate;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer = new Customer();


    @OneToOne
    @JoinColumn(name = "staff_id")
    private Staff staff = new Staff();

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store = new Store();





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

    public Integer getOderStatus() {
        return oderStatus;
    }

    public void setOderStatus(Integer oderStatus) {
        this.oderStatus = oderStatus;
    }

    public LocalDate getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(LocalDate requiredDate) {
        this.requiredDate = requiredDate;
    }

    public LocalDate getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDate shippedDate) {
        this.shippedDate = shippedDate;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
