package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private LocalDate birthdate;

    private String zip;

    private String street;


    @OneToMany(cascade =
            CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Set<Order> customerOrders = new HashSet<>();


    /**
     * Constructor of class Customer
     * @param firstname
     * @param lastname
     * @param email
     * @param birthdate
     * @param zip
     * @param street
     */
    public Customer(String firstname, String lastname, String email, LocalDate birthdate, String zip, String street) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthdate = birthdate;
        this.zip = zip;
        this.street = street;
    }

    public Customer() {

    }


    //Getters and Setters

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getZip() {
        return zip;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Set<Order> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(Set<Order> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public String getStreet() {
        return street;
    }

    public String toString(){
        return this.firstname + "   " + this.lastname;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
