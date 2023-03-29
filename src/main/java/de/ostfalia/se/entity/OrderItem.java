package de.ostfalia.se.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "list_price")
    private Double listPrice;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order = new Order();

    @OneToOne
    private Product product;

    public OrderItem() {

    }

    /**
     * Constructor of the class OrderItem
     * @param product
     */
    public OrderItem(Product product) {
        this.quantity = 1;
        this.product = product;
        this.listPrice = product.getPrice();
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public String toString(){
        return  "( " + this.getQuantity() + "  X " + this.getProduct().getName() + " )";
    }


}
