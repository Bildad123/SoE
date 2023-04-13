package de.ostfalia.se.entity;

import java.io.Serializable;
import java.util.Objects;

public class StockPK implements Serializable {
    private Product product;
    private Store store;

    public StockPK(Product product, Store store) {
        this.product = product;
        this.store = store;
    }

    public StockPK() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockPK stockPK = (StockPK) o;
        return Objects.equals(product, stockPK.product) && Objects.equals(store, stockPK.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, store);
    }
}
