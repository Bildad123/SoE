package de.ostfalia.se.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "staffs")
public class Staff {
    @Id
    @GeneratedValue
    @Column(name = "staff_id")
    private Long id;

    private Integer active;

    private String email;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Staff manager;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store = new Store();


    public Staff(){

    }




    //Getters and Setters

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Staff getManager() {
        return manager;
    }

    public void setManager(Staff manager) {
        this.manager = manager;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }





}
