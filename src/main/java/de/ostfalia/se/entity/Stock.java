package de.ostfalia.se.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stocks", schema = "production")
public class Stock {

    @Id
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Id
    @OneToOne
    @JoinColumn(name = "store_id", nullable = false)
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
