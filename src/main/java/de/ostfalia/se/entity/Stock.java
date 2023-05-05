package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "stocks")
@IdClass(StockPK.class)
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

    @Override
    public String toString() {
        return "Stock{" +
                "product=" + product +
                ", store=" + store +
                ", quantity=" + quantity +
                '}';
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(product, stock.product) && Objects.equals(store, stock.store) && Objects.equals(quantity, stock.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, store, quantity);
    }
}
