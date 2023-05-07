package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private Integer quantity;

    public Stock(Product product, Store store, Integer quantity) {
        this.product = product;
        this.store = store;
        this.quantity = quantity;
    }

    public Stock() {
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
