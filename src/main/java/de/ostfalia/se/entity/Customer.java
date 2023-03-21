package de.ostfalia.se.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    String firstname;

    String lastname;

    String email;

    String phone;

    String zip;

    String street;

    String country;

    public Customer(String firstname, String lastname, String email, String phone, String zip, String street, String country) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.zip = zip;
        this.street = street;
        this.country = country;
    }

    public Customer() {

    }

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

    public String getPhone() {
        return phone;
    }

    public String getZip() {
        return zip;
    }

    public String getStreet() {
        return street;
    }

    public String getCountry() {
        return country;
    }

    public String toString(){
        return this.firstname + " " + this.lastname;
    }
}
