package de.ostfalia.se.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stores")
public class Stores {
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

    public Stores(Long id, String storeName, String email, String phone, String street, String zipCode, String city, String state) {
        this.id = id;
        this.storeName = storeName;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }

    public Stores() {
    }

    @Override
    public String toString() {
        return "Stores{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    //Getter und Setter

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
}
