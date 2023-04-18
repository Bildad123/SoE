package de.ostfalia.se.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    private String email;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    private String phone;

    private String state;

    private String street;

    @Column(name = "zip_code")
    private String zip;





    /**
     * Constructor of class Customer
     * @param firstname
     * @param lastname
     * @param email
     * @param zip
     * @param street
     */
    public Customer(String firstname, String lastname, String email, String zip, String street) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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


    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
