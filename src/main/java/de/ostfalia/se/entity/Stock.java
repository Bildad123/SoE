package de.ostfalia.se.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock {

    private Integer quantity;

    @Id
    @GeneratedValue
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product = new Product();

    @Id
    @GeneratedValue
    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store = new Store();


    //Getters and Setters


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


}
