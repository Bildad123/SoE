package de.ostfalia.se.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    private Integer quantity;

    @OneToOne
    private Product product;

    public OrderItem() {

    }

    /**
     * Constructor of the class OrderItem
     * @param quantity
     * @param product
     */
    public OrderItem(Integer quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }


    //Getters and Setters
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

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
}
