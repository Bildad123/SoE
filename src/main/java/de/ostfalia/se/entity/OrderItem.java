package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@IdClass(OrderItemPK.class)
public class OrderItem {
    @Id
    @Column(name = "item_id")
    private Long id;


    @Id
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private BigDecimal discount;

    @Column(name = "list_price")
    private BigDecimal listPrice;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public OrderItem(Long id, Order order, BigDecimal discount, BigDecimal listPrice, Integer quantity, Product product) {
        this.id = id;
        this.order = order;
        this.discount = discount;
        this.listPrice = listPrice;
        this.quantity = quantity;
        this.product = product;
    }

    public OrderItem() {

    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order +
                ", discount=" + discount +
                ", listPrice=" + listPrice +
                ", quantity=" + quantity +
                ", product=" + product +
                '}';
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
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
