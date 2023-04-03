package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Locale;

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
    private Brand brand;

    @Column(name = "category_id")
    private Category category;

    //private Double price; Wird nicht mehr benötigt


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", listPrice=" + listPrice +
                ", modelYear=" + modelYear +
                ", name='" + name + '\'' +
                ", brand=" + brand +
                ", category=" + category +
                '}';
    }

    public Product(Long id, BigDecimal listPrice, Integer modelYear, String name, Brand brand, Category category) {
        this.id = id;
        this.listPrice = listPrice;
        this.modelYear = modelYear;
        this.name = name;
        this.brand = brand;
        this.category = category;
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
