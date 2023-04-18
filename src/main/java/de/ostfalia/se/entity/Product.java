package de.ostfalia.se.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @Column(name = "list_price")
    private Double price;

    @Column(name = "product_name")
    private String name;

    @Column(name = "model_year")
    Integer modelYear;

    @OneToOne
    @JoinColumn(name = "category_id")
    Category category = new Category();

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand = new Brand();




    /**
     * Constructor of the class Product
     * @param name
     * @param price
     */
    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Product() {

    }

    //Getters and Setters
    public String toString(){
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
