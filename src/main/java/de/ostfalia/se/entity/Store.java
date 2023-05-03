package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "store_name")
    private String storeName;

    private String email;

    private String phone;

    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    private String city;

    private String state;

    @OneToMany(mappedBy = "store")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "store")
    private Set<Staff> staffs = new HashSet<>();

    @OneToMany(mappedBy = "store")
    private Set<Stock> stocks = new HashSet<>();

    public Store(Long id, String storeName, String email, String phone, String street, String zipCode, String city, String state, Set<Order> orders, Set<Staff> staffs, Set<Stock> stocks) {
        this.id = id;
        this.storeName = storeName;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
        this.orders = orders;
        this.staffs = staffs;
        this.stocks = stocks;
    }

    public Store() {
    }

    @Override
    public String toString() {
        return storeName;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(Set<Staff> staffs) {
        this.staffs = staffs;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }
}
