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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "order_status")
    private Integer orderStatus;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "shipped_date")
    private LocalDate shippedDate;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "staff_id")
    private Staffs staff;

    @Column(name = "store_id")
    private Stores store;

    //Wird nicht mehr benötigt
    //@Column(name = "total_price") Wird nicht mehr benötigt
    //private Double totalPrice;

    //Wird nicht mehr benötigt
   // @OneToMany(cascade =
   // CascadeType.ALL, fetch = FetchType.EAGER)
   //@JoinColumn(name = "order_id")
   // private Set<OrderItem> orderItems = new HashSet<>();


    public Order(Long id, LocalDate orderDate, Integer orderStatus, LocalDate requiredDate, LocalDate shippedDate, Customer customer, Staffs staff, Stores store) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.customer = customer;
        this.staff = staff;
        this.store = store;
    }

    /**
     * Constructor of the class Order
     * @param customer
     */


    public Order() {

    }


/*        public void calculateTotalPrice(){
            Iterator<OrderItem> iterator = this.orderItems.iterator();
            double totalPrice = 0;
            if(iterator != null){
                while (iterator.hasNext()){
                    OrderItem oi = iterator.next();
                    totalPrice += oi.getQuantity()  * oi.getProduct().getPrice();
                }
                this.totalPrice = totalPrice;
            }*/
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", orderStatus=" + orderStatus +
                ", requiredDate=" + requiredDate +
                ", shippedDate=" + shippedDate +
                ", customer=" + customer +
                ", staff=" + staff +
                ", store=" + store +
                '}';
    }

    //Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Staffs getStaff() {
        return staff;
    }

    public void setStaff(Staffs staff) {
        this.staff = staff;
    }

    public Stores getStore() {
        return store;
    }

    public void setStore(Stores store) {
        this.store = store;
    }
}
