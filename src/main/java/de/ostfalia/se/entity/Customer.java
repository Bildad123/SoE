package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers", schema = "sales")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    private String email;

    // private LocalDate birthdate; nicht mehr notwendig

    private String street;

    @Column(name = "zip_code")
    private String zip;

    private String state;

    private String city;

    private String phone;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();

    public Customer(Long id, String firstname, String lastname, String email, String street, String zip, String state, String city, String phone, Set<Order> orders) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.street = street;
        this.zip = zip;
        this.state = state;
        this.city = city;
        this.phone = phone;
        this.orders = orders;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", orders=" + orders +
                '}';
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
