package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "list_price")
    private BigDecimal listPrice;

    @Column(name = "model-year")
    private Integer modelYear;

    @Column(name = "product_name")
    private String name;

    @Column(name = "brand_id")
    private Brands brand;

    @Column(name = "category_id")
    private Categories categories;

    //private Double price; Wird nicht mehr benötigt


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", listPrice=" + listPrice +
                ", modelYear=" + modelYear +
                ", name='" + name + '\'' +
                ", brand=" + brand +
                ", category=" + categories +
                '}';
    }

    public Product(Long id, BigDecimal listPrice, Integer modelYear, String name, Brands brand, Categories categories) {
        this.id = id;
        this.listPrice = listPrice;
        this.modelYear = modelYear;
        this.name = name;
        this.brand = brand;
        this.categories = categories;
    }

    public Product() {

    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brands
    getBrand() {
        return brand;
    }

    public void setBrand(Brands brand) {
        this.brand = brand;
    }

    public Categories getCategory() {
        return categories;
    }

    public void setCategory(Categories categories) {
        this.categories = categories;
    }
}
