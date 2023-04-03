package de.ostfalia.se.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Double price;


    /**
     * Constructor of the class Product
     * @param name
     * @param price
     */
    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Product() {

    }

    //Getters and Setters
    public String toString(){
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
